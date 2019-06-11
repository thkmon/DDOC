package com.thkmon.ddoc.open;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.thkmon.ddoc.book.BookController;
import com.thkmon.ddoc.book.BookMapper;
import com.thkmon.ddoc.doc.read.ReadDocController;
import com.thkmon.ddoc.doc.read.ReadDocMapper;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.StringUtil;

public class OpenController {
	
	public String openMenuInRead(HttpServletRequest request, String docId) {
//		
		Connection conn = null;
		StringBuffer htmlBuff = new StringBuffer();
//		
		try {
			conn = BasicMapper.getConnection();
//
//			//			try {
////				VisitHistoryController visitCtl = new VisitHistoryController();
////				visitCtl.addVisitHistory(conn, request, request.getRequestURI());
////				BasicMapper.commitAndClose(conn);
////				
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
//			
			String userId = "bb_";
			
			String paramBookId = request.getParameter("bookId");
			if (paramBookId == null || paramBookId.length() == 0 || paramBookId.equalsIgnoreCase("all")) {
				paramBookId = "";
			}
			
			ReadDocMapper mapper = new ReadDocMapper();
			BasicMapList mapList = mapper.getDocListArround(conn, userId, paramBookId, docId, request);
			
//			BookController bookCtrl = new BookController();
			ReadDocController readDocCtrl = new ReadDocController();
			String html = makeStringByDocList2(mapList, docId, paramBookId);
			htmlBuff.append(html);
			
			// 최근글
			
//			BasicMapList preDocList = readDocCtrl.getPreDocList(conn, userId, paramBookId, docId);
//			BasicMapList postDocList = readDocCtrl.getPostDocList(conn, userId, paramBookId, docId);
//			
//			htmlBuff.append("<b>다른 글</b>");
//			htmlBuff.append("<table class=\"w100p basicLine basicpadding\">");
//			htmlBuff.append(makeStringByDocList(postDocList, paramBookId));
//			// htmlBuff.append("<br>");
//			
//			htmlBuff.append(makeStringByDocList(preDocList, paramBookId));
//			htmlBuff.append("</table>");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}

		return htmlBuff.toString();
	}
	
//	private String makeStringByDocList(BasicMapList preDocList, String paramBookId) {
//		
//		StringBuffer htmlBuff = new StringBuffer();
//		
//		if (preDocList == null || preDocList.size() == 0) {
//			return "";
//		}
//		
//		int docCount = preDocList.size();
//		BasicMap oneDoc = null;
//		
//		for (int k=0; k<docCount; k++) {
//			oneDoc = preDocList.get(k);
//			
//			if (oneDoc == null) {
//				continue;
//			}
//			
//			String docTitle = oneDoc.getString("title");
//			String docId = oneDoc.getString("doc_id");
//			String bookTitle = oneDoc.getString("book_title");
//			// String seqNum = oneDoc.getString("seq_num");
//			String registTime = oneDoc.getString("regist_time");
//			
////				if (k==0) {
////					htmlBuff.append("<table class=\"w100p basicLine basicpadding\"><tr><td>");
////				}
////				
////				if (paramBookId != null && paramBookId.length() > 0) {
////					htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "?bookId=" + paramBookId + "\">");
////				} else {
////					htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "\">");
////				}
////				
////				htmlBuff.append("[").append(bookTitle).append("]&nbsp;&nbsp;");
////				htmlBuff.append(docTitle);
////
////				htmlBuff.append("</a>");
////				
////				htmlBuff.append("<div style=\"font-size: 11px; float: right;\">");
////				htmlBuff.append(DateUtil.toPirntDateTime(registTime));
////				htmlBuff.append("</div>");
////				
////				htmlBuff.append("<br>");
////				
////				if (k==docCount-1) {
////					htmlBuff.append("</td></tr></table>");
////				}
////				
//			
//			if (k==0) {
////				htmlBuff.append("<table class=\"w100p basicLine basicpadding\">");
//			}
//			
//			htmlBuff.append("<tr>");
//			htmlBuff.append("<td>");
//			
//			if (paramBookId != null && paramBookId.length() > 0) {
//				htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "?bookId=" + paramBookId + "\">");
//			} else {
//				htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "\">");
//			}
//			
//			htmlBuff.append("[").append(bookTitle).append("]&nbsp;&nbsp;");
//			htmlBuff.append(docTitle);
//
//			htmlBuff.append("</a>");
//			
//			htmlBuff.append("<div style=\"font-size: 11px; float: right;\">");
//			htmlBuff.append(DateUtil.toPirntDateTime(registTime));
//			htmlBuff.append("</div>");
//			htmlBuff.append("</td></tr>");
//			
//			if (k==docCount-1) {
////				htmlBuff.append("</table>");
//			}
//		}
//		
//		return htmlBuff.toString();
//	}
	
	private String makeStringByDocList2(BasicMapList preDocList, String currentDocId, String paramBookId) {
		
		StringBuffer htmlBuff = new StringBuffer();
		
		if (preDocList == null || preDocList.size() == 0) {
			return "";
		}
		
		int docCount = preDocList.size();
		BasicMap oneDoc = null;
		
		for (int k=0; k<docCount; k++) {
			oneDoc = preDocList.get(k);
			
			if (oneDoc == null) {
				continue;
			}
			
			String docTitle = oneDoc.getString("title");
			String docId = oneDoc.getString("doc_id");
			String bookTitle = oneDoc.getString("book_title");
			String registTime = oneDoc.getString("regist_time");
			String docSecret = oneDoc.getString("secret");
			
			boolean currentDoc = false;
			if (currentDocId != null && docId != null && currentDocId.equals(docId)) {
				currentDoc = true;
			}
			
			if (k==0) {
				htmlBuff.append("<table class=\"w100p basicLine basicpadding\">");
			}
			
			htmlBuff.append("<tr>");
			htmlBuff.append("<td>");
			
			if (paramBookId != null && paramBookId.length() > 0) {
				htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "?bookId=" + paramBookId + "\">");
				
			} else {
				htmlBuff.append("<a style=\"color: #000000;\" href=\"/bb_/" + docId + "\">");
			}
			
			if (currentDoc) {
				htmlBuff.append("<b>");
			}
			
			htmlBuff.append("[").append(bookTitle).append("]&nbsp;&nbsp;");
			
			if (docSecret != null && docSecret.equals("1")) {
				htmlBuff.append("[비밀]");	
			}
			
			htmlBuff.append(docTitle);
			
			if (currentDoc) {
				htmlBuff.append("</b>");
			}
			
			htmlBuff.append("</a>");
			
			htmlBuff.append("<div style=\"font-size: 11px; float: right;\">");
			htmlBuff.append(DateUtil.toPirntDateTime(registTime));
			htmlBuff.append("</div>");
			htmlBuff.append("</td></tr>");
			
			if (k==docCount-1) {
				htmlBuff.append("</table>");
			}
		}
		
		return htmlBuff.toString();
	}
	
	
	private int basicCountInPage = 20;
	private int basicCountInNav = 10;
	
