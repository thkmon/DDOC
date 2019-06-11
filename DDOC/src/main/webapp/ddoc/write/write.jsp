<%@page import="com.thkmon.ddoc.book.BookController"%>
<%@page import="com.thkmon.webstd.common.prototype.BasicMap"%>
<%@page import="com.thkmon.webstd.common.prototype.BasicMapList"%>
<%@page import="com.thkmon.ddoc.doc.read.ReadDocController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String modifyDocId = "";
	String modifyTitle = "";
	String modifyContent = "";
	String modifyBookId = "b0";
	String secretDoc = "0";

	try {
		String sessionUserId = String.valueOf(session.getAttribute("userId"));
		if (sessionUserId == null || !sessionUserId.equals("bb_")) {
			throw new Exception("접근권한이 없습니다.");
		}
		
		modifyDocId = request.getParameter("docId");
		if (modifyDocId == null || modifyDocId.trim().length() == 0) {
			modifyDocId = "";
			
		} else {
			modifyDocId = modifyDocId.trim();
			
			ReadDocController readCtrl = new ReadDocController();
			BasicMapList readResult = readCtrl.readDoc(sessionUserId, modifyDocId);
			if (readResult == null || readResult.size() < 1) {
				throw new Exception("해당 문서가 존재하지 않습니다. (modifyDocId==" + modifyDocId + ")");
			}
			
			BasicMap oneDoc = readResult.get(0);
			modifyTitle = oneDoc.getString("title");
			modifyContent = oneDoc.getString("content");
			modifyBookId = oneDoc.getString("book_id");
			secretDoc = oneDoc.getString("secret");
		}
	
	} catch (Exception e) {
		out.print(e.getMessage());
		return;
	}
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
<script type="text/javascript" src="/js/util/dateUtil.js?v=${ver}" ></script>
<script type="text/javascript">
	var g_modifyDocId = "<%=modifyDocId%>";
	var g_secretDoc = "<%=secretDoc%>";
	
	window.onload = function loadPage() {
		
		if (g_secretDoc != null && g_secretDoc == "1") {
			var secretDocObj = $(".secretDoc").eq(0);
			secretDocObj.prop("checked", true);
		}
		
		if (g_modifyDocId != null && g_modifyDocId.length > 0) {
			$("#regDatetimeDiv").hide();
			
		} else {
			// 날짜 세팅
			var todayDateAndTime = DateUtil.getTodayDateTime();
			
			// var regDatetimeObj = $(".regDatetime").eq(0);
			// regDatetimeObj.val(todayDateAndTime);	
		}
		
	}
	
	/**
		첨부 버튼
		첨부 슬롯을 하나 추가한다.
	**/
	function addUploadSlot() {
		
		if ($("#writeForm").length < 1) {
			alert("writeForm 아이디가 존재하지 않습니다.");
			return false;
		}
		
		$("#writeForm").append(getOneSlot());
		
		var fileObjCount = $(".fileobj").length;
		if (fileObjCount > 0) {
			var lastIndex = fileObjCount - 1;
			$(".fileobj")[lastIndex].click();
		}
		
		var oneFileName = getAttachFileName(lastIndex);
		
		if (oneFileName == null || oneFileName.length == 0) {
			deleteAttachObject(lastIndex);
			return false;
			
		}
		
		var deleteButton = "<input type=\"button\" onclick=\"deleteAttach(" + lastIndex + ")\" value=\"삭제\"/>";
		
		var oneSlot = "<div class=\"attachSlot\">" + oneFileName + " " + deleteButton + "</div>";
		
		var preAttach = $("#attachList").html();
		$("#attachList").html(preAttach + oneSlot);
		
		var newAttachPath = "\<img class=\"attImg\" src=\"AttachPath_" + oneFileName + "\"\>";
		var preContent = $(".docContent").eq(0).text();
		if (preContent == null || preContent.length == 0) {
			$(".docContent").eq(0).html(preContent + newAttachPath);	
		} else {
			$(".docContent").eq(0).html(preContent + "\r\n" + newAttachPath);
		}
		
		// alert("첨부되었습니다.");
		return true;
	}

	function getOneSlot(_num) {
		var source = "";
		
		source += "<input type=\"file\" name=\"fileobj\" class=\"fileobj\" style=\"display:none;\"/>";
		// source += "<br/>";
		
		return source;
	}

	/**
	*	업로드 버튼을 눌렀을 때 동작
	*/
	function uploadFormData() {
		
		if ($("#writeForm").length < 1) {
			alert("writeForm 아이디가 존재하지 않습니다.");
			return false;
		}

		if (g_modifyDocId != null && g_modifyDocId.length > 0) {
			var preActionText = $("#writeForm").attr("action");
			preActionText = preActionText + "?modifyDocId=" + g_modifyDocId;
			$("#writeForm").attr("action", preActionText);
			$("#writeForm").submit();
			
		} else {
			$("#writeForm").submit();
		}
	}
	
	function deleteAttach(_index) {
		
		// alert(_index);
		
		// 문서 내용 속의 어태치 삭제
		deleteAttachInContent(_index);
		
		// 객체는 지우지 않고 path만 지운다.
		deleteAttachObjectPathOnly(_index);
		
		$(".attachSlot").eq(_index).hide();
	}
	
	function deleteAttachInContent(_index) {
		// 문서 내용 속의 어태치 삭제
		var oneFileName = getAttachFileName(_index);
		if (oneFileName == null || oneFileName.length == 0) {
			return false;
		}
		
		var attachTag = "\<img class=\"attImg\" src=\"AttachPath_" + oneFileName + "\"\>";
		
		var preContent = $(".docContent").eq(0).text();
		while (preContent.indexOf(attachTag) > -1) {
			preContent = preContent.replace(attachTag, "");
		}
		
		$(".docContent").eq(0).html(preContent);
	}
	
	function getAttachFileName(_index) {
		
		var oneFileName = "";
		var filePath = $(".fileobj")[_index].value;
		
		if (filePath == null || filePath.length == 0) {
			return "";
		}
		
		if (filePath.lastIndexOf("/") > -1) {
			oneFileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		} else {
			oneFileName = filePath;
		}
		
		if (filePath.lastIndexOf("\\") > -1) {
			oneFileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		} else {
			oneFileName = filePath;
		}
		
		return oneFileName;
	}
	
	// 파일 객체를 지운다.
	function deleteAttachObject(_index) {
		var obj = $(".fileobj")[_index];
		$(obj).remove();
	}
	
	// 객체는 지우지 않고 path만 지운다.
	function deleteAttachObjectPathOnly(_index) {
		// alert("as-is : " + $(".fileobj")[_index].value);
		$(".fileobj")[_index].value = "";
		// alert("to-be : " +$(".fileobj")[_index].value);
	}
	
	function changeBookId() {
		var a = $(".bookIdCombo option:selected").attr("id");
		$(".bookId").eq(0).val(a);
	}
