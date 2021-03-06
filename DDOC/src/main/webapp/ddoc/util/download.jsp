<%-- <%@page import="util.UploadUtil"%> --%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%
	String fn = request.getParameter("fn");
	if (fn == null || fn.trim().length() == 0) {
		System.out.println("파일명을 알 수 없습니다.");
		return;
	}

	File targetDir = new File(""); //UploadUtil.getRepositoryFolder("temp");
	File targetFile = new File(targetDir.getAbsolutePath() + "/" + fn);

	response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fn,"UTF-8"));

	response.setContentLength((int)targetFile.length());
 
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(targetFile));
	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
 
    byte[] buffer=new byte[1024];
    
	int i=0;
	
    while((i=bis.read(buffer,0,1024))!=-1){
		bos.write(buffer,0,i);
    }
    
    bis.close();
    bos.close();
    out.clear();
    out = pageContext.pushBody();
%>
