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
window.onload = function() {
	document.getElementById("pwd").focus();
}

function onClick_postButton() {
	var pwdVal = document.getElementById("pwd").value;
	if (pwdVal == null || pwdVal == "") {
		return false;
	}
	
	form1.submit();
}
</script>
<style type="text/css">
	input {
		height: 20px;
	}
</style>
</head>
<body>
	<div style="margin: 20px;">
		<form id="form1" name="form1" method="post" target="_self" action="/reqsetperm">
			<input id="pwd" name="pwd" type="password">
		</form>
		<input type="button" value="post" onclick="onClick_postButton()">
	</div>
</body>
</html>