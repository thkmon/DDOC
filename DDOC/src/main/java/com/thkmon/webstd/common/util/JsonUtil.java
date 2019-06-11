package com.thkmon.webstd.common.util;

public class JsonUtil {
	public static String getJsonAddingKeyValue(String preJson, String key, String value) {
		if (preJson == null || preJson.trim().length() == 0) {
			preJson = "{}";
		}
		
		StringBuffer newJson = new StringBuffer();
		
		// �� ���� �߰�ȣ�� ����.
		newJson.append(preJson.substring(0, preJson.lastIndexOf("}")));
		
		newJson.append(", ");
		newJson.append("\"");
		newJson.append(key);
		newJson.append("\"");
		newJson.append(" : ");
		newJson.append("\"");
		// newJson.append(value.replace("\r\n", "\\r\\n").replace("\"", "\\\""));
		newJson.append(value.replace("\r\n", "<br>").replace("\"", "\\\""));
		newJson.append("\"");
		
		newJson.append("}");
		
		return newJson.toString();
	}
}
