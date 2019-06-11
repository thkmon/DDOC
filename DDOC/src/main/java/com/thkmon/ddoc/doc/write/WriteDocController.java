package com.thkmon.ddoc.doc.write;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartRequest;

import com.thkmon.ddoc.bin.BinController;
import com.thkmon.webstd.common.config.CommonConfig;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.LogUtil;
import com.thkmon.webstd.common.util.StringUtil;

public class WriteDocController {

	public String writeDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LogUtil writeDocLogger = LogUtil.getLoggerInstance("writeDoc");
		String returnDocId = "";
		
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
			
			// 독아이디를 정한다.
			String docId = dateTime;
			
			// 커넥션 얻는다.
			conn = BasicMapper.getConnection();
						
			writeDocLogger.debug("시퀀스 넘버를 얻도록 시도한다.");
			
			// 독 시퀀스 넘버를 얻는다.
			WriteDocMapper mapper = new WriteDocMapper();
			String docSeqNum = mapper.createDocSeqNum(conn, userId);
			
			// 1. 첨부파일을 우선 업로드한다.
			writeDocLogger.debug("첨부파일을 우선 업로드한다.");
			
			BinController binCtrl = new BinController();
			BasicMap resMap = binCtrl.uploadBin(conn, request, response, docId);
			
//			MultipartRequest multiReq = (MultipartRequest) resMap.get("multiReq");
//			boolean uploadBinResult = (boolean) resMap.get("uploadBin");
			
			HttpServletRequest multiReq = request;
			boolean uploadBinResult = false;
			
			// 1. 문서정보를 얻는다.
			String docTitle = multiReq.getParameter("docTitle");
			String docContent = multiReq.getParameter("docContent");
			String bookId = multiReq.getParameter("bookId");
			String regDatetime = multiReq.getParameter("regDatetime");
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
			
			// 날짜 대입
			if (regDatetime != null) {
				regDatetime = regDatetime.replace(".", "").replace(":", "").replace("/", "").replace(" ", "");
				if (StringUtil.isNumber(regDatetime)) {
					
					if (regDatetime.length() > 14) {
						regDatetime = regDatetime.substring(0, 14);
					}
					
					if (regDatetime.length() < 14) {
						regDatetime = StringUtil.appendRightSide(regDatetime, 14 - regDatetime.length(), "0");
					}
					
					dateTime = regDatetime;
				} else {
					regDatetime = "";
				}
			}
			
			if (bookId == null || bookId.length() == 0) {
				// 초기값
				bookId = "b0";
			}
			
			// 첨부파일 명을 리얼패스로 바꾼다.
//			docContent = replaceAttachFileNameToRealPath(uploadResult, docContent, docId, dateTime, userId);

			writeDocLogger.debug("docTitle : " + docTitle);
			
			// 본문길이 제한적용
			if (docContent.length() > CommonConfig.docContentLimitlength) {
				docContent = docContent.substring(0, CommonConfig.docContentLimitlength);
				writeDocLogger.debug("docContent : " + docContent);
			} else {
				writeDocLogger.debug("docContent : " + docContent);
			}

			// 2. 문서를 만들고 docId 를 얻는다.
			writeDocLogger.debug("문서를 만들고 docId 를 얻는다.");
			
			BasicMap map = new BasicMap();
			map.put("user_id", "bb_");
			map.put("user_name", "흑곰");
			map.put("title", docTitle);
			map.put("content", docContent);
			map.put("book_id", bookId);
			map.put("secret", secretDoc);
			
			returnDocId = mapper.insertDoc(conn, map, docSeqNum, docId, dateTime);
			
			if (returnDocId == null || returnDocId.trim().length() == 0) {
				throw new Exception("returnDocId is null or empty");
			}
			
			BasicMapper.commitAndClose(conn);
			
			// 3. 본문파일을 업로드한다.
//			writeDocLogger.debug("본문파일을 업로드한다.");
//			upload.uploadContentFile(docContent, userId, returnDocId, dateTime);
			
		} catch (Exception e) {
			writeDocLogger.debug("문서 등록에 실패하였습니다.");
			writeDocLogger.debug(e.getMessage());
			BasicMapper.rollbackAndClose(conn);
			// return false;
			throw e;
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return returnDocId;
	}
	
	/**
	 * 첨부파일 명을 리얼패스로 바꾼다.
	 * 
	 * @param uploadResult
	 * @param docContent
	 * @param docId
	 * @param dateTime
	 * @param userId
	 * @return
	 */
//	public String replaceAttachFileNameToRealPath(BasicMap uploadResult, String docContent, String docId, String dateTime, String userId) {
//		
//		StringList originFilePathList = (StringList) uploadResult.get("originFilePathList");
//		if (originFilePathList == null) {
//			return docContent;
//		}
//		
//		StringList newFilePathList = (StringList) uploadResult.get("newFilePathList");
//		if (newFilePathList == null) {
//			return docContent;
//		}
//		
//		if (originFilePathList.size() != newFilePathList.size()) {
//			System.out.println("replaceAttachFileNameToRealPath ERROR : size different(originFilePathList, newFilePathList)");
//			return docContent;
//		}
//		
//		int size = originFilePathList.size();
//		
//		String originFilePath = "";
//		String originFileName = "";
//		
//		String newFilePath = "";
//		String newFileName = "";
//		
//		WriteDocMapper writeDocMapper = new WriteDocMapper();
//		
//		StringBuffer urlBuffer = null;
//		
//		for (int i=0; i<size; i++) {
//			originFilePath = originFilePathList.get(i);
//			originFileName = StringUtil.getFileNameOnlyFromFilePath(originFilePath);
//			
//			newFilePath = newFilePathList.get(i);
//			newFileName = StringUtil.getFileNameOnlyFromFilePath(newFilePath);
//			
//			System.out.println("AS-IS : " + docContent);
//			
//			// UploadUtil.createMiddleFolderName(docSeqNum)
//			
//			urlBuffer = new StringBuffer();
//			urlBuffer.append("\"");
//			urlBuffer.append("/getAtt/");
//			urlBuffer.append(userId);
//			urlBuffer.append("/");
//			// urlBuffer.append(UploadUtil.createMiddleFolderName(dateTime));
//			urlBuffer.append(docId.substring(0, 4));
//			urlBuffer.append("/");
//			urlBuffer.append(docId);
//			urlBuffer.append("/");
//			urlBuffer.append(newFileName);
//			urlBuffer.append("\"");
//			
//			docContent = docContent.replace("\"AttachPath_" + originFileName + "\"", urlBuffer.toString());
//			docContent = docContent.replace("'AttachPath_" + originFileName + "'", urlBuffer.toString());
//			
//			System.out.println("TO-BE : " + docContent);
//		}
//		
//		return docContent;
//	}
}
