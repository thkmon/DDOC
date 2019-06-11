package com.thkmon.webstd.common.mybatis;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thkmon.webstd.common.util.PropertiesUtil;

public class ThkDataSource extends BasicDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(ThkDataSource.class);
	
	
	public synchronized void close() throws SQLException {
		super.close();
	}
	
	
	public synchronized void setDriverClassName(String driverClassName) {
		super.setDriverClassName(driverClassName);
	}
	
	
	public synchronized void setUrl(String url) {
		logger.info("ThkDataSource setUrl : BEFORE");
		
		String newUrl = url;
		try {
			newUrl = PropertiesUtil.readPropertiesFile("/webstd_config/database.properties").get("url");
			logger.info("ThkDataSource setUrl : SUCCESS");
			
		} catch (Exception e) {
			logger.info("ThkDataSource setUrl : FAIL");
			logger.error(e.getMessage());
		}
		super.setUrl(newUrl);
	}
	
	
	public void setUsername(String username) {
		logger.info("ThkDataSource setUsername : BEFORE");
		
		String newUserName = username;
		try {
			newUserName = PropertiesUtil.readPropertiesFile("/webstd_config/database.properties").get("id");
			logger.info("ThkDataSource setUsername : SUCCESS");
			
		} catch (Exception e) {
			logger.info("ThkDataSource setUsername : FAIL");
			logger.error(e.getMessage());
		}
		super.setUsername(newUserName);
	}
	
	
	public void setPassword(String password) {
		logger.info("ThkDataSource setPassword : BEFORE");
		
		String newPassword = password;
		try {
			newPassword = PropertiesUtil.readPropertiesFile("/webstd_config/database.properties").get("pw");
			logger.info("ThkDataSource setPassword : SUCCESS");
			
		} catch (Exception e) {
			logger.info("ThkDataSource setPassword : FAIL");
			logger.error(e.getMessage());
		}
		super.setPassword(newPassword);
	}
}