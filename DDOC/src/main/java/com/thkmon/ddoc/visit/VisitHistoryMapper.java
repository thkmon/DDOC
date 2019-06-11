package com.thkmon.ddoc.visit;

import java.sql.Connection;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.prototype.StringList;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.HttpUtil;

public class VisitHistoryMapper extends BasicMapper {
	public boolean insertVisitHistory(Connection conn, BasicMap map) throws Exception {
		
		try {
			
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO bbVisitHistory ");
			query.append(" (USER_ID, USER_NAME, page_url, ipAddress, visit_time) ");
			query.append(" VALUES ");
			query.append(" (?, ?, ?, ?, ?) ");
			
			String userId = map.getNotEmptyString("userId");
			String userName = map.getNotEmptyString("userName");
			String pageUrl = map.getNotEmptyString("pageUrl");
			
			// ��¥�� ��´�.
			String dateTime = DateUtil.getDateTime();
						
			ObjList mapper = new ObjList();
			mapper.add(userId);
			mapper.add(userName);
			mapper.add(pageUrl);
			mapper.add(HttpUtil.getIpAddress());
			mapper.add(dateTime);
			
			insert(conn, query.toString(), mapper);
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return true;
	}
	
	public boolean deleteAdminVisitHistory(Connection conn) throws Exception {
		
		try {
			/**
			 * ������ �����ǰ� ������ �ʵ��� �����Ѵ�.
			 */
			StringBuffer query = new StringBuffer();
			
			query.append(" DELETE FROM bbVisitHistory ");
			query.append(" WHERE ipAddress = ? AND visit_time like ? ");
			
			// ��¥�� ��´�.
			String dateTime = DateUtil.getDateTime();
						
			String ipAddress = HttpUtil.getIpAddress();
			String visitTime = dateTime.substring(0, 8) + "%";
			System.out.println("ipAddress : " + ipAddress + " / visitTime : " + visitTime + " �����");

			ObjList mapper = new ObjList();
			mapper.add(ipAddress);
			mapper.add(visitTime);
			
			insert(conn, query.toString(), mapper);
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return true;
	}
}
