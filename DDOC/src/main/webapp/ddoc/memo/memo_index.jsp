<%@page import="com.thkmon.webstd.common.prototype.BasicMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.thkmon.webstd.common.prototype.BasicMapList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	BasicMapList memoList = (BasicMapList) request.getAttribute("memoList");
	System.out.println("jsp에서 출력 : " + memoList);
	BasicMap basicMap = memoList.get(0);
	
	pageContext.setAttribute("basicMap", basicMap);
	
// 	HashMap hMap = new HashMap();
// 	hMap.put("aaaa", "1234");
// 	pageContext.setAttribute("hMap", hMap);
	
	
// 	ArrayList aList = new ArrayList();
// 	HashMap hMap = new HashMap();
// 	hMap.put("name", "=");
// 	hMap.put("id", "=");
// 	aList.add(hMap);
// 	System.out.println("aList : " + aList);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
<meta name="theme-color" content="#CCCCFF">
<link rel="stylesheet" href="/css/common.css?v={ver}">
<style type="text/css">

</style>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"> -->
<script type="text/javascript" src="/js/jquery-1.8.3.min.js?v={ver}"></script>
<script type="text/javascript" src="/ddoc/util/ajaxUtil.js?v={ver}"></script>

<script type="text/javascript">
	function writeMemo() {
		if (event != null && event.keyCode == "13") {
			
			AjaxUtil.sendPost("/memo.e", "#memoInput", "e=writeMemo", function(result){
				alert("결과 : " + result);
			});
			
			$("#memoForm").val("");
		}
	}
</script>
<title>${htmlTitle}</title>
</head>
<body>
	<div class="mainCont">
		<div class="margin10">
			메모장
			<br><br>
			<input type="text" id="memoInput" class="basicInput" onkeypress="writeMemo()"/>
			<c:forEach items="${memoList}" var="oneMemo">
				<c:out value="${oneMemo.content}"></c:out>
			</c:forEach>
		</div>
	</div>
	
</body>
</html>