package com.thkmon.ddoc.doc.read;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.BasicModel;
import com.thkmon.webstd.common.util.JsonUtil;
import com.thkmon.webstd.common.util.StringUtil;

@Controller
public class ReadModel extends BasicModel {
	
	
	@RequestMapping(value = "/{userId}/{docId}", method = {RequestMethod.GET, RequestMethod.POST})
	public String showReadDocPage(HttpServletRequest req, HttpServletResponse res, @PathVariable("userId") String userId, @PathVariable("docId") String docId) throws Exception {
		
		// DB에 붙어서 문서 row를 가져온다.
		BasicMapList result = new ReadDocController().readDoc(userId, docId);
		BasicMap oneDoc = result.get(0);
		
		oneDoc.setJsonBlackListKey("ip_address");
		//System.out.println(oneDoc.toJson());
		
		System.out.println(oneDoc.getKeyListText());
		
		String valid = oneDoc.getString("valid");
		if (valid == null || !valid.equals("1")) {
			throw new Exception("삭제된 문서입니다.");
		}
		
		// 키 목록 :
		// valid, update_time, regist_time, user_id, user_name, ip_address, title, doc_id, content_src, content
		
		// 본문 얻기
//		String contentSrc = oneDoc.getString("content_src");
//		String content = FileUtil.readFile(UploadUtil.getBasicFolderPath() + "/" + contentSrc);
		
		String content = oneDoc.getString("content");
//		StringBuffer contentBuffer = new StringBuffer();
//		
//		String oneChar = "";
//		boolean open = false;
//		for (int i=0; i<content.length(); i++) {
//			oneChar = content.substring(i, i+1);
//			if (oneChar.equals("<")) {
//				open = true;
//			} else if (oneChar.equals(">")) {
//				open = false;
//			}
//			
//			if (open)
//		}
		
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		
		content = content.replace(" ", "&nbsp;");
		content = content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		
		content = content.replace("'", "&#39;");
//		content = content.replace("\"", "&quot;");
		
		content = content.replace("\\", "&#92;");
		
		content = content.replace("&lt;img class", "<img onclick=\"clickedImg(this)\" style=\"max-width: 100%; width: 100%;\" class");
		content = content.replace("_jpg\"&gt;", "_jpg\">");
		
		content = content.replace("_jpg&quot;&gt;", "_jpg\">");
		
		
//		content = content.replace("\"", "22");
		
		
//		if (false) { //content.indexOf(">") > -1 || content.indexOf("<") > -1) {
//			
//			// 태그제거
//			String oneChar = "";
//			String preChar = "";
//			
//			int contentLen = content.length();
//			for (int i=0; i<contentLen; i++) {
//				oneChar = content.substring(i, i+1);
//				
//	//			if (!preChar.equals("\\") && (oneChar.equals("<") || oneChar.equals(">"))) {
//				if (!preChar.equals("\\")) {
//					
//					if (oneChar.equals("<")) {
//						contentBuffer.append("&lt;");
//						
//					} else if (oneChar.equals("<")) {
//						contentBuffer.append("&gt;");
//						
//					} else {
//						contentBuffer.append(oneChar);
//					}
//					
//					
//				} else {
//					contentBuffer.append(oneChar);
//				}
//	
//				preChar = oneChar;
//			}
//				
//		} else {
//			contentBuffer.append(content);
//		}
		
		String oneDocJson = oneDoc.toJson("update_time", "regist_time", "user_id", "user_name", "title", "doc_id", "seq_num");
		
		System.out.println("oneDocJson : " + oneDocJson);
		if (content != null && content.length() > 150) {
			System.out.println("content : " + StringUtil.toPrintString(content.substring(0, 150) + "..."));
		} else {
			System.out.println("content : " + StringUtil.toPrintString(content));
		}
		
		oneDocJson = JsonUtil.getJsonAddingKeyValue(oneDocJson, "content", content);
		System.out.println("oneDocJson : " + oneDocJson);

		// bookId 파라미터로 받기
		String paramBookId = req.getParameter("bookId");
		
		if (paramBookId == null || paramBookId.length() == 0) {
			paramBookId = null;
			
		} else if (paramBookId.equalsIgnoreCase("all")) {
			paramBookId = null;
		}

		if (paramBookId != null && paramBookId.length() > 0) {
			req.setAttribute("bookId", paramBookId);
		}
		
		// 관리자 권한 있는지 체크해서 값을 내려준다.
		String sessionUserId = String.valueOf(req.getSession().getAttribute("userId"));
		if (sessionUserId != null && sessionUserId.equals("bb_")) {
			req.setAttribute("isAdmin", "true");
			
			String modifyLinkText = "/m?docId=" + oneDoc.get("doc_id");
			req.setAttribute("isAdmin", "true");
			req.setAttribute("modifyLinkText", modifyLinkText);
		}
		
		req.setAttribute("json", oneDocJson);
		req.setAttribute("docId", oneDoc.get("doc_id"));
		req.setAttribute("seqNum", oneDoc.get("seq_num"));
//		forward("/ddoc/read/read.jsp");
		
		return "/ddoc/read/read";
	}
	
	
}