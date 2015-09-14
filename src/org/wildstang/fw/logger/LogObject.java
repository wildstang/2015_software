package org.wildstang.fw.logger;

public class LogObject {
	String name;
	Object object;

	public LogObject(String name, Object object) {
		this.name = name;
		this.object = object;
	}

	public String getName() {
		return name;
	}

	public Object getObject() {
		return object;
	}
}
