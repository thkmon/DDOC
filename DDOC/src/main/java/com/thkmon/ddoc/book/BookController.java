package com.thkmon.ddoc.book;

import java.sql.Connection;

import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMapList;

public class BookController {
	public BasicMapList getBookList(String userId) throws Exception {
		
		BasicMapList result = null;
		Connection conn = null;
		
		try {
			conn = BasicMapper.getConnection();
			BookMapper mapper = new BookMapper();
			result = mapper.selectBookToWrite(conn, userId);
			
			if (result == null || result.size() < 1) {
				throw new Exception("������ �������� �ʽ��ϴ�. userId [" + userId + "]");
			}
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			BasicMapper.rollbackAndClose(conn);
		}
		
		return result;
	}
}
