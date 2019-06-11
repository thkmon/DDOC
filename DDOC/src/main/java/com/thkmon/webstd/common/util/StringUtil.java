package com.thkmon.webstd.common.util;

public class StringUtil {
	public static String upperFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String lowerFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * 전체 파일 패스에서 슬래시와 역슬래시 뒤 쪽의 파일명+확장자만 가져온다.
	 * @param filePath
	 * @return
	 */
	public static String getFileNameOnlyFromFilePath(String filePath) {
		int lastSlashPos = filePath.lastIndexOf("/");
		if (lastSlashPos > -1) {
			filePath = filePath.substring(lastSlashPos + 1);
		}
		
		int lastBackSlashPos = filePath.lastIndexOf("\\");
		if (lastBackSlashPos > -1) {
			filePath = filePath.substring(lastBackSlashPos + 1);
		}
		
		return filePath;
	}
	
	public static String escapeNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}
	
	
	public static String parseString(Object obj) {
		if (obj == null) {
			return "";
		}
		
		try {
			return String.valueOf(obj);
			
		} catch (Exception e) {
			return "";
		}
	}
	

	public static String toPrintString(String str) {
		if (str == null) {
			return "{null}";
		}
		
		if (str.length() == 0) {
			return "{empty}";
		}
		
		str = str.replace("\t", "\\t");
		str = str.replace("\r", "\\r");
		str = str.replace("\n", "\\n");
		
		return str;
	}
	
	public static boolean isNumber(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		
		int len = str.length();
		String oneChar = "";
		for (int i=0; i<len; i++) {
			oneChar = str.substring(i, i+1);
			if (!oneChar.equals("0") &&
				!oneChar.equals("1") && 
				!oneChar.equals("2") &&
				!oneChar.equals("3") &&
				!oneChar.equals("4") &&
				!oneChar.equals("5") &&
				!oneChar.equals("6") &&
				!oneChar.equals("7") &&
				!oneChar.equals("8") &&
				!oneChar.equals("9")) {
				
				return false;
			}
		}
		
		return true;
	}
	
	public static String appendRightSide(String orginStr, int count, String strToAppend) {
		if (orginStr == null) {
			orginStr = "";
		}
		
		StringBuffer buff = new StringBuffer();
		
		// 원본 스트링 추가
		buff.append(orginStr);
		
		for (int i=0; i<count; i++) {
			buff.append(strToAppend);
		}
		
		return buff.toString();
	}
	
	
	public static String appendLeftSide(String orginStr, int count, String strToAppend) {
		if (orginStr == null) {
			orginStr = "";
		}
		
		StringBuffer buff = new StringBuffer();
		
		for (int i=0; i<count; i++) {
			buff.append(strToAppend);
		}
		
		// 원본 스트링 추가
		buff.append(orginStr);
		
		return buff.toString();
	}
	
	
	public static int parseInt(String str) {
		return parseInt(str, 0);
	}
	
	
	public static int parseInt(String str, int defaultNum) {
		
		int resultNum = defaultNum;
		
		try {
			if (str == null || str.length() == 0) {
				return defaultNum;
			}
			
			resultNum = Integer.parseInt(str);
			
		} catch (Exception e) {
			return defaultNum;
		}
		
		return resultNum;
	}
	
	
//	public static String getFileNameInPath(String path) {
//		if (path == null || path.length() == 0) {
//			return "";
//		}
//		
//		path = path.replace("/", "\\");
//		
//		int lastSlashIdx = path.lastIndexOf("\\");
//		if (lastSlashIdx > -1) {
//			return path.substring(lastSlashIdx + 1);
//		}
//		
//		return path;
//		
//	}
}