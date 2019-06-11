package com.thkmon.webstd.common.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import com.thkmon.webstd.common.prototype.BasicMap;
import com.thkmon.webstd.common.prototype.BasicMapList;
import com.thkmon.webstd.common.prototype.ObjList;
import com.thkmon.webstd.common.util.PropertiesUtil;

public class BasicMapper {
	
	
	private static HashMap<String, String> propFile = null;

	// mysql-installer-community-5.7.16.0.msi
	// "jdbc:mysql://~~~~~~~:9999/dbname?userUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true";
	private static String jdbcDriver = null;
	private static String dbUser = null;
	private static String dbPass = null;
	
	
	static {
		try {
			if (propFile == null) {
				propFile = PropertiesUtil.readPropertiesFile("/ddoc_config/database.properties");
				jdbcDriver = propFile.get("url");
				dbUser = propFile.get("id");
				dbPass = propFile.get("pw");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getPass() {
		if (dbPass == null) {
			return "";
		}
		
		return dbPass;
	}
	

	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 커넥션 생성
			conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	

	public BasicMapList select(Connection conn, String query, ObjList objList) throws Exception {

		BasicMapList basicMapList = null;
		
		try {
			if (query == null || query.trim().length() == 0) {
				return null;
			}
			
			System.out.println(getQeuryString(query, objList));
			
			if (query.indexOf("@rownum") > -1) {
				this.update(conn, " set @rownum:=0 ", null);
			}
			
			PreparedStatement pst = conn.prepareStatement(query);
			
			Object oneObj = null;
			if (objList != null && objList.size() > 0) {
				int size = objList.size();
				for (int i=0; i<size; i++) {
					oneObj = objList.get(i);
					if (oneObj == null) {
						pst.setNull(i+1, 0);
					} else if (oneObj instanceof String) {
						pst.setString(i+1, String.valueOf(oneObj));
					} else {
						pst.setInt(i+1, Integer.parseInt(String.valueOf(oneObj)));
					}
					
				}
			}
			
			ResultSet rs = pst.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			
			int colCount = metaData.getColumnCount();
			
			if (colCount < 0) {
				return null;
			}

			basicMapList = new BasicMapList();
			
			while (rs.next()) {
				BasicMap basicMap = new BasicMap();
				for (int i=0; i<colCount; i++) {
					basicMap.setString(metaData.getColumnName(i+1).toLowerCase(), rs.getString(i+1));
				}
				basicMapList.add(basicMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
		}
		
		return basicMapList;
	}
	
	public InputStream selectBlob(Connection conn, String query, ObjList objList, String blobColumnName) throws Exception {

		InputStream inputStream = null;
		
		try {
			if (query == null || query.trim().length() == 0) {
				return null;
			}
			
			System.out.println(getQeuryString(query, objList));
			
			PreparedStatement pst = conn.prepareStatement(query);
			
			Object oneObj = null;
			if (objList != null && objList.size() > 0) {
				int size = objList.size();
				for (int i=0; i<size; i++) {
					oneObj = objList.get(i);
					if (oneObj == null) {
						pst.setNull(i+1, 0);
					} else if (oneObj instanceof String) {
						pst.setString(i+1, String.valueOf(oneObj));
					} else {
						pst.setInt(i+1, Integer.parseInt(String.valueOf(oneObj)));
					}
					
				}
			}
			
			ResultSet rs = pst.executeQuery();
			
//			ResultSetMetaData metaData = rs.getMetaData();
			
//			String fistColumnName = "";
			while (rs.next()) {
//				fistColumnName = metaData.getColumnName(1);
//				inputStream = rs.getBinaryStream(fistColumnName);
				
				inputStream = rs.getBinaryStream(blobColumnName);
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
		}
		
		return inputStream;
	}
	
	private String getQeuryString(String query, ObjList objList) {
		
		if (query == null || query.length() == 0) {
			return "";
		}
		
		query = query.replace("\t", " ").replace("\r", "").replace("\n", "");
		while (query.indexOf("  ") > -1) {
			query = query.replace("  ", " ");
		}
		
		int mapIdx = 0;
		
		int quesIdx = query.indexOf("?");
		while (quesIdx > -1) {
			if (objList.size() <= mapIdx) {
				break;
			}
			query = query.substring(0, quesIdx) + "'" + objList.get(mapIdx) + "'" + query.substring(quesIdx + 1);
			mapIdx++;
			quesIdx = query.indexOf("?");
		}
		
		query = query.trim();
		
		return "getQeuryString : " + query + ";";
		
	}
	
	public boolean insert(Connection conn, String query, ObjList objList) throws Exception {

		PreparedStatement pst = null;
		boolean isTransaction = true;
		
		try {
			if (query == null || query.trim().length() == 0) {
				return false;
			}
			
			System.out.println(getQeuryString(query, objList));
			
			if (conn == null) {
				conn = getConnection();
				isTransaction = false;
			}
			
			pst = conn.prepareStatement(query);
			
			if (objList != null && objList.size() > 0) {
				int size = objList.size();
				
				Object oneObj = null;
				for (int i=0; i<size; i++) {
//					System.out.println(i);
					oneObj = objList.get(i);
					if (oneObj == null) {
						pst.setNull(i+1, 0);
					} else if (oneObj instanceof String) {
						pst.setString(i+1, String.valueOf(oneObj));
					} else if (oneObj instanceof File) {
						FileInputStream input = new FileInputStream((File)oneObj);
						pst.setBinaryStream(i+1, input);
					} else {
						pst.setInt(i+1, Integer.parseInt(String.valueOf(oneObj)));
					}
				}
			}
			
			int result = pst.executeUpdate();
			
			if (result < 1) {
				return false;
			}

			close(pst);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			close(pst);
			
			if (!isTransaction) {
				rollbackAndClose(conn);
			}
		}
		
		return true;
	}
	
	public int update(Connection conn, String query, ObjList objList) throws Exception {

		PreparedStatement pst = null;
		boolean isTransaction = true;
		
		int updateCount = 0;
		
		try {
			if (query == null || query.trim().length() == 0) {
				return 0;
			}
			
			System.out.println(getQeuryString(query, objList));
			
			if (conn == null) {
				conn = getConnection();
				isTransaction = false;
			}
			
			pst = conn.prepareStatement(query);
			
			if (objList != null && objList.size() > 0) {
				int size = objList.size();
				
				Object oneObj = null;
				for (int i=0; i<size; i++) {
					oneObj = objList.get(i);
					if (oneObj == null) {
						pst.setNull(i+1, 0);
					} else if (oneObj instanceof String) {
						pst.setString(i+1, String.valueOf(oneObj));
					} else {
						pst.setInt(i+1, Integer.parseInt(String.valueOf(oneObj)));
					}
				}
			}
			
			updateCount = pst.executeUpdate();
			
			if (updateCount < 1) {
				return 0;
			}

			close(pst);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			close(pst);
			
			if (!isTransaction) {
				rollbackAndClose(conn);
			}
		}
		
		return updateCount;
	}
	
	public void close(PreparedStatement pst) {
		try {
			if (pst != null) {
				pst.close();
			}
			
		} catch (Exception e) {
			pst = null;
		}
	}
	
	public static void rollbackAndClose(Connection conn) {
		
		try {
			if (conn != null) {
				conn.rollback();
			}
			
		} catch (Exception e) {
			conn = null;
		}
		
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
			
		} catch (Exception e) {
			conn = null;
		}
	}
	
	public static void rollbackOnly(Connection conn) {
		
		try {
			if (conn != null) {
				conn.rollback();
			}
			
		} catch (Exception e) {
		}
	}
	
	public static void commitAndClose(Connection conn) {
		
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
			
		} catch (Exception e) {
			conn = null;
		}
	}
	
//	public String convertFileToString(File file) {
//	BufferedInputStream bis = null;// 파일을 서버에서 읽기 시작함
//	 BufferedOutputStream bos= null;//리스폰스가 접속한 사람임. 접속한 사람에게 응답하기 위해서 리스폰스를 씀. 읽어온 파일을 접속한 사람에게 줌.
//
//	StringBuffer resBuffer = new StringBuffer();
//	
//	try {
//		ArrayList<Byte> aaaa = new ArrayList<>();
//		bis = new BufferedInputStream(new FileInputStream(file));
//		bos = new BufferedOutputStream(new OutputStream() {
//			
//			@Override
//			public void write(int b) throws IOException {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//		int i = 0;
//		byte[] buffer = new byte[1024];// 한번 읽을때마다 1024씩 읽는다.
//		while ((i = bis.read(buffer, 0, 1024)) != -1) {
//			// -1 EOF
//			for (int k=0; k<i; k++) {
//				resBuffer.append(buffer[k]);
//			}
//			 bos.write(buffer, 0, i);// i가 읽은 바이트 수
//			 
//			 for (int k=0; k<i; k++) {
//				 aaaa.add(buffer[k]);
//			}
//		}
//
//		if (bis != null) {
//			bis.close();
//		}
//
//	} catch (Exception e) {
//
//	} finally {
//		try {
//			if (bis != null) {
//				bis.close();
//			}
//
//		} catch (Exception e) {
//		}
//	}
//	
//	return resBuffer.toString();
//
//}
	
	
}
