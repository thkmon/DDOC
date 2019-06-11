package com.thkmon.ddoc.visit;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.util.HttpUtil;

public class VisitHistoryController {
//	public boolean addVisitHistory (Connection conn, HttpServletRequest req, String pageUrl) throws Exception {
//		
//		boolean result = false;
//		
//		boolean transaction = false;
//		
//		try {
//			if (1==1) {
//				return true;
//			}
//			
//			if (conn != null) {
//				transaction = true;
//				
//			} else {
//				transaction = false;
//				conn = BasicMapper.getConnection();
//			}
//			
//			BasicMap map = new BasicMap();
//			
//			String userId = String.valueOf(req.getSession().getAttribute("userId"));
//			String userName = String.valueOf(req.getSession().getAttribute("userName"));
//			
//			if (userId == null) {
//				userId = "";
//			}
//			
//			if (userName == null) {
//				userName = "";
//			}
//			
//			if (pageUrl == null) {
//				pageUrl = "";
//			}
//			
//			VisitHistoryMapper mapper = new VisitHistoryMapper();
//			
//			if (userId.equals("bb_")) {
//				// perm�� ���� ��� (�������� ���)
//				// ���� ���� ������ �ƴ϶� ���� ���� �����.
//				mapper.deleteAdminVisitHistory(conn);
////				if (!transaction) {
//					BasicMapper.commitAndClose(conn);
////				}
//				return true;
//			}
//			
//			map.put("userId", userId);
//			map.put("userName", userName);
//			map.put("pageUrl", pageUrl);
//
//			
//			result = mapper.insertVisitHistory(conn, map);
//			
////			if (!transaction) {
//				BasicMapper.commitAndClose(conn);
////			}
//			
//		} catch (Exception e) {
//			throw e;
//			
//		} finally {
//			
////			if (!transaction) {
//				BasicMapper.rollbackAndClose(conn);
////			}
//		}
//		
//		return result;
//	}
}