</script>
<style type="text/css">
#uploadForm {
	width: 100%;
}

.mainPlate {
	width: 95%;
	max-width: 550px;
	margin: auto;
}

.docContent {
	width: 99%;
}

.docTitle {
	width: 100%;
}

.attachSlot {
	height: 30px;
	border: 1px solid blue;
	line-height: 30px;
}
</style>
</head>
<body>
	<div class="mainPlate" style="overflow: hidden;">	
		<!-- <form id="writeForm" action="/ddoc/write/writeDoc.jsp" method="post" enctype="multipart/form-data" target="hiddenFrame"> -->
		<form id="writeForm" action="/ddoc/write/writeDoc.jsp" method="post" target="hiddenFrame">
			<select name="bookIdCombo" class="bookIdCombo w50p" onchange="changeBookId()">
				<%
					BookController bookCtrl = new BookController();
					BasicMapList bookList = bookCtrl.getBookList("bb_");
					BasicMap bookMap = null;
					String bookTitle = "";
					String bookId = "";
					StringBuffer buf = null;
					
					if (bookList != null && bookList.size() > 0) {
						int bookCount = bookList.size();
						for (int i=0; i<bookCount; i++) {
							bookMap = bookList.get(i);	
							bookTitle = bookMap.getString("title");
							bookId = bookMap.getString("book_id");
							
							if (bookId == null || bookId.length() == 0) {
								continue;
							}
							
							if (bookTitle == null || bookTitle.length() == 0) {
								continue;
							}
								
							buf = new StringBuffer();
							buf.append("<option");
							
							if (bookId.equals(modifyBookId)) {
								buf.append(" selected=\"selected\" ");
							}
							
							buf.append(" id=\"");
							buf.append(bookId);
							buf.append("\"");
							buf.append(" >");
							
							buf.append(bookTitle);
							buf.append("</option>");
							
							out.print(buf.toString());
						}
						
					}
				%>
			</select>
			<input type="hidden" name="bookId" class="bookId" value="<%=modifyBookId%>"/>
			<input type="text" name="docTitle" class="docTitle" value="<%=modifyTitle%>"/>
			<input type="checkbox" name="secretDoc" class="secretDoc"/>&nbsp;비밀글
			<div id="regDatetimeDiv">
				DATE : <input type="text" name="regDatetime" class="regDatetime" value=""/>
			</div>
<%-- 			<input style="display: none;" type="text" name="modifyDocId" class="modifyDocId" value="<%=modifyDocId%>"/> --%>
			<textarea rows="10" cols="10" name="docContent" class="docContent"><%=modifyContent%></textarea>
		</form>
		<div id="attachList"></div>
		<input type="button" value="첨부" onclick="addUploadSlot()"/>
		&nbsp;
		<input type="button" class="uploadButton" value="업로드" onclick="uploadFormData()"/>
		<br/>
	</div>
	<iframe name="hiddenFrame" style="width: 1px; height: 1px; border: 0;"></iframe>
</body>
</html>