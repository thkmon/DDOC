package com.thkmon.ddoc.doc.modify;

import java.sql.Connection;

import com.thkmon.webstd.common.config.CommonConfig;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.prototype.StringList;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.HttpUtil;
//import com.thkmon.webstd.common.util.UploadUtil;

public class ModifyDocMapper extends BasicMapper {
	public String updateDoc(Connection conn, BasicMap map, String docId, String modifyTime) throws Exception {
		
		try {
			
			StringBuffer query = new StringBuffer();
			query.append(" UPDATE bbDoc SET ");
			query.append(" book_id = ?, ");
			query.append(" TITLE = ?, CONTENT = ?, UPDATE_TIME = ?, SECRET = ? ");
			query.append(" WHERE DOC_ID = ? ");
			
			String content = map.getNotEmptyString("CONTENT");
			if (content.length() > CommonConfig.docContentLimitlength) {
				content = content.substring(0, CommonConfig.docContentLimitlength);
			}
			
			ObjList mapper = new ObjList();
			mapper.add(map.getNotEmptyString("BOOKID"));
			mapper.add(map.getNotEmptyString("TITLE"));
			mapper.add(content);
			mapper.add(modifyTime);
			mapper.add(map.getNotEmptyString("SECRET"));
			mapper.add(docId);
			
			update(conn, query.toString(), mapper);
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return docId;
	}
}