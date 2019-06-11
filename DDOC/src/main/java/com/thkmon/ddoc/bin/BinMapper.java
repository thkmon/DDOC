package com.thkmon.ddoc.bin;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;

import javax.sound.midi.SysexMessage;

import com.thkmon.webstd.common.config.CommonConfig;
import com.thkmon.webstd.common.database.BasicMapper;
import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.util.DateUtil;
import com.thkmon.webstd.common.util.StringUtil;

//  CREATE TABLE bbBin (
//    seq_num varchar(32),
//    bin_id varchar(32),
//    bin_cont LONGBLOB,
//    title varchar(1000),
//    valid char(1),
//    regist_time varchar(14),
//    doc_id varchar(32));

public class BinMapper extends BasicMapper {
	public boolean insertBin(Connection conn, File file, String originFilePath, String newFilePath, String dateTime, String docId) throws Exception {
		
		try {
			String fileTitle = null;
			
			if (originFilePath == null) {
				fileTitle = "{null}";
				
			} else {
				originFilePath = originFilePath.replace("/", "\\");
				int lastSlashIdx = originFilePath.lastIndexOf("\\");
				if (lastSlashIdx > -1) {
					fileTitle = originFilePath.substring(lastSlashIdx + 1);
				}
			}
			
			String binId = "";
			
			if (newFilePath == null || newFilePath.length() == 0) {
				System.err.println("newFilePath == null || newFilePath.length() == 0");
				binId = DateUtil.getDateTime();
				
			} else {
				newFilePath = newFilePath.replace("/", "\\");
				int lastSlashIdx = newFilePath.lastIndexOf("\\");
				if (lastSlashIdx > -1) {
					binId = newFilePath.substring(lastSlashIdx + 1);
				} else {
					binId = newFilePath;
				}
				
				binId = binId.replace(".", "_");
			}
			
			
			int curNum = 0;
			BasicMapList mapList = select(conn, "SELECT seq_num from bbBin", null);
			if (mapList != null && mapList.size() > 0) {
				curNum = StringUtil.parseInt(mapList.get(0).getString("seq_num"));
				if (curNum == 0) {
					curNum = 0;
				}
			}
			
			int nextNum = curNum + 1;
			
			StringBuffer query = new StringBuffer();
			query.append(" INSERT INTO bbBin ");
			query.append(" ( seq_num, ");
			query.append("   bin_id, bin_cont, title, ");
			query.append("   valid, ");
			query.append("   regist_time, doc_id ) ");
			query.append(" VALUES ");
			query.append(" ( ?, ");
			query.append("   ?, ?, ?, ");
			query.append("   '1', ");
			query.append("   ?, ? ) ");
			
			ObjList mapper = new ObjList();
			mapper.add(String.valueOf(nextNum));
			mapper.add(binId.replace(".", "_"));
			mapper.add(file);
			mapper.add(fileTitle);
			
			mapper.add(dateTime);
			mapper.add(docId);
			
			insert(conn, query.toString(), mapper);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return true;
	}

	public InputStream selectBin(Connection conn, String binId) throws Exception {
		
		InputStream resultBin = null;
		
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT bin_cont FROM bbBin WHERE bin_id = ? AND valid = '1' ");
			
			ObjList mapper = new ObjList();
			mapper.add(binId);
			
			// BasicMapList list = select(conn, query.toString(), mapper);
			resultBin = selectBlob(conn, query.toString(), mapper, "bin_cont");
			if (resultBin != null) {
				return resultBin;
			}
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			
		}
		
		return null;
	}
}
