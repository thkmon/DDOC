package com.thkmon.webstd.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String padLeft(int i, int digit) {
		String orgStr = String.valueOf(i);
		
		StringBuffer buff = new StringBuffer();
		while (buff.length() + orgStr.length() < digit) {
			buff.append("0");
		}
		buff.append(orgStr);
		return buff.toString();
	}
	
	public static String getYear() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.YEAR), 4);
	}
	
	public static String getMonth() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.MONTH) + 1, 2);
	}
	
	public static String getDay() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.DAY_OF_MONTH), 2);
	}
	
	public static String getHour() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.HOUR_OF_DAY), 2);
	}
	
	public static String getMinute() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.MINUTE), 2);
	}
	
	public static String getSecond() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.SECOND), 2);
	}
	
	public static String getMiliSecond() {
		Calendar c = Calendar.getInstance();
		return padLeft(c.get(c.MILLISECOND), 3);
	}
	
	public static String getYearMonth(){
		StringBuffer buff = new StringBuffer();
		buff.append(getYear());
		buff.append(getMonth());
		return buff.toString();
	}
	
	public static String getYearMonthDay(){
		StringBuffer buff = new StringBuffer();
		buff.append(getYear());
		buff.append(getMonth());
		buff.append(getDay());
		return buff.toString();
	}

	public static String getHourMinuteSecond(){
		StringBuffer buff = new StringBuffer();
		buff.append(getHour());
		buff.append(getMinute());
		buff.append(getSecond());
		return buff.toString();
	}
	
	public static String getDateTime(){
		StringBuffer buff = new StringBuffer();
		buff.append(getYear());
		buff.append(getMonth());
		buff.append(getDay());
		buff.append(getHour());
		buff.append(getMinute());
		buff.append(getSecond());
		return buff.toString();
	}
	
	public static String toPirntDateTime(String str) {
		if (str == null || str.length() == 0) {
			return "0000/00/00 00:00:00";
		}
		
		str = str.replace(".", "").replace(":", "").replace("/", "").replace(" ", "");
		
		if (str.length() < 14) {
			int lenToPlus = 14 - str.length();
			for (int i=0; i<lenToPlus; i++) {
				str += "0";
			}
		}
		
		if (str.length() > 14) {
			str = str.substring(0, 14);
		}
		
		return str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, 8) + " " + 
			str.substring(8, 10) + ":" + str.substring(10, 12); // + ":" + str.substring(12, 14);
	}
}
