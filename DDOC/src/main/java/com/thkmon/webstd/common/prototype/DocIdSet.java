//package com.thkmon.webstd.common.prototype;
//
//import java.io.File;
//import java.util.Random;
//
//import com.thkmon.webstd.common.util.DateUtil;
//import com.thkmon.webstd.common.util.UploadUtil;
//
//public class DocIdSet {
//	
//	// quotient : ��
//	// remainder : ������
//	// scale : ����
//	
//	private static final int SCALE = 26;
//	private String docId = "";
//	private String yearMonth = "";
//	private String absoluteContentPath = "";
//	
//	private Random random = new Random();
//	
//	public String getDocId() {
//		return docId;
//	}
////	public void setDocId(String docId) {
////		this.docId = docId;
////	}
//	public String getYearMonth() {
//		return yearMonth;
//	}
////	public void setYearMonth(String yearMonth) {
////		this.yearMonth = yearMonth;
////	}
//	
//	public String getAbsoluteContentPath() {
//		return absoluteContentPath;
//	}
////	public void setAbsoluteContentPath(String absoluteContentPath) {
////		this.absoluteContentPath = absoluteContentPath;
////	}
//
//	/**
//	 * 5�ڸ� ���� ���� ����
//	 * @return
//	 */
//	public String create5digitRandom() {
//		int ran = random.nextInt(99999);
//		
//		StringBuffer buff = new StringBuffer();
//		
//		if (ran < 10) {
//			buff.append("0000");
//			
//		} else if (ran < 100) {
//			buff.append("000");
//			
//		} else if (ran < 1000) {
//			buff.append("00");
//			
//		} else if (ran < 10000) {
//			buff.append("0");
//			
//		}
//		
//		buff.append(String.valueOf(ran));
//		
//		return buff.toString();
//	}
//	
//	/**
//	 * 4�ڸ� ���� ���� ����
//	 * @return
//	 */
//	public String create4digitRandom() {
//		int ran = random.nextInt(9999);
//		
//		StringBuffer buff = new StringBuffer();
//		
//		if (ran < 10) {
//			buff.append("000");
//			
//		} else if (ran < 100) {
//			buff.append("00");
//			
//		} else if (ran < 1000) {
//			buff.append("0");
//			
//		}
//		
//		buff.append(String.valueOf(ran));
//		
//		return buff.toString();
//	}
//	
////	public String create3digitRandom() {
////		int ran = random.nextInt(999);
////		
////		StringBuffer buff = new StringBuffer();
////		
////		if (ran < 10) {
////			buff.append("00");
////			
////		} else if (ran < 100) {
////			buff.append("0");
////			
////		}
////		
////		buff.append(String.valueOf(ran));
////		
////		return buff.toString();
////	}
//	
//	
//	/**
//	 * �� ���̵� ����
//	 * @return
//	 */
//	public boolean createDocId() {
//		
//		String newDocId = "";
//		String newYearMonth = "";
//		
//		while (true) {
//			
//			try {
//				newYearMonth = DateUtil.getYearMonth();
//				
//				StringBuffer dateTime = new StringBuffer();
//				dateTime.append(newYearMonth);
//				dateTime.append(DateUtil.getDay());
//				dateTime.append(DateUtil.getHour());
//				dateTime.append(DateUtil.getMinute());
//				dateTime.append(DateUtil.getSecond());
//				dateTime.append(DateUtil.getMiliSecond());
//				
//				dateTime.append(random.nextInt(9) + 1);
//				dateTime.append(create4digitRandom());
//				dateTime.append(create4digitRandom());
//				dateTime.append(create4digitRandom());
//				
//				newDocId = encodeUniqueId(dateTime.toString().substring(0, 15)) + "_" + encodeUniqueId(dateTime.toString().substring(15, 30));
//				
//				File file = new File(UploadUtil.getRepositoryFolder("content") + "/" + newDocId + ".txt");
//				if (file.exists()) {
//					continue;
//				} else {
//					file.createNewFile();
//					absoluteContentPath = file.getAbsolutePath().replace("\\", "/");
//					System.out.println("���� ���� ���� : " + absoluteContentPath);
//					break;
//				}
//			
//			} catch (Exception e) {
//				try {
//					Thread.sleep(10);
//				} catch (Exception ie) {}
//				
//				continue;
//			}
//		}
//		
//		this.docId = newDocId;
//		this.yearMonth = newYearMonth;
//		
//		return true;
//	}
//	
//	/**
//	 * 26������ ���ڵ� (��, ���ڰ� ���� ����. 0 == a, 1 == b ... 26 == z)
//	 * @param dateTime
//	 * @return
//	 * @throws Exception
//	 */
//	public static String encodeUniqueId(String dateTime) throws Exception {
//		
//		long lDateTime = 0;
//		
//		if (dateTime == null || dateTime.trim().length() == 0) {
//			throw new Exception("encodeUniqueId : dateTime is null or empty");
////			StringBuffer buff = new StringBuffer();
////			buff.append(DateUtil.getYear());
////			buff.append(DateUtil.getMonth());
////			buff.append(DateUtil.getDay());
////			buff.append(DateUtil.getHour());
////			buff.append(DateUtil.getMinute());
////			buff.append(DateUtil.getSecond());
////			dateTime = buff.toString();
//		}
//
//		lDateTime = Long.parseLong(dateTime);
//		
//		StringBuffer result = new StringBuffer();
//		
//		long quotient = lDateTime;
//		int remainder = -1;
//		
//		while(true) {
//			remainder = (int)(quotient % SCALE);
//			quotient = (quotient - remainder) / SCALE;
//			
//			if (remainder > -1) {
//				result.append((char)(remainder + 97));
//			}
//			
//			if (quotient < SCALE) {
//				result.append((char)(quotient + 97));
//				break;
//			}
//		}
//		
//		return result.reverse().toString();
//	}
//	
//	/**
//	 * 26������ ���ڵ� (��, ���ڰ� ���� ����. 0 == a, 1 == b ... 26 == z)
//	 * @param uniqueId
//	 * @return
//	 * @throws Exception
//	 */
//	public static String decodeUniqueId(String uniqueId) throws Exception {
//		
//		if (uniqueId == null || uniqueId.trim().length() == 0) {
//			throw new Exception("decodeUniqueId : uniqueId is null or empty");
//		}
//		
//		long result = 0;
//		int len = uniqueId.length();
//		
//		char[] chArray = uniqueId.toCharArray();
//		int oneNum = 0;
//		char oneChar = 0;
//		
//		for (int i=0; i<len; i++) {
//			oneChar = chArray[i];
//			oneNum = ((int)oneChar) - 97;
//			
//			result += power(len - i - 1) * oneNum;
//			
//		}
//		
//		return String.valueOf(result);
//	}
//	
//	/**
//	 * ���� ���ϱ�
//	 * @param exponent
//	 * @return
//	 */
//	public static long power(long exponent) {
//
//		// exponent : ����
//		
//		if (exponent == 0) {
//			// 0��
//			return 1;
//		}
//
//		long scaleMultiply = 1;
//		
//		for(int i=0; i<exponent; i++) {
//			scaleMultiply = scaleMultiply * SCALE;
//		}
//		
//		return scaleMultiply;
//	}
//}
