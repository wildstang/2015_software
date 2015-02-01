package org.wildstang.logger.sender;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogManager
{
	private static LogManager instance = null;
	private static List<LogObject> objects = new ArrayList<>();
	private static Socket socket;
	private static long startTime = System.currentTimeMillis();
	
	public static LogManager getInstance()
	{
		if(LogManager.instance == null)
		{
			LogManager.instance = new LogManager();
		}
		return LogManager.instance;
	}
	
	public void init()
	{
		try
		{
			//hostname will need to be updated
			socket = new Socket("hostname", 1111);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update()
	{
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("Timestamp", System.currentTimeMillis() - startTime);
		for(LogObject object : objects)
		{
		    map.put(object.getName(), object.getObject());
		}
		try
		{
		    OutputStream outputStream;
			outputStream = socket.getOutputStream();
		    ObjectOutputStream mapOutputStream;
			mapOutputStream = new ObjectOutputStream(outputStream);
		    mapOutputStream.writeObject(map);
		    mapOutputStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LogObject getObject(int index)
	{
		return (LogObject) objects.get(index);
	}

	//0-15 reserved for individual currents
	public static final int CURRENT_INDEX = 16;
	public static final int VOLTAGE_INDEX = 17;
	public static final int TEMPERATURE_INDEX = 18;
	
	protected LogManager()
	{
		for(int i = 0; i < 16; i++)
		{
			objects.add(i, new LogObject("Current " + i));
		}
		objects.add(CURRENT_INDEX, new LogObject("Total Current"));
		objects.add(VOLTAGE_INDEX, new LogObject("Voltage"));
		objects.add(TEMPERATURE_INDEX, new LogObject("Temperature"));
	}
	
}
