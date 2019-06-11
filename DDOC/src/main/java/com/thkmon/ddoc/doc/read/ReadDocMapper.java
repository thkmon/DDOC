package com.thkmon.ddoc.doc.read;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.prototype.StringList;
import com.thkmon.webstd.common.util.StringUtil;

public class ReadDocMapper {
	public BasicMapList selectDoc (Connection conn, String userIdParam, String docId) throws Exception {
		
		BasicMapList result = null;
		
		try {
			ObjList strList = new ObjList();
			strList.add(docId);
			strList.add(userIdParam);
			
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" seq_num      , ");
			selectQuery.append(" doc_id      , ");
			selectQuery.append(" user_id     , ");
			selectQuery.append(" user_name   , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" content     , ");
			selectQuery.append(" content_src , ");
			selectQuery.append(" ip_address  , ");
			selectQuery.append(" valid       , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time , ");
			selectQuery.append(" secret      , ");
			selectQuery.append(" book_id       ");
			
			selectQuery.append(" FROM bbDoc WHERE doc_id = ? AND user_id = ? ");
			
			BasicMapper mapper = new BasicMapper();
			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public BasicMapList selectDocList (Connection conn, String userIdParam, String bookId, boolean hasLimit) throws Exception {
		
		BasicMapList result = null;
		
		try {
			
			
			ObjList strList = new ObjList();
			
			if (bookId != null && bookId.length() > 0) {
				strList.add(bookId);				
			}
			
			strList.add(userIdParam);
			
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" seq_num      , ");
			selectQuery.append(" doc_id      , ");
			selectQuery.append(" user_id     , ");
			selectQuery.append(" user_name   , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" content     , ");
			selectQuery.append(" content_src , ");
			selectQuery.append(" ip_address  , ");
			selectQuery.append(" valid       , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time , ");
			selectQuery.append(" book_id,       ");
			selectQuery.append(" @rownum:=@rownum+1 AS rnum ");
			
			selectQuery.append(" FROM (       ");
			
				selectQuery.append(" SELECT ");
				
				selectQuery.append(" seq_num     , ");
				selectQuery.append(" doc_id      , ");
				selectQuery.append(" user_id     , ");
				selectQuery.append(" user_name   , ");
				selectQuery.append(" title       , ");
				selectQuery.append(" content     , ");
				selectQuery.append(" content_src , ");
				selectQuery.append(" ip_address  , ");
				selectQuery.append(" valid       , ");
				selectQuery.append(" regist_time , ");
				selectQuery.append(" update_time , ");
				selectQuery.append(" book_id,       ");
				selectQuery.append(" @rownum := 0 rnum ");
				
				selectQuery.append(" FROM bbDoc WHERE ");
				
				if (bookId != null && bookId.length() > 0) {
					selectQuery.append(" book_id = ? AND ");
				}
				
				selectQuery.append(" user_id = ? ");
				
				selectQuery.append(" ORDER BY LPAD(seq_num, '6','0') desc ");
				
			selectQuery.append(" ) aaa ");
			
			if (hasLimit) {
				selectQuery.append(" WHERE @rownum < 6 ");
			}
			
			BasicMapper mapper = new BasicMapper();

			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
//	public BasicMapList selectDocListArround (Connection conn, String userIdParam, String bookId, String docId, boolean preList) throws Exception {
//		
//		BasicMapList result = null;
//		
//		try {
//			
//			
//			ObjList strList = new ObjList();
//
//			StringBuffer selectQuery = new StringBuffer();
//			
////			selectQuery.append(" SELECT ");
////			
////			selectQuery.append(" seq_num      , ");
////			selectQuery.append(" int_seq_num      , ");
////			selectQuery.append(" doc_id      , ");
////			selectQuery.append(" user_id     , ");
////			selectQuery.append(" user_name   , ");
////			selectQuery.append(" title       , ");
////			selectQuery.append(" content     , ");
////			selectQuery.append(" content_src , ");
////			selectQuery.append(" ip_address  , ");
////			selectQuery.append(" valid       , ");
////			selectQuery.append(" regist_time , ");
////			selectQuery.append(" update_time , ");
////			selectQuery.append(" book_title  , ");
////			selectQuery.append(" book_id     , ");
////			selectQuery.append(" @rownum:=@rownum+1 AS rnum ");
////			
////			selectQuery.append(" FROM (       ");
////			
////				selectQuery.append(" SELECT ");
////				
////				selectQuery.append(" seq_num     , ");
////				selectQuery.append(" doc_id      , ");
////				selectQuery.append(" user_id     , ");
////				selectQuery.append(" user_name   , ");
////				selectQuery.append(" title       , ");
////				selectQuery.append(" content     , ");
////				selectQuery.append(" content_src , ");
////				selectQuery.append(" ip_address  , ");
////				selectQuery.append(" valid       , ");
////				selectQuery.append(" regist_time , ");
////				selectQuery.append(" update_time , ");
////				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
////				selectQuery.append(" book_id,       ");
////				selectQuery.append(" CAST(seq_num AS SIGNED) int_seq_num, ");
////				selectQuery.append(" @rownum := 0 rnum ");
////				
////				selectQuery.append(" FROM bbDoc doc ");
////				selectQuery.append(" WHERE ");
////				selectQuery.append(" user_id = ? ");
////				strList.add(userIdParam);
////				
////				selectQuery.append(" AND valid = '1' ");
////				selectQuery.append(" AND secret = '0' ");
////				
////				if (bookId != null && bookId.length() > 0) {
////					selectQuery.append(" AND book_id = ? ");
////					strList.add(bookId);
////				}
////				
////				selectQuery.append(" ORDER BY LPAD(seq_num, '6','0') asc ");
////				
////			selectQuery.append(" ) aaa ");
////			
////			if (!preList) {
////				// ������
////				selectQuery.append(" WHERE ? < int_seq_num AND ? > int_seq_num - 5 ");
////				strList.add(axisSeqNum);
////				strList.add(axisSeqNum);
////				
////			} else {
////				// ������
////				selectQuery.append(" WHERE ? > int_seq_num AND ? < int_seq_num + 5 ");
////				strList.add(axisSeqNum);
////				strList.add(axisSeqNum);
////			}
//			
//			selectQuery.append(" SELECT ");
//			
//			selectQuery.append(" @rownum:=@rownum+1 AS rnum, ");
//			selectQuery.append(" seq_num      , ");
//			selectQuery.append(" doc_id      , ");
//			selectQuery.append(" user_id     , ");
//			selectQuery.append(" user_name   , ");
//			selectQuery.append(" title       , ");
//			selectQuery.append(" content     , ");
//			selectQuery.append(" content_src , ");
//			selectQuery.append(" ip_address  , ");
//			selectQuery.append(" valid       , ");
//			selectQuery.append(" regist_time , ");
//			selectQuery.append(" update_time , ");
//			selectQuery.append(" book_title  , ");
//			selectQuery.append(" book_id      ");
//			
//			selectQuery.append(" FROM (       ");
//			
//				selectQuery.append(" ( SELECT ");
//				
//				selectQuery.append(" seq_num     , ");
//				selectQuery.append(" doc_id      , ");
//				selectQuery.append(" user_id     , ");
//				selectQuery.append(" user_name   , ");
//				selectQuery.append(" title       , ");
//				selectQuery.append(" content     , ");
//				selectQuery.append(" content_src , ");
//				selectQuery.append(" ip_address  , ");
//				selectQuery.append(" valid       , ");
//				selectQuery.append(" regist_time , ");
//				selectQuery.append(" update_time , ");
//				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
//				selectQuery.append(" book_id       ");
//				
//				selectQuery.append(" FROM bbDoc doc ");
//				selectQuery.append(" WHERE ");
//				selectQuery.append(" user_id = ? ");
//				strList.add(userIdParam);
//				
//				selectQuery.append(" AND valid = '1' ");
//				selectQuery.append(" AND secret = '0' ");
//				
//				if (bookId != null && bookId.length() > 0) {
//					selectQuery.append(" AND book_id = ? ");
//					strList.add(bookId);
//				}
//				
//				// ������
//				selectQuery.append(" AND doc_id > ? ");
//				strList.add(docId);
//				selectQuery.append(" ORDER BY LPAD(seq_num, '6','0') desc ) ");
//				
//				selectQuery.append(" UNION ");
//				
//				selectQuery.append(" ( SELECT ");
//				
//				selectQuery.append(" seq_num     , ");
//				selectQuery.append(" doc_id      , ");
//				selectQuery.append(" user_id     , ");
//				selectQuery.append(" user_name   , ");
//				selectQuery.append(" title       , ");
//				selectQuery.append(" content     , ");
//				selectQuery.append(" content_src , ");
//				selectQuery.append(" ip_address  , ");
//				selectQuery.append(" valid       , ");
//				selectQuery.append(" regist_time , ");
//				selectQuery.append(" update_time , ");
//				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
//				selectQuery.append(" book_id       ");
//				
//				selectQuery.append(" FROM bbDoc doc ");
//				selectQuery.append(" WHERE ");
//				selectQuery.append(" user_id = ? ");
//				strList.add(userIdParam);
//				
//				selectQuery.append(" AND valid = '1' ");
//				selectQuery.append(" AND secret = '0' ");
//				
//				if (bookId != null && bookId.length() > 0) {
//					selectQuery.append(" AND book_id = ? ");
//					strList.add(bookId);
//				}
//				
//				// ������
//				selectQuery.append(" AND doc_id < ? ");
//				strList.add(docId);
//				selectQuery.append(" ORDER BY LPAD(seq_num, '6','0') desc ) ");
//				
//			selectQuery.append(" ) aaa where @rownum < 3; ");
//			
//			
////			select @rownum:=@rownum+1 AS rnum, parDoc.* from
////			        (select * from bbDoc WHERE doc_id > '20170208091543' ORDER BY LPAD(seq_num, '6','0') asc) parDoc where @rownum < 5;
//			
//			BasicMapper mapper = new BasicMapper();
//
//			result = mapper.select(conn, selectQuery.toString(), strList);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//		
//		return result;
//	}
//	
//	public BasicMapList getRowNumber(Connection conn, String userIdParam, String bookId, String docId) throws Exception {
//		
//		BasicMapList result = null;
//		
//		try {
//			ObjList strList = new ObjList();
//
//			StringBuffer selectQuery = new StringBuffer();
//			
//			selectQuery.append(" SELECT ");
//			
//			selectQuery.append(" rnum, doc_id ");
//			
//			selectQuery.append(" FROM (       ");
//				selectQuery.append(" SELECT ");
//				selectQuery.append("   @rownum:=@rownum+1 AS rnum , "); 
//				selectQuery.append("   doc_id ");
//				selectQuery.append(" FROM bbDoc doc ");
//				selectQuery.append(" WHERE ");
//				selectQuery.append(" user_id = ? ");
//				strList.add(userIdParam);
//				
//				selectQuery.append(" AND valid = '1' ");
//				selectQuery.append(" AND secret = '0' ");
//				
//				if (bookId != null && bookId.length() > 0) {
//					selectQuery.append(" AND book_id = ? ");
//					strList.add(bookId);
//				}
//				
//				selectQuery.append(" ORDER BY regist_time desc ");
//				
//			selectQuery.append(" ) aaa ");
//			selectQuery.append(" WHERE doc_id = ? ");
//			strList.add(docId);
//			
//			BasicMapper mapper = new BasicMapper();
//
//			result = mapper.select(conn, selectQuery.toString(), strList);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//		
//		return result;
//	}
	
	public BasicMapList getDocListArround(Connection conn, String userIdParam, String bookId, String docId, HttpServletRequest request) throws Exception {
		
		boolean hideSecretDoc = true;
		String sessionUserId = String.valueOf(request.getSession().getAttribute("userId"));
		if (sessionUserId != null && sessionUserId.equals("bb_")) {
			hideSecretDoc = false;
		}
		
		BasicMapList result = null;
		
		try {
			ObjList strList = new ObjList();

			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" rnum, doc_id ");
			
			selectQuery.append(" FROM (       ");
				selectQuery.append(" SELECT ");
				selectQuery.append("   @rownum:=@rownum+1 AS rnum , "); 
				selectQuery.append("   doc_id ");
				selectQuery.append(" FROM bbDoc doc ");
				selectQuery.append(" WHERE ");
				selectQuery.append(" user_id = ? ");
				strList.add(userIdParam);
				
				selectQuery.append(" AND valid = '1' ");
				if (hideSecretDoc) {
					selectQuery.append(" AND secret = '0' ");
				}
				
				if (bookId != null && bookId.length() > 0) {
					selectQuery.append(" AND book_id = ? ");
					strList.add(bookId);
				}
				
				selectQuery.append(" ORDER BY regist_time desc ");
				
			selectQuery.append(" ) aaa ");
			selectQuery.append(" WHERE doc_id = ? ");
			strList.add(docId);
			
			BasicMapper mapper = new BasicMapper();
			BasicMapList result1 = mapper.select(conn, selectQuery.toString(), strList);
			
			// �ʱ�ȭ
			selectQuery = new StringBuffer();
			strList = new ObjList();
			 
			String axisRnum = result1.get(0).getString("rnum");
			int axisRnumInt = StringUtil.parseInt(axisRnum);
			
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" rnum,         ");
			selectQuery.append(" doc_id      , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" valid       , ");
			selectQuery.append(" book_title  , ");
			selectQuery.append(" secret      , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time   ");
			
			selectQuery.append(" FROM (       ");
				selectQuery.append(" SELECT ");
				selectQuery.append("   @rownum:=@rownum+1 AS rnum , "); 
				
				selectQuery.append(" doc_id      , ");
				selectQuery.append(" title       , ");
				selectQuery.append(" valid       , ");
				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
				selectQuery.append(" secret      , ");
				selectQuery.append(" regist_time , ");
				selectQuery.append(" update_time ");
				
				selectQuery.append(" FROM bbDoc doc ");
				selectQuery.append(" WHERE ");
				selectQuery.append(" user_id = ? ");
				strList.add(userIdParam);
				
				selectQuery.append(" AND valid = '1' ");
				if (hideSecretDoc) {
					selectQuery.append(" AND secret = '0' ");
				}
				
				if (bookId != null && bookId.length() > 0) {
					selectQuery.append(" AND book_id = ? ");
					strList.add(bookId);
				}
				
				selectQuery.append(" ORDER BY regist_time desc ");
				
			selectQuery.append(" ) aaa ");
			selectQuery.append(" WHERE ? < rnum and rnum < ? ");
			strList.add(axisRnumInt - 3);
			strList.add(axisRnumInt + 3);
			
			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	
	
	
	public BasicMapList selectDocList (Connection conn, String userIdParam, String bookId, int pageNum, int countInPage, HttpServletRequest request) throws Exception {
		
		BasicMapList result = null;
		
		try {
			
			boolean hideSecretDoc = true;
			String sessionUserId = String.valueOf(request.getSession().getAttribute("userId"));
			if (sessionUserId != null && sessionUserId.equals("bb_")) {
				hideSecretDoc = false;
			}
			
			int beginPage = (pageNum - 1) * countInPage + 1;
			int endPage = pageNum * countInPage;
			
			ObjList strList = new ObjList();
			strList.add(userIdParam);
			
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" seq_num     , ");
			selectQuery.append(" doc_id      , ");
			selectQuery.append(" user_id     , ");
			selectQuery.append(" user_name   , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" content     , ");
			selectQuery.append(" content_src , ");
			selectQuery.append(" ip_address  , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time , ");
			selectQuery.append(" secret      , ");
			selectQuery.append(" book_id     , ");
			selectQuery.append(" book_title  , ");
			selectQuery.append(" hidden_menu  , ");
			selectQuery.append(" rnum          ");
			
			selectQuery.append(" FROM (       ");
			
				selectQuery.append(" SELECT ");
				
				selectQuery.append(" doc.seq_num     , ");
				selectQuery.append(" doc.doc_id      , ");
				selectQuery.append(" doc.user_id     , ");
				selectQuery.append(" doc.user_name   , ");
				selectQuery.append(" doc.title       , ");
				selectQuery.append(" doc.content     , ");
				selectQuery.append(" doc.content_src , ");
				selectQuery.append(" doc.ip_address  , ");
				selectQuery.append(" doc.regist_time , ");
				selectQuery.append(" doc.update_time , ");
				selectQuery.append(" doc.secret,      ");
				selectQuery.append(" doc.book_id,      ");
				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
				selectQuery.append(" (SELECT hidden_menu FROM bbBook WHERE book_id = doc.book_id) hidden_menu , ");

				selectQuery.append(" @rownum:=@rownum+1 rnum ");
				selectQuery.append(" FROM bbDoc doc ");
				
				selectQuery.append(" WHERE ");
				
				selectQuery.append(" doc.valid = '1' ");
				
				if (hideSecretDoc) {
					selectQuery.append(" AND doc.secret = '0' ");					
				}
				
				selectQuery.append(" AND doc.user_id = ? ");
				
				
				if (bookId != null && bookId.length() > 0) {
					selectQuery.append(" AND doc.book_id = ? ");
					strList.add(bookId);
					
				} else {
					selectQuery.append(" AND (SELECT hidden_menu FROM bbBook WHERE book_id = doc.book_id) = '0' ");
				}
				
				selectQuery.append(" ORDER BY regist_time desc ");
				
			selectQuery.append(" ) aaa ");
			selectQuery.append(" WHERE ").append(beginPage).append(" <= rnum AND rnum <= ").append(endPage);
			
			BasicMapper mapper = new BasicMapper();

			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public BasicMapList selectDocCount (Connection conn, String userIdParam, String bookId, int countInPage) throws Exception {
		
		BasicMapList result = null;
		
		try {
			ObjList strList = new ObjList();
			strList.add(userIdParam);
			
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT ");
			selectQuery.append(" max(rnum) max_row_num     ");
			
			selectQuery.append(" FROM (       ");
			
				selectQuery.append(" SELECT ");
				
				selectQuery.append(" seq_num     , ");
				selectQuery.append(" doc_id      , ");
				selectQuery.append(" user_id     , ");
				selectQuery.append(" user_name   , ");
				selectQuery.append(" title       , ");
				selectQuery.append(" content     , ");
				selectQuery.append(" content_src , ");
				selectQuery.append(" ip_address  , ");
				selectQuery.append(" regist_time , ");
				selectQuery.append(" update_time , ");
				selectQuery.append(" book_id,      ");
				selectQuery.append(" (SELECT title FROM bbBook WHERE book_id = doc.book_id) book_title , ");
				selectQuery.append(" (SELECT hidden_menu FROM bbBook WHERE book_id = doc.book_id) hidden_menu , ");
				
				selectQuery.append(" @rownum:=@rownum+1 rnum ");
				
				selectQuery.append(" FROM bbDoc doc ");
				selectQuery.append(" WHERE valid = '1' ");
				selectQuery.append(" AND secret = '0' ");
				selectQuery.append(" AND user_id = ? ");
				
				
				if (bookId != null && bookId.length() > 0) {
					selectQuery.append(" AND book_id = ? ");
					strList.add(bookId);
				} else {
					selectQuery.append(" AND (SELECT hidden_menu FROM bbBook WHERE book_id = doc.book_id) = '0' ");
				}
				
				selectQuery.append(" ORDER BY regist_time desc ");
				
			selectQuery.append(" ) aaa ");
			
			BasicMapper mapper = new BasicMapper();

			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
}
