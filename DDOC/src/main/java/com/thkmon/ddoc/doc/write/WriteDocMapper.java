package com.thkmon.ddoc.doc.write;

import java.sql.Connection;

import com.thkmon.webstd.common.config.CommonConfig;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.prototype.StringList;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.HttpUtil;
import com.thkmon.webstd.common.util.StringUtil;
//import com.thkmon.webstd.common.util.UploadUtil;

public class WriteDocMapper extends BasicMapper {
	
// �ʱ�ȭ SQL
	
//	SET SQL_SAFE_UPDATES=0;
//	delete from bbDoc;
//	update bbDocSeq set seq_num = 0;

	
//	CREATE TABLE bbDocSeq(
//	user_id VARCHAR(20),
//	seq_num VARCHAR(32));
		
  	
//	Drop table bbdoc;
  	
		
//	CREATE TABLE bbDoc(
//  seq_num varchar(32),
//	doc_id varchar(32),
//	user_id VARCHAR(20),
//	user_name VARCHAR(20),
//	title VARCHAR(1000), 
//	content VARCHAR(60000),
//	content_src VARCHAR(200),
//	ip_address VARCHAR(100),
//	valid char(1),
//	regist_time VARCHAR(14),
//	update_time VARCHAR(14));
	
	// 2017.02.21�� �Ʒ��� ����.
//	CREATE TABLE bbBook (
//		seq_num varchar(32),
//		book_id varchar(32),
//		user_id varchar(20),
//		user_name varchar(20),
//		title varchar(1000),
//		valid char(1),
//		regist_time varchar(14),
//		update_time varchar(14));
	
	//2017.04.02
	// alter table bbBook add secret char(2) default '0';
	// alter table bbBook add hidden_menu char(1) default '0';
	
	//		insert into bbBook values (0, 'b0', 'bb_', '���', '����', '1', '20170220234300', '20170220234300');
	//		insert into bbBook values (1, 'b1', 'bb_', '���', '�ڹ� �޼���', '1', '20170220234300', '20170220234300');
	//    
	//    insert into bbBook values (2, 'b2', 'bb_', '���', '�ϱ�', '1', '20170220234300', '20170220234300');
	//    
	//    insert into bbBook values (3, 'b3', 'bb_', '���', '��������', '1', '20170220234300', '20170220234300');
	//    
	//    insert into bbBook values (4, 'b4', 'bb_', '���', '���İ�', '1', '20170220234300', '20170220234300');
	//    
	//    insert into bbBook values (5, 'b5', 'bb_', '���', '������ �� �����', '1', '20170220234300', '20170220234300');
	//    
	//    insert into bbBook values (6, 'b6', 'bb_', '���', '���� ���� �����', '1', '20170220234300', '20170220234300');
	
//	insert into bbBook values (7, 'b7', 'bb_', '���', '�� �����丮', '1', '20170220234300', '20170220234300');
//	insert into bbBook values (8, 'b8', 'bb_', '���', '������ �ܻ�', '1', '20170220234300', '20170220234300');
	
	// insert into bbBook values (9, 'b9', 'bb_', '���', '���� ����', '1', '20170220234300', '20170220234300');
	
//	alter table bbDoc add book_id varchar(32);
	
	// 2017.04.02
	// alter table bbDoc add secret char(2) default '0';
	
	
	public String insertDoc(Connection conn, BasicMap map, String docSeqNum, String docId, String registTime) throws Exception {
		
		try {
			
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO bbDoc ");
			query.append(" (SEQ_NUM, DOC_ID, USER_ID, USER_NAME, TITLE, ");
			query.append("  CONTENT, CONTENT_SRC, IP_ADDRESS, VALID, REGIST_TIME, ");
			query.append("  UPDATE_TIME, book_id, SECRET ) ");
			query.append(" VALUES ");
			query.append(" (?, ?, ?, ?, ?, ");
			query.append(" ?, ?, ?, '1', ?, ");
			query.append(" ?, ?, ? ) ");
			
			
			String userId = map.getNotEmptyString("USER_ID");
			// String yearValue = DateUtil.getYear();
			
			String contentSrc = ""; // UploadUtil.createContentFileCorePath(userId, docId, registTime);
			
			String content = map.getNotEmptyString("CONTENT");
			if (content.length() > CommonConfig.docContentLimitlength) {
				content = content.substring(0, CommonConfig.docContentLimitlength);
			}
			
			// String registTime = DateUtil.getDateTime();
			ObjList mapper = new ObjList();
			mapper.add(docSeqNum);
			mapper.add(docId);
			mapper.add(userId);
			mapper.add(map.getNotEmptyString("USER_NAME"));
			mapper.add(map.getNotEmptyString("TITLE"));
			mapper.add(content);
			mapper.add(contentSrc);
			mapper.add(HttpUtil.getIpAddress());
			
			mapper.add(registTime);
			mapper.add(registTime);
			mapper.add(map.getNotEmptyString("BOOK_ID"));
			mapper.add(map.getNotEmptyString("secret"));
			
			insert(conn, query.toString(), mapper);
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return docId;
	}
	
	/**
	 * Ư�� ������ docSeqNum�� ���´�.
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String createDocSeqNum(Connection conn, String userId) throws Exception {
		
		if (userId == null || userId.trim().length() == 0) {
			throw new Exception("userId is null or empty");
		}
		
		String docSeqNum = "";
		
		try {
			
			int currentSeqNum = 0;
	
			String selectQuery = " SELECT SEQ_NUM FROM bbDocSeq WHERE USER_ID = ? ";
			
			ObjList mapper = new ObjList();
			mapper.add(userId);
			
			BasicMapList mapList = select(conn, selectQuery, mapper);
			
			/**
			 * �����Ͱ� ������ �μ�Ʈ
			 */
			if (mapList == null || mapList.size() < 1) {
				String insertQuery = " INSERT INTO bbDocSeq (USER_ID, SEQ_NUM) VALUES (?, '0') ";
				
				mapper = new ObjList();
				mapper.add(userId);
				
				insert(conn, insertQuery, mapper);
				currentSeqNum = 0;
				
			} else {
				BasicMap map = mapList.get(0);
				currentSeqNum = StringUtil.parseInt(map.getString("seq_num"));
			}
			
			/**
			 * seqNum�� +1 �� ������ ������Ʈ
			 */
			String newSeqNum = String.valueOf(currentSeqNum + 1);
			
			String updateQuery = " UPDATE bbDocSeq SET SEQ_NUM = ? WHERE USER_ID = ? ";
			mapper = new ObjList();
			mapper.add(newSeqNum);
			mapper.add(userId);
			
			update(conn, updateQuery, mapper);
			
			docSeqNum = newSeqNum;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return docSeqNum;
	}
}
