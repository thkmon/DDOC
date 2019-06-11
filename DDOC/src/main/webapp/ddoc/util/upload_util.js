$(window).load(function(){
	
	if ($("#uploadDiv").length < 1) {
		alert("upload_util.js : uploadDiv 아이디가 존재하지 않습니다.");
		return false;
	}
	
	var source = "";
	source += "<input type=\"button\" value=\"추가\" onclick=\"addUploadSlot()\"/>"
	source += "&nbsp;";
	source += "<input type=\"button\" class=\"uploadButton\" value=\"업로드\" onclick=\"uploadFormData()\"/>";
	source += "<br/>";
	source += "<br/>";
	source += "<form id=\"uploadForm\" action=\"/ddoc/util/upload_util.jsp\" method=\"post\" enctype=\"multipart/form-data\" target=\"_blank\">";		
	source += getOneSlot(0);
	source += "</form>";
	
	$("#uploadDiv").html(source);
	
});

function addUploadSlot() {
	
	if ($("#uploadForm").length < 1) {
		alert("upload_util.js : uploadForm 아이디가 존재하지 않습니다.");
		return false;
	}
	
	$("#uploadForm").append(getOneSlot());
}

function getOneSlot(_num) {
	var source = "";
	
	source += "<input type=\"file\" name=\"fileobj\" class=\"fileobj\"/>";
	source += "<br/>";
	
	return source;
}

function uploadFormData() {
	
	if ($("#uploadForm").length < 1) {
		alert("upload_util.js : uploadForm 아이디가 존재하지 않습니다.");
		return false;
	}
	
	$("#uploadForm").submit();
}