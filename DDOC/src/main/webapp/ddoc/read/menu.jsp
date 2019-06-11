<%@page import="com.thkmon.ddoc.doc.read.ReadDocController"%>
<%@page import="com.thkmon.ddoc.doc.read.ReadDocMapper"%>
<%@page import="com.thkmon.ddoc.open.OpenController"%>
<script type="text/javascript" src="/js/lib/jquery-1.8.3.min.js?v=${ver}"></script>
<script type="text/javascript">
	function changeBook() {
		var selectedBookId = $("#bookListCombo option:selected").prop("id");
		
		if (selectedBookId == null || selectedBookId.length == 0) {
			alert("선택한 bookId를 알 수 없습니다.");
			return false;
		}
		
		location.href = "?bookId=" + selectedBookId;
	}
	
	function changePage(_page) {
		var selectedBookId = $("#bookListCombo option:selected").prop("id");
		
		if (selectedBookId == null || selectedBookId.length == 0) {
			location.href = "?p=" + _page;
			
		} else {
			location.href = "?bookId=" + selectedBookId + "&p=" + _page;
		}
		
		
	}
</script>
<%
	String htmlCon1 = new OpenController().openBookList(request);
	if (htmlCon1 != null) {
		out.print(htmlCon1);	
	}
	
	String htmlCon = new OpenController().openCommonDocList2(request);
	if (htmlCon != null) {
		out.print(htmlCon);	
	}
%>