<%@page import="com.thkmon.ddoc.doc.modify.ModifyDocController"%>
<%@page import="com.thkmon.webstd.common.util.StringUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="com.thkmon.ddoc.doc.write.WriteDocController"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	String sessionUserId = String.valueOf(session.getAttribute("userId"));
	String alertMsg = "";
	boolean bIsSuccess = false;
	String returnDocId = "";
	
	try {
		String modifyDocId = request.getParameter("modifyDocId");
		if (modifyDocId == null || modifyDocId.trim().length() == 0) {
			modifyDocId = "";
		} else {
			modifyDocId = modifyDocId.trim();
		}
		System.out.println("modifyDocId : " + modifyDocId);
		
		if (modifyDocId != null && modifyDocId.length() > 0) {
			// 문서 수정
			ModifyDocController modifyCtrl = new ModifyDocController();
			returnDocId = modifyCtrl.modifyDoc(request, response, modifyDocId);
			
			if (returnDocId != null && returnDocId.trim().length() > 0) {
				returnDocId = returnDocId.trim();
				bIsSuccess = true;
			} else {
				bIsSuccess = false;
			}
			
			if (bIsSuccess) {
				alertMsg = "글이 수정되었습니다.";
				
			} else {
				alertMsg = "오류가 발생하여 글이 수정되지 않았습니다.";
			}
			
		} else {
			// 문서 등록
			WriteDocController writeCtrl = new WriteDocController();
			returnDocId = writeCtrl.writeDoc(request, response);
			
			if (returnDocId != null && returnDocId.trim().length() > 0) {
				returnDocId = returnDocId.trim();
				bIsSuccess = true;
			} else {
				bIsSuccess = false;
			}
			
			if (bIsSuccess) {
				alertMsg = "글이 등록되었습니다.";
				
			} else {
				alertMsg = "오류가 발생하여 글이 등록되지 않았습니다.";
			}
		}
		
	} catch (Exception e) {
		alertMsg = e.getMessage();
	}
	
	alertMsg = StringUtil.escapeNull(alertMsg);
	pageContext.setAttribute("alertMsg", alertMsg);
	pageContext.setAttribute("bIsSuccess", bIsSuccess ? "1" : "0");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
<meta name="theme-color" content="#CCCCFF">
<title>DDOC</title>
<link rel="stylesheet" href="/css/common.css?v=${ver}">
<script type="text/javascript" src="/js/lib/jquery-1.8.3.min.js?v=${ver}" ></script>
<script type="text/javascript">
	$(window).load(function(){
		var g_isSuccess = "${bIsSuccess}";
		var g_sessionUserId = "<%=sessionUserId%>";
		var g_returnDocId = "<%=returnDocId%>";
		
		alert("${alertMsg}");
		
		if (g_isSuccess == "1") {
			var newLink = "/" + g_sessionUserId + "/" + g_returnDocId;
			if (window != null) {
				if (window.parent != null) {
					window.parent.location.href = newLink;
				} else {
					window.location.href = newLink;
				}	
			}
		}
	});
</script>
</head>
<body>
<!-- <form id="form1" name="form1" target="_self" action=""></form> -->
</body>
</html>