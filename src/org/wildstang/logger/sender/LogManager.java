package org.wildstang.logger.sender;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This is the robot component of the WildLog system. It is responsible for receiving logs from robot code, storing
 * them, and eventually sending them to an offboard processor for storage.
 * 
 * USAGE
 * 
 * The LogManager is a singleton; to obtain an instance; call LogManager.getInstance().
 * 
 * The LogManager will send data as discrete bundles (Maps, specifically). Each of those bundles contains the timestamp
 * of when the bundle was sent and any logged data that was added since the last bundle was sent out. A typical pattern
 * for this is to log, once per robot cycle, all the things you want to keep track of, and then send out the bundle at
 * the end of the cycle.
 * 
 * To log a piece of data; call addObject(). That method accepts two parameters. The first is a key that can later be
 * used to identify the piece of data you just logged. Note that this key should be unique for each map of data sent
 * out. The second is a Java object that will be associated with the key. This can be any Java object that you want,
 * although usually it will be a Double, String, or a Boolean (The WildLog data visualizer has built-in support for
 * those data types).
 * 
 * To queue the current logs for sending, call the (aptly-named) method queueCurrentLogsForSending(). This will send all
 * the current logs to a background thread, which will queue them to be sent over to the offboard processor. To avoid
 * any issues with running out of memory, the queue is limited to storing 20 objects at any given time.
 * 
 * @author Nathan
 *
 */
public class LogManager {
	private static LogManager instance = null;
	private List<LogObject> objects = new ArrayList<>();
	private long startTime = System.currentTimeMillis();
	private LogSender logDataSender;

	public static LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
			instance.init();
		}
		return instance;
	}

	private void init() {
		new Thread(logDataSender = new LogSender("beaglebone.local", 1111)).start();
	}

	/**
	 * Queues the current accumulated data to be sent to the offboard processor. Once the logs have been added to the
	 * queue, the list of logs is cleared. This should be called once all the data you want in the outgoing bundle has
	 * been added via one of the add methods.
	 */
	public void queueCurrentLogsForSending() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Timestamp", System.currentTimeMillis() - startTime);
		for (LogObject object : objects) {
			map.put(object.getName(), object.getObject());
		}
		logDataSender.addLogToQueue(map);

		objects.clear();

	}

	public void addLog(String name, Object obj) {
		objects.add(new LogObject(name, obj));
	}

	public void startLog() {
		logDataSender.addCommandToQueue("startlog");
	}

	public void endLog() {
		logDataSender.addCommandToQueue("endlog");
	}

	/**
	 * A thread that queues and sends log maps to the offboard processor.
	 * 
	 * @author Nathan
	 *
	 */
	public class LogSender implements Runnable {
		private static final int QUEUE_MAX_SIZE = 20;
		private Socket socket;
		private ObjectOutputStream outputStream;
		// Limit queue to 20 objects; if we don't we'll run out of memory after a while.
		private BlockingDeque<Object> logQueue = new LinkedBlockingDeque<>(QUEUE_MAX_SIZE);
		private BlockingDeque<Object> commandQueue = new LinkedBlockingDeque<>(QUEUE_MAX_SIZE);
		private String host;
		private int port;

		public LogSender(String host, int port) {
			this.port = port;
			this.host = host;
		}

		public void addLogToQueue(Object o) {
			while (logQueue.size() >= QUEUE_MAX_SIZE) {
				logQueue.removeLast();
			}
			logQueue.addFirst(o);
		}

		public void addCommandToQueue(Object o) {
			while (commandQueue.size() >= QUEUE_MAX_SIZE) {
				commandQueue.removeLast();
			}
			commandQueue.addFirst(o);
		}

		@Override
		public void run() {
			while (true) {
				// If we lose connection or the connection attempt times out, continually retry.

				try {
					socket = new Socket(host, port);
					outputStream = new ObjectOutputStream(socket.getOutputStream());

					while (true) {
						Object o;
						// send commands first
						while ((o = commandQueue.pollLast()) != null) {
							outputStream.writeObject(o);
						}

						// now send any logs
						while ((o = logQueue.pollLast()) != null) {
							outputStream.writeObject(o);
						}
						outputStream.flush();
					}
				} catch (IOException e) { //
					e.printStackTrace();
					System.out.println("Unable to open socket. Retrying in a few seconds.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
