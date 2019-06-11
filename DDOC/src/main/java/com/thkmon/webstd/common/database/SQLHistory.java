package com.thkmon.webstd.common.database;

public class SQLHistory {

/*

	2016-11-13 ���� 6:29
	CREATE TABLE DDMemo(
			 userId VARCHAR(20),
			 userName VARCHAR(20), 
			 content VARCHAR(1000),
			 ipAddress VARCHAR(100),
			 valid char(1),
			 regtime VARCHAR(14));
			 
	insert into DDMemo values ('id', 'name', 'content', 'ipAddr', '1', '20161113000000');
	
  -------------------------------------
	
	java.sql.SQLException: The server time zone value '????��? ????' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.

  ====> jdbcDriver += "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; �� �ٿ��� �ذ��Ѵ�.
  
  ---------------------------------------------------------
  

	// Sun Nov 13 07:50:57 KST 2016 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
	�ذ� ;	jdbcDriver += "&useSSL=true";






���: The web application [ROOT] registered the JDBC driver [com.mysql.cj.jdbc.Driver] but failed to unregister it when the web application was stopped. To prevent a memory leak, the JDBC Driver has been forcibly unregistered.


����::
 // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }

        }
        
  �ذ� : Enumeration<Driver> drivers = DriverManager.getDrivers();
			Driver driver = drivers.nextElement();
        
        
        �߷�
        
        try {
				System.out.println("�����!!11");
				DriverManager.deregisterDriver(driver);
				System.out.println("�����!!22");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
        
        
        
        
        
        
*/
	
	
	// 03:04 2017-02-21 ȭ
//	CREATE TABLE bbVisitHistory (
//			page_url varchar(100),
//			user_id varchar(20),
//			user_name varchar(20),
//	        ipAddress VARCHAR(100),
//			visit_time varchar(14));
	
}
