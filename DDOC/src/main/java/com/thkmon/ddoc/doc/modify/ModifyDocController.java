package com.thkmon.ddoc.doc.modify;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.ddoc.bin.BinController;

//import com.oreilly.servlet.MultipartRequest;

import com.thkmon.webstd.common.config.CommonConfig;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.LogUtil;
//import com.thkmon.webstd.common.util.UploadUtil;

public class ModifyDocController {
	
	
	public String modifyDoc(HttpServletRequest request, HttpServletResponse response, String modifyDocId) throws Exception {
		
		String returnDocId = "";
		LogUtil modifyDocLogger = LogUtil.getLoggerInstance("modifyDoc");
		
		Connection conn = null;
		
		try {
			String userId = "";
			
			// 세션에서 userId 얻는다.
			Object userIdAttribute = request.getSession().getAttribute("userId");
			if (userIdAttribute != null) {
				userId = String.valueOf(userIdAttribute);
			}
			
			if (userId == null || userId.trim().length() == 0) {
				throw new Exception("userId가 존재하지 않습니다.");
				
			} else {
				userId = userId.trim();
			}
			
			// 날짜를 얻는다.
			String dateTime = DateUtil.getDateTime();
			
			// 커넥션 얻는다.
			conn = BasicMapper.getConnection();
			
			System.out.println("userId : " + userId);
			System.out.println("modifyDocId : " + modifyDocId);
			
			BinController binCtrl = new BinController();
			BasicMap resMap = binCtrl.uploadBin(conn, request, response, modifyDocId);
//			MultipartRequest multiReq = (MultipartRequest) resMap.get("multiReq");
//			boolean uploadBinResult = (boolean) resMap.get("uploadBin");
			
			HttpServletRequest multiReq = request;
			boolean uploadBinResult = false;

			// 1. 문서정보를 얻는다.
			String docTitle = multiReq.getParameter("docTitle");
			String docContent = multiReq.getParameter("docContent");
			String secretDoc = multiReq.getParameter("secretDoc");
			if (secretDoc != null && secretDoc.equals("on")) {
				secretDoc = "1";
			} else {
				secretDoc = "0";
			}
			
			// 업로드 했다면 문서 내용을 바꿔친다.
			if (uploadBinResult) {
				docContent = binCtrl.reviseDocContent(resMap, docContent);
			}
			
			String oneBookId = multiReq.getParameter("bookId");
			
			if (oneBookId == null || oneBookId.trim().length() == 0) {
				throw new Exception("oneBookId == null || oneBookId.trim().length() == 0");
			} else {
				oneBookId = oneBookId.trim();
			}
					
			modifyDocLogger.debug("docTitle : " + docTitle);
			
			// 본문길이 제한적용
			if (docContent.length() > CommonConfig.docContentLimitlength) {
				docContent = docContent.substring(0, CommonConfig.docContentLimitlength);
				modifyDocLogger.debug("docContent : " + docContent);
			} else {
				modifyDocLogger.debug("docContent : " + docContent);
			}

			BasicMap map = new BasicMap();
			map.put("title", docTitle);
			map.put("content", docContent);
			map.put("BookId", oneBookId);
			map.put("secret", secretDoc);
			
			ModifyDocMapper mapper = new ModifyDocMapper();
			returnDocId = mapper.updateDoc(conn, map, modifyDocId, dateTime);
			
			if (returnDocId == null || returnDocId.trim().length() == 0) {
				throw new Exception("returnDocId is null or empty");
			}
			
			BasicMapper.commitAndClose(conn);
			
		} catch (Exception e) {
			modifyDocLogger.debug("문서 수정에 실패하였습니다.");
			modifyDocLogger.debug(e.getMessage());
			BasicMapper.rollbackAndClose(conn);
			// return false;
			throw e;
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return returnDocId;
	}
}