package com.thkmon.webstd.common.prototype;

import java.util.ArrayList;

public class BasicMapList extends ArrayList<BasicMap> {
	// public ArrayList<BasicMap> basicMapList = null;
	
//	public void add(BasicMap map) {
//		if (basicMapList == null) {
//			basicMapList = new ArrayList<BasicMap>();
//		}
//		
//		this.add(map);
//	}
//	
//	public BasicMap get(int index) {
//		if (basicMapList == null) {
//			return null;
//		}
//		return this.get(index);
//	}
	public int getSize() {
		return this.size();
	}
//	
//	public String toString() {
//		
//		// �������  [{}] ���
//		
//		int size = getSize();
//		StringBuffer res = new StringBuffer();
//		
//		res.append("[");
//		
//		for (int i=0; i<size; i++) {
//			res.append("{");
//			res.append(get(i));
//			res.append("},");
//		}
//		int lastComma = res.lastIndexOf(",");
//		if (lastComma >= 0) {
//			res.deleteCharAt(lastComma);	
//		}
//		
//		res.append("]");
//		
//		return res.toString();
//	}
}