	public String openCommonDocList2(HttpServletRequest request) {
		
		Connection conn = null;
		StringBuffer htmlBuff = new StringBuffer();
		 
		try {
			
			conn = BasicMapper.getConnection();
			String userId = "bb_";
	
			BookController bookCtrl = new BookController();
			ReadDocController readDocCtrl = new ReadDocController();
			
			String paramBookId = request.getParameter("bookId");
			if (paramBookId == null || paramBookId.length() == 0) {
				paramBookId = null;
				
			} else if (paramBookId.equalsIgnoreCase("all")) {
				paramBookId = null;
			}
			
			String paramPage = request.getParameter("p");
			if (paramPage == null || paramPage.length() == 0) {
				paramPage = "1";
			}
			
			int curPage = StringUtil.parseInt(paramPage);
			
			// 최근글
			BasicMapList recentDocList = readDocCtrl.getDocList(conn, userId, paramBookId, curPage, basicCountInPage, request);
			
			if (recentDocList != null && recentDocList.size() > 0) {
				
				int docCount = recentDocList.size();
				BasicMap oneDoc = null;
				
				String hrefUrl = "";
				
				for (int k=0; k<docCount; k++) {
					oneDoc = recentDocList.get(k);
					
					if (oneDoc == null) {
						continue;
					}
					
					String docTitle = oneDoc.getString("title");
					String docId = oneDoc.getString("doc_id");
					String bookTitle = oneDoc.getString("book_title");
					String docSecret = oneDoc.getString("secret");
//					String content = oneDoc.getString("content");
//					String seqNum = oneDoc.getString("seq_num");
//					String rownum = oneDoc.getString("rnum");
					
					String registTime = oneDoc.getString("regist_time");
					
					if (k==0) {
						htmlBuff.append("<table class=\"w100p basicLine basicpadding\">");
					}
					
					htmlBuff.append("<tr>");
					htmlBuff.append("<td>");
					
					if (paramBookId != null && paramBookId.length() > 0) {
						hrefUrl = "/bb_/" + docId + "?bookId=" + paramBookId;
					} else {
						hrefUrl = "/bb_/" + docId;
					}
					htmlBuff.append("<a style=\"color: #000000;\" href=\"" + hrefUrl + "\">");
					
					htmlBuff.append("[").append(bookTitle).append("]&nbsp;&nbsp;");
					
					if (docSecret != null && docSecret.equals("1")) {
						htmlBuff.append("[비밀]");	
					}
					
					htmlBuff.append(docTitle);

					htmlBuff.append("</a>");
					
					htmlBuff.append("<div style=\"font-size: 11px; float: right;\">");
					htmlBuff.append(DateUtil.toPirntDateTime(registTime));
					htmlBuff.append("</div>");
					htmlBuff.append("</td></tr>");
					
					if (k==docCount-1) {
						htmlBuff.append("</table>");
					}
				}
			}
			
			ReadDocMapper readDocMapper = new ReadDocMapper();
			BasicMapList docCount = readDocMapper.selectDocCount(conn, userId, paramBookId, basicCountInPage);
			if (docCount != null && docCount.size() > 0) {
				String maxRowNum = docCount.get(0).getString("max_row_num");
				
				if (maxRowNum == null || maxRowNum.equalsIgnoreCase("null") || maxRowNum.length() == 0) {
					maxRowNum = "0";
				}
				
				int maxRowCount = StringUtil.parseInt(maxRowNum);
				int maxPageCount = (int)(Math.ceil((double) maxRowCount / basicCountInPage));
				
				for (int i=1; i<maxPageCount + 1; i++) {
					htmlBuff.append("<div ");
					
					if (i == curPage) {
						htmlBuff.append(" class=\"pageBox selectedPageBox\" ");
					} else {
						htmlBuff.append(" class=\"pageBox\" ");
					}
					
					htmlBuff.append(" onclick=\"changePage(").append(i).append(")\">");
					htmlBuff.append(i).append("</div>").append("&nbsp;");
				}
				
				htmlBuff.append("<br/>");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return htmlBuff.toString();
	}
	
	public String openBookList(HttpServletRequest request) {
		
		Connection conn = null;
		StringBuffer htmlBuff = new StringBuffer();
		
		boolean foundBookIdInComboBox = false;
		
		try {
	
			String paramBookId = request.getParameter("bookId");
			if (paramBookId == null || paramBookId.length() == 0 || paramBookId.equalsIgnoreCase("all")) {
				paramBookId = "";
				foundBookIdInComboBox = true;
			}
			
			conn = BasicMapper.getConnection();
			String userId = "bb_";
	
			BookMapper bookMapper = new BookMapper();
			BasicMapList bookList = bookMapper.selectBookOrderByUpdateTime(conn, userId);
			
			if (bookList == null || bookList.size() == 0) {
				return "등록되어 있는 책이 없습니다.";
			}
			
			htmlBuff.append("<table class=\"w100p\"><tr><td>");
			htmlBuff.append("<select id=\"bookListCombo\" onchange=\"changeBook()\">");
			
			htmlBuff.append("<option id=\"all\" name=\"all\" onchange=\"changeBook()\">전체 글</option>");
			
			BasicMap oneMap = null;
			String oneBookId = null;
			String oneTitle = null;
			
			int bookCount = bookList.size();
			for (int i=0; i<bookCount; i++) {
				oneMap = bookList.get(i);
				oneBookId = oneMap.getString("book_id");
				oneTitle = oneMap.getString("title");
				
				if (oneBookId == null || oneBookId.length() == 0) {
					continue;
				}
				
				if (oneTitle == null || oneTitle.length() == 0) {
					continue;
				}
				
				if (paramBookId != null && paramBookId.equals(oneBookId)) {
					htmlBuff.append("<option id=\"").append(oneBookId).append("\" name=\"").append(oneBookId).append("\" selected>");
					foundBookIdInComboBox = true;
				} else {
					htmlBuff.append("<option id=\"").append(oneBookId).append("\" name=\"").append(oneBookId).append("\">");
				}
				
				htmlBuff.append(oneTitle);
				htmlBuff.append("</option>");
			}
			
			htmlBuff.append("</select>");
			htmlBuff.append("</td></tr></table>");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		if (foundBookIdInComboBox == false) {
			return "";
		}
		return htmlBuff.toString();
	}
}
