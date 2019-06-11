package com.thkmon.webstd.common.util;

import java.util.HashMap;

public class LogUtil {

	private static HashMap loggerList = new HashMap();
	
	public static LogUtil getLoggerInstance(String name) {
		
		Object obj = loggerList.get(name);
		
		if (obj == null) {
			LogUtil logger = new LogUtil();
			logger.setName(name);
			loggerList.put(name, logger);
			return logger;
			
		} else {
			return (LogUtil)obj;
		}
	}
	
	private String name = "";

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void debug(String str) {
		System.out.println(name + " : " + str);
	}
	public void debug(Object obj) {
		System.out.println(name + " : " + obj);
    }
}
