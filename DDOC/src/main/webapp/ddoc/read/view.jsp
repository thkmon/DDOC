<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
<meta name="theme-color" content="#CCCCFF">
<title>DDOC</title>
<link rel="stylesheet" type="text/css" href="/css/common.css">
<script type="text/javascript" src="/js/lib/jquery-1.8.3.min.js?v=${ver}"></script>
<script type="text/javascript" src="/js/util/dateUtil.js?v=${ver}"></script>

<script type="text/javascript">
	function closeWin() {
		window.close();
	}
</script>
</head>
<body>
	<div style="width: 100%">
		<img src="${imgSrc}" style="width: 100%;" onclick="closeWin()">
	</div>
</body>
</html>