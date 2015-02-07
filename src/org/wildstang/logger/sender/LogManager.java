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

public class LogManager {
	private static LogManager instance = null;
	private List<LogObject> objects = new ArrayList<>();
	private List<LogObject> debugs = new ArrayList<>();
	private long startTime = System.currentTimeMillis();
	private ObjectSender logDataSender;
	private ObjectSender debugDataSender;

	public static LogManager getInstance() {
		if (LogManager.instance == null) {
			LogManager.instance = new LogManager();
		}
		return LogManager.instance;
	}

	public void init() {
		new Thread(logDataSender = new ObjectSender(1111)).start();
		new Thread(debugDataSender = new ObjectSender(1112)).start();
	}

	public void update() {
		// for sending subsystem data
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Timestamp", System.currentTimeMillis() - startTime);
		for (LogObject object : objects) {
			map.put(object.getName(), object.getObject());
		}
		logDataSender.addToQueue(map);

		// for sending debug messages
		map = new HashMap<String, Object>();
		map.put("Timestamp", System.currentTimeMillis() - startTime);
		for (LogObject debug : debugs) {
			map.put(debug.getName(), debug.getObject());
		}
		debugDataSender.addToQueue(map);

		objects.clear();
		debugs.clear();
	}

	public void addDebug(Object message) {
		debugs.add(new LogObject("Debug", message));
	}

	public void addObject(String name, Object obj) {
		objects.add(new LogObject(name, obj));
	}

	public class ObjectSender implements Runnable {
		private Socket socket;
		private ObjectOutputStream outputStream;
		private BlockingDeque<Object> queue = new LinkedBlockingDeque<>();
		private int portNumber;

		public ObjectSender(int portNumber) {
			this.portNumber = portNumber;
		}

		public void addToQueue(Object o) {
			queue.addFirst(o);
		}

		@Override
		public void run() {
			while (true) {
				try {
					socket = new Socket("beaglebone.local", portNumber);
					outputStream = new ObjectOutputStream(
							socket.getOutputStream());

					while (true) {
						Object o;
						try {
							while ((o = queue.takeLast()) != null) {
								outputStream.writeObject(o);
							}
						} catch (InterruptedException e) {
							continue;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
