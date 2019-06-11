package com.thkmon.webstd.common.prototype;

import java.util.HashMap;
import java.util.Iterator;

public class BasicMap extends HashMap<String, Object> {
	
	private StringList BlackListKey = null;
	
	public Object put(String key, Object obj) {
		return super.put(key.toLowerCase(), obj);
	}
	
	public void setString(String key, String strValue) {
		super.put(key.toLowerCase(), strValue);
	}
	
	public Object get(String key) {
		key = key.toLowerCase();
        return super.get(key);
    }
	
	public String getString(String key) {
		try {
			return String.valueOf(this.get(key.toLowerCase()));
		} catch (Exception e) {
			return "";
		}
    }
	
	public String getNotEmptyString(String key) throws Exception {
		try {
			key = key.toLowerCase();
			
			Object obj = super.get(key);
			if (obj == null) {
				throw new Exception("getNotNullString : value is null. key is [" + key + "]");
			}
			
			String val = String.valueOf(obj);
			if (val.trim().length() == 0) {
				throw new Exception("getNotNullString : value is empty. key is [" + key + "]");
			}
			
			return val;
			
		} catch (Exception e) {
			throw e;
		}
    }
	
	/**
	 * ���ϴ� key���� ������� key-value �� ��µǴ� json�� ���� �����Ѵ�.
	 * 
	 * @param keys
	 * @return
	 */
	public String toJson(String... keys) {
		if (keys == null || keys.length == 0) {
			return "{}";
		}
		
		StringBuffer content = new StringBuffer();
		
		content.append("{");
		
		String oneKey = "";
		
		int keyCount = keys.length;
		for (int i=0; i<keyCount; i++) {
			
			oneKey = keys[i];
			
			if (oneKey == null || oneKey.trim().length() == 0) {
				continue;
				
			} else {
				oneKey = oneKey.trim();
			}
			
			content.append("\"");
			content.append(oneKey.replace("\"", ""));
			content.append("\"");
			content.append(" : ");
			content.append("\"");
			content.append(this.get(oneKey).toString().replace("\"", ""));
			content.append("\"");
			content.append(", ");
		}
		
		int lastComma = content.lastIndexOf(", ");
		content.deleteCharAt(lastComma);
		content.deleteCharAt(lastComma);
		
		content.append("}");
		
		return content.toString();
		
	}
	
	/**
	 * ��� key�� ������� key-value �� ��µǴ� json�� ���� �����Ѵ�.
	 * ��, ������Ʈ�� ���ܵȴ�.
	 * 
	 * @return
	 */
	public String toJson() {
		if (this.keySet() == null) {
			return "{}";
		}
		
		StringBuffer content = new StringBuffer();
		
		content.append("{");
		
		String oneKey = "";
		
		Iterator<String> iter = keySet().iterator();
		while (iter.hasNext()) {
			oneKey = iter.next();
			if (oneKey == null || oneKey.length() == 0) {
				continue;
			}
			
			boolean isBlackList = false;
			
			if (BlackListKey != null && BlackListKey.size() > 0) {
				for(int k=0; k<BlackListKey.size(); k++) {
					if (BlackListKey.get(k).equals(oneKey)) {
						isBlackList = true;
						break;
					}
				}
			}
			
			if (isBlackList) {
				continue;
			}
			
			content.append("\"");
			content.append(oneKey.replace("\"", ""));
			content.append("\"");
			content.append(" : ");
			content.append("\"");
			content.append(this.get(oneKey).toString().replace("\"", ""));
			content.append("\"");
			content.append(", ");
		}
		
		int lastComma = content.lastIndexOf(", ");
		content.deleteCharAt(lastComma);
		content.deleteCharAt(lastComma);
		
		content.append("}");
		
		return content.toString();
	}
	
	/**
	 * Ư�� Ű�� ������Ʈ�� �����Ѵ�.
	 * ������Ʈ�� ������ Ű�� toJson�� �� �� ���ܵȴ�.
	 * 
	 * @param blackKey
	 * @return
	 */
	public boolean setJsonBlackListKey(String blackKey) {
		if (blackKey == null || blackKey.trim().length() == 0) {
			return false;
			
		} else {
			blackKey = blackKey.trim();
		}
		
		if (BlackListKey == null) {
			BlackListKey = new StringList();
		}
		
		BlackListKey.add(blackKey);
		return true;
	}
	
	/**
	 * ��� key�� ��ǥ�� delimeter�� �Ͽ� �ؽ�Ʈ�� �����.
	 * @return
	 */
	public String getKeyListText() {
		
		StringBuffer keyListText = new StringBuffer();
		
		String oneKey = "";
		
		Iterator<String> iter = keySet().iterator();
		while (iter.hasNext()) {
			oneKey = iter.next();
			if (oneKey == null || oneKey.length() == 0) {
				continue;
			}
			
			keyListText.append(oneKey);
			keyListText.append(", ");
		}
		
		int lastComma = keyListText.lastIndexOf(", ");
		keyListText.deleteCharAt(lastComma);
		keyListText.deleteCharAt(lastComma);
		
		return keyListText.toString();
	}
}
