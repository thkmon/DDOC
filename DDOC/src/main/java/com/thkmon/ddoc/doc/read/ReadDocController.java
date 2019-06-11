package com.thkmon.ddoc.doc.read;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.thkmon.ddoc.book.BookMapper;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMapList;

public class ReadDocController {
	
	public BasicMapList readDoc(String userIdParam, String docId) throws Exception {
		
		BasicMapList result = null;
		Connection conn = null;
		
		try {
			conn = BasicMapper.getConnection();
			ReadDocMapper mapper = new ReadDocMapper();
			result = mapper.selectDoc(conn, userIdParam, docId);
			
			if (result == null || result.size() < 1) {
				throw new Exception("문서가 존재하지 않습니다. userIdParam [" + userIdParam + "], docId [" + docId + "]");
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return result;
	}
	
	public BasicMapList getDocList(Connection conn, String userId, String bookId) {
		
		BasicMapList result = null;
		
		boolean transaction = false;
		
		try {
			if (conn == null) {
				transaction = false;
				conn = BasicMapper.getConnection();
				
			} else {
				transaction = true;				
			}

			
			ReadDocMapper mapper = new ReadDocMapper();
			result = mapper.selectDocList(conn, userId, bookId, true);
			
			if (result == null || result.size() < 1) {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (!transaction) {
				BasicMapper.rollbackAndClose(conn);
			}
		}
		
		return result;
	}
	
//	public BasicMapList getPreDocList(Connection conn, String userId, String bookId, String docId) {
//		return getDocListArround(conn, userId, bookId, docId, true);
//	}
//	
//	public BasicMapList getPostDocList(Connection conn, String userId, String bookId, String docId) {
//		return getDocListArround(conn, userId, bookId, docId, false);
//	}
	
//	private BasicMapList getDocListArround(Connection conn, String userId, String bookId, String docId, boolean preList) {
//		
//		BasicMapList result = null;
//		
//		boolean transaction = false;
//		
//		try {
//			if (conn == null) {
//				transaction = false;
//				conn = BasicMapper.getConnection();
//				
//			} else {
//				transaction = true;				
//			}
//			
//			ReadDocMapper mapper = new ReadDocMapper();
//			result = mapper.selectDocListArround(conn, userId, bookId, docId, preList);
//			
//			if (result == null || result.size() < 1) {
//				return null;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		} finally {
//			if (!transaction) {
//				BasicMapper.rollbackAndClose(conn);
//			}
//		}
//		
//		return result;
//	}
	
	public BasicMapList getDocList(Connection conn, String userIdParam, String bookId, int pageNum, int countInPage, HttpServletRequest request) {
		
		BasicMapList result = null;
		
		boolean transaction = false;
		
		try {
			if (conn == null) {
				transaction = false;
				conn = BasicMapper.getConnection();
				
			} else {
				transaction = true;				
			}

			ReadDocMapper mapper = new ReadDocMapper();
			result = mapper.selectDocList(conn, userIdParam, bookId, pageNum, countInPage, request);
			
			if (result == null || result.size() < 1) {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (!transaction) {
				BasicMapper.rollbackAndClose(conn);
			}
		}
		
		return result;
	}
}
