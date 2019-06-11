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
	$(window).load(function(){
		
		var json = null;
		
		try {
			json = ${json};
		} catch (e) {
			alert("JSON 변환 에러 : " + e);
		}
		
		if (json == null) {
			return false;
		}
		
		var isAdmin = "${isAdmin}";
		
		if (isAdmin != null && isAdmin == "true") {
			var linkText = "<a style=\"color: #000000; text-decoration: none;\" href=\"" + "${modifyLinkText}" + "\">*</a>";
			$("#updateTime").html(DateUtil.makeDateToPrint(json.update_time) + "&nbsp;" + linkText);
			
		} else {
			$("#updateTime").html(DateUtil.makeDateToPrint(json.update_time));
		}
		
		$("#registTime").text(DateUtil.makeDateToPrint(json.regist_time));
		$("#userName").text(json.user_name + " (" + json.user_id + ")");
		$("#title").text(json.title);
		
		$("#content").html(json.content);

// 		var mainTableWidth = 600;
		
// 		var oneAttachWidth = 0;
		var attachCount = $(".attImg").length;
		
		if (attachCount > 0) {
			for(var i=0; i<attachCount; i++) {
// 				$(".attImg").on("click", "ddd");
// 				oneAttachWidth = $(".attImg:eq(" + i + ")").css("width");
// 				oneAttachWidth = erasePx(oneAttachWidth);
				
// 				if (parseInt(oneAttachWidth, 10) > parseInt(mainTableWidth, 10)) {
// 					$(".attImg:eq(" + i + ")").css("width", "100%");
// 				}
			}
		}
		
// 		function erasePx(_str) {
// 			if (_str == null || _str.length == 0) {
// 				return _str;
// 			}
			
// 			var len = _str.length;
// 			if (len < 2) {
// 				return _str;
// 			}
			
// 			var last2dig = _str.substring(len-2, len);
			
// 			if ("px" == last2dig || "PX" == last2dig) {
// 				return _str.substring(0, len-2);
// 			}
			
// 			return _str;
// 		}


	});
	
	
	function clickedImg(_this) {
		var src = $(_this).prop("src");
		window.open("/view/" + src);
	}
	
	function goBack() {
		history.back();
	}
	
	function goMainPage() {
		var bookId = "${bookId}";
		
		if (bookId != null && bookId.length > 0) {
			location.href = "/?bookId=" + bookId;

		} else {
			location.href = "/";
		}
	}
</script>
</head>
<body>
	<div id="pageBox" class="pageBox" style="position:absolute; left:10px; top:10px;" onclick="goBack()">
		<a href="javascript:goBack()">←</a>
	</div>
	<div class="mainPlate">
		<div class="center">
			<img src="/img/ddoc_title200.gif" class="titleImg" onclick="goMainPage()" style="cursor: pointer;">
		</div>
		<br>
		<table class="mainTable">
			<tr>
				<td colspan="2" id="title" class="titleFont"></td>
			</tr>
			<tr class="minGap">
				<td class="minGap"></td>
			</tr>
			<tr>
				<td colspan="2" id="content" style="table-layout:fixed; word-break:break-all;" class="basicLine basicpadding"></td>
			</tr>
			<tr class="minGap">
				<td class="minGap"></td>
			</tr>
			<tr>
				<td class="leftData">등록일</td>
				<td class="rightData" id="registTime"></td>
			</tr>
			<tr>
				<td class="leftData">최종수정일</td>
				<td class="rightData" id="updateTime"></td>
			</tr>
			<tr>
				<td class="leftData">필자</td>
				<td class="rightData" id="userName"></td>
			</tr>
		</table>
		<br>
		<div class="w100p" style="border-top: 1px solid #BBBBBB;"></div>
		<br>
		<%@ include file="/ddoc/read/menu_in_read.jsp"%>
		<br>
		<div class="center">
			<img src="/img/ddoc_title200.gif" class="titleSmallImg" onclick="goMainPage()" style="cursor: pointer;">
			<br>
			<span class="smallFont">bb_@naver.com</span>
		</div>
	</div>
	<br>
	<br>
</body>
</html>