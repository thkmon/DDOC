package com.thkmon.webstd.common.util;

public class UniqueKeyUtil {
	
	/**
	 * �ð��� �̿��Ͽ� ����ũ�� Ű�� �����Ѵ�.
	 * @return
	 */
	public static String createUniqueKey() {
		return createUniqueKey("");
	}
	
	/**
	 * �ð��� �̿��Ͽ� ����ũ�� Ű�� �����Ѵ�.
	 * 
	 * @param preText
	 * @return
	 */
	public static String createUniqueKey(String preText) {
		
		// ����Ű ���ʿ� ���� Text
		if (preText == null || preText.trim().length() == 0) {
			preText = "";
		} else {
			preText = preText.trim();
		}
		
		StringBuffer uniqueKey = new StringBuffer();
		
		uniqueKey.append(preText);
		uniqueKey.append(DateUtil.getYear());
		uniqueKey.append(DateUtil.getMonth());
		uniqueKey.append(DateUtil.getDay());
		
		uniqueKey.append("_");
		
		uniqueKey.append(DateUtil.getHour());
		uniqueKey.append(DateUtil.getMinute());
		uniqueKey.append(DateUtil.getSecond());
		
		uniqueKey.append("_");
		
		uniqueKey.append(DateUtil.getMiliSecond());
		uniqueKey.append(createRandomInt3digit()); // 3�ڸ� ���� ����
		
		return uniqueKey.toString();
	}
	
	/**
	 * 1 ~ 999 ������ ������ �����Ѵ�.
	 * @return
	 */
	public static String createRandomInt3digit() {
		// 1 ~ 999 ���� ����
		int newNum = (int) (Math.random() * 999) + 1;
		String strNum = String.valueOf(newNum);
		
		if (strNum.length() == 0) {
			strNum = "000";
			
		} else if (strNum.length() == 1) {
			strNum = "00" + strNum;
			
		} else if (strNum.length() == 2) {
			strNum = "0" + strNum;
			
		} else if (strNum.length() > 3) {
			strNum = strNum.substring(0, 3);
		}
		return strNum;
	}
	
	/**
	 * ����Ű�� �����Ѵ�.
	 * @return
	 */
	public static String createErrorKey() {
		return createUniqueKey("E");
	}
}
