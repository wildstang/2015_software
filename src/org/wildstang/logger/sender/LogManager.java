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
			socket = new Socket("hostname", 1111);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update()
	{
	    Map<String, Object> map = new HashMap<String, Object>();
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
	
	
	
	protected LogManager()
	{
		//logs.add(UNKNOWN_INDEX, new NoLog());
	}
	
}
