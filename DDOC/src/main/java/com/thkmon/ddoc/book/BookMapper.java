package com.thkmon.ddoc.book;

import java.sql.Connection;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.prototype.StringList;

public class BookMapper {
	public BasicMapList selectBookToWrite (Connection conn, String userId) throws Exception {
		
		BasicMapList result = null;
		
		try {
			ObjList strList = new ObjList();
			
			strList.add(userId);
			
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" seq_num     , ");
			selectQuery.append(" book_id      , ");
			selectQuery.append(" user_id     , ");
			selectQuery.append(" user_name   , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" valid       , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time   ");
			
			// selectQuery.append(" FROM bbBook WHERE user_id = ? ORDER BY LPAD(seq_num, '6','0') DESC ");
			
			selectQuery.append(" FROM bbBook ");
			selectQuery.append(" WHERE user_id = ? ");
			selectQuery.append(" AND valid = '1' ");
			selectQuery.append(" ORDER BY update_time DESC, seq_num DESC ");
			
			BasicMapper mapper = new BasicMapper();
			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public BasicMapList selectBookOrderByUpdateTime (Connection conn, String userId) throws Exception {
		
		BasicMapList result = null;
		
		try {
			ObjList strList = new ObjList();
			
			strList.add(userId);
			
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT ");
			
			selectQuery.append(" seq_num     , ");
			selectQuery.append(" book_id      , ");
			selectQuery.append(" user_id     , ");
			selectQuery.append(" user_name   , ");
			selectQuery.append(" title       , ");
			selectQuery.append(" valid       , ");
			selectQuery.append(" regist_time , ");
			selectQuery.append(" update_time   ");
			
			selectQuery.append(" FROM bbBook ");
			selectQuery.append(" WHERE user_id = ? ");
			selectQuery.append(" AND valid = '1' ");
			selectQuery.append(" AND secret = '0' ");
			selectQuery.append(" AND hidden_menu = '0' ");
			selectQuery.append(" ORDER BY update_time DESC, seq_num DESC ");
			
			BasicMapper mapper = new BasicMapper();
			result = mapper.select(conn, selectQuery.toString(), strList);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
}
