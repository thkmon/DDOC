package com.thkmon.ddoc.bin;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.oreilly.servlet.MultipartRequest;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.StringList;
import com.thkmon.webstd.common.util.DateUtil;
//import com.thkmon.webstd.common.util.FileNameMaker;
import com.thkmon.webstd.common.util.LogUtil;
import com.thkmon.webstd.common.util.StringUtil;

public class BinController {
	
	private int size = 1024 * 1024 * 100; // 100�ް�
	private String encode = "UTF-8";
	
	public BasicMap uploadBin(Connection conn, HttpServletRequest request, HttpServletResponse response, String docId) {
		
		BasicMap map = new BasicMap();
////		MultipartRequest multiReq = null;
//
//		try {
////			FileNameMaker fileNameMaker = new FileNameMaker();
////			multiReq = new MultipartRequest(request, getTempDirPath(), size, encode, fileNameMaker);
//
////			StringList originFilePathList = fileNameMaker.getOriginFilePathList();
////			StringList newFilePathList = fileNameMaker.getNewFilePathList();
//			
//			System.out.println("originFilePathList : " + originFilePathList);
//			System.out.println("newFilePathList : " + newFilePathList);
//			
//			boolean uploadBinResult = addBin(conn, originFilePathList, newFilePathList, docId);
//			
//			map.put("multiReq", multiReq);
//			
//			if (uploadBinResult) {
//				map.put("uploadBin", true);
//				map.put("originFilePathList", originFilePathList);
//				map.put("newFilePathList", newFilePathList);
//				
//			} else {
//				map.put("uploadBin", false);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return map;
	}
	
	public String reviseDocContent(BasicMap resMap, String orgDocContent) {
		
		String docContent = orgDocContent;
		
		try {
			
		// ���ε� �ߴٸ� ���� ������ �ٲ�ģ��.
//		if (uploadBinResult) {
			StringList originFilePathList = (StringList) resMap.get("originFilePathList");
			StringList newFilePathList = (StringList) resMap.get("newFilePathList");
			System.out.println("originFilePathList : " + originFilePathList);
			System.out.println("newFilePathList : " + newFilePathList);
			
			int attachCount = originFilePathList.size();
			String oneOriginFileName = "";
			String oneNewFileName = "";
			
			for (int i=0; i<attachCount; i++) {
				oneOriginFileName = originFilePathList.get(i);
				oneOriginFileName = StringUtil.getFileNameOnlyFromFilePath(oneOriginFileName);
				oneOriginFileName = "AttachPath_" + oneOriginFileName;
				
				if (docContent.indexOf(oneOriginFileName) > -1) {
					
					oneNewFileName = StringUtil.getFileNameOnlyFromFilePath(newFilePathList.get(i));
					oneNewFileName = "/bin/" + oneNewFileName.replace(".", "_");
					docContent = docContent.replace(oneOriginFileName, oneNewFileName);
				}
			}
//		}
		} catch (Exception e) {
			e.printStackTrace();
			return orgDocContent;
		}
		
		return docContent;
	}
	
	private String getTempDirPath() {
		File dir = new File("C:/");
		if (dir.exists()) {
			dir = new File("C:/ddata1/");
			
		} else {
			dir = new File("/ddata1/");
		}
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		return dir.getAbsolutePath();
	}
	
	private boolean addBin(Connection conn, StringList originFilePathList, StringList newFilePathList, String docId) throws Exception {
		
		if (originFilePathList == null || originFilePathList.size() == 0) {
			System.err.println("���ε��� ÷�������� �����ϴ�.");
			return false;
		}
		
		if (newFilePathList == null || newFilePathList.size() == 0) {
			System.err.println("���ε��� ÷�������� �����ϴ�.");
			return false;
		}
		
		if (originFilePathList.size() != newFilePathList.size()) {
			System.err.println("originFilePathList.size() != newFilePathList.size()");
			return false;
		}
		
		LogUtil logger = LogUtil.getLoggerInstance("addBin");
		
		try {
			// ��¥�� ��´�.
			String dateTime = DateUtil.getDateTime();
			
			BinMapper binMapper = new BinMapper();
			
			int count = originFilePathList.size();
			
			String originFilePath = "";
			String newFilePath = "";
			File file = null;
			
			for (int i=0; i<count; i++) {
				originFilePath = originFilePathList.get(i);
				newFilePath = newFilePathList.get(i);
				file = new File(newFilePath);
				if (!file.exists()) {
					System.err.println("������ �������� �ʽ��ϴ�. (" + originFilePath + " / " + newFilePath + ")");
					continue;
				}
				
				boolean inserted = binMapper.insertBin(conn, file, originFilePath, newFilePath, dateTime, docId);
				System.out.println("inserted : " + inserted);
				if (!inserted) {
					System.err.println("���� ���ε� �����߽��ϴ�.");
					BasicMapper.rollbackOnly(conn);
					return false;
				}
			}
			
		} catch (Exception e) {
			logger.debug("addBin�� �����Ͽ����ϴ�.");
			logger.debug(e.getMessage());
			BasicMapper.rollbackOnly(conn);
			throw e;
			
		} finally {
		}
		
		return true;
	}
	
	public InputStream getBin(String binId) {
		Connection conn = null;

		InputStream result = null;
		LogUtil logger = LogUtil.getLoggerInstance("getBin");
		
		try {
			conn = BasicMapper.getConnection();
			
			result = new BinMapper().selectBin(conn, binId);
			if (result != null) {
				return result;
			}
		
		} catch (Exception e) {
			logger.debug("getBin�� �����Ͽ����ϴ�.");
			logger.debug(e.getMessage());
			BasicMapper.rollbackAndClose(conn);
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return null;
	}
	

}
