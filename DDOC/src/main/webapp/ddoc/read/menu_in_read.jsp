<%@page import="com.thkmon.ddoc.open.OpenController"%>
<%	
	String docId = String.valueOf(request.getAttribute("docId"));
	String htmlCon = new OpenController().openMenuInRead(request, docId);
	if (htmlCon != null) {
		out.print(htmlCon);	
	}
%>