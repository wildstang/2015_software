package org.wildstang.logger.sender;

public class LogObject
{
	String name;
	Object object;
	
	public LogObject(String name, Object object)
	{
		this.name = name;
		this.object = object;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Object getObject()
	{
		return object;
	}
	
	public void updateObject(Object object)
	{
		this.object = object;
	}
}
