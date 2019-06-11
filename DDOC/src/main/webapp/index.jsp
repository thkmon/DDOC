<%@page import="com.thkmon.webstd.common.util.StringUtil"%>
<%@page import="sun.swing.StringUIClientPropertyKey"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
<meta name="theme-color" content="#CCCCFF">
<title>DDOC</title>
<link rel="stylesheet" type="text/css" href="/css/common.css">
<link rel="shortcut icon" href="/favicon.ico">
<script type="text/javascript" src="/js/lib/jquery-1.8.3.min.js?v=${ver}"></script>
<script type="text/javascript">
	function goBack() {
		history.back();
	}
</script>
</head>
<body>
	<div id="pageBox" class="pageBox" style="position:absolute; left:10px; top:10px;" onclick="goBack()">
		<a href="javascript:goBack()">←</a>
	</div>
	<div class="mainPlate">
		<div class="w100p center">
			<a href="/"><img src="/img/ddoc_title200.gif" class="titleImg"></a>
		</div>
		<br>
		<%@ include file="/ddoc/read/menu.jsp"%>
		<br>
		<div class="center">
			<a href="/"><img src="/img/ddoc_title200.gif" class="titleSmallImg"></a>
			<br>
			<span class="smallFont">bb_@naver.com</span>
			<%
				if (StringUtil.parseString(session.getAttribute("userId")).equals("bb_")) {
					out.println("<a style=\"color: #000000; text-decoration: none;\" href=\"/w\">*</a>");		
				}
			%>
		</div>
	</div>
<br><br>
</body>
</html>