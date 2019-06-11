package com.thkmon.webstd.common.prototype;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thkmon.webstd.common.util.UniqueKeyUtil;

public class BasicModel {
	
//	private String pageToForward = null;
//	private boolean forward = true;
//	
//	private StringList pageContextAttribute = null;
//
//	public String getPageToForward() {
//		if (pageToForward == null) {
//			return "";
//		}
//		return pageToForward;
//	}
//	public void setPageToForward(String pageToForward) {
//		this.pageToForward = pageToForward;
//	}
//	public boolean isForward() {
//		return forward;
//	}
//	public void setForward(boolean forward) {
//		this.forward = forward;
//	}
//	/**
//	 * BasicModel
//	 * @param req
//	 * @param res
//	 */
//	public BasicModel(HttpServletRequest req, HttpServletResponse res) {
//		this.request = req;
//		this.response = res;
//	}

//	private HttpServletRequest request = null;
//	private HttpServletResponse response = null;
	
//	public HttpServletRequest getRequest() {
//		return request;
//	}
//	public void setRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//	public HttpServletResponse getResponse() {
//		return response;
//	}
//	public void setResponse(HttpServletResponse response) {
//		this.response = response;
//	}
//	
//	public boolean setAttribute(String name, Object obj) {
//		if (request == null) {
//			System.out.println("request 객체가 null이므로 set을 수행할 수 없습니다.");
//			return false;
//		}
//		
//		request.setAttribute(name, obj);
//		return true;
//	}
//	
//	public Object getAttribute(String name) {
//		if (request == null) {
//			System.out.println("request 객체가 null이므로 get을 수행할 수 없습니다.");
//			return null;
//		}
//		
//		return request.getAttribute(name);
//	}
	
	public void drawHtmlCode(HttpServletResponse res, String text) {
		try {
			PrintWriter writer = res.getWriter();
			writer.println(text);
			writer.close();
			
		} catch (Exception e)  {
			System.err.println("drawHtmlCode");
			e.printStackTrace();
		}
	}
	
//
//	public String forwardPath = "";
//
//	public String getForwardPath() {
//		return forwardPath;
//	}
//
//	public void setForwardPath(String forwardPath) {
//		this.forwardPath = forwardPath;
//	}
//	
//	public void mainProcess() {
//		
//	}
//	
	
	public void forward(HttpServletRequest request, HttpServletResponse response, String jspPathToForward) {
		
		if (jspPathToForward == null) {
			jspPathToForward = "";
		}
		
		// v= 가 존재하지 않을 경우 주소 뒤에 붙인다.
		if (jspPathToForward.indexOf("v=") < 0) {
			if (jspPathToForward.indexOf("?") > -1) {
				jspPathToForward = jspPathToForward + "&v=" + UniqueKeyUtil.createUniqueKey();
				
			} else {
				jspPathToForward = jspPathToForward + "?v=" + UniqueKeyUtil.createUniqueKey();
			}
		}
		
		System.out.println("Forward to JSP page : " + jspPathToForward);
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPathToForward);
		
		try {
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			System.out.println("해당 주소로 포워드할 수 없습니다. (" + jspPathToForward + ") " + e.getClass().getName() + " : " + e.getMessage());
		}
		
		// forward가 완료되면 pageContextAttribute를 제거한다.
//		if (pageContextAttribute != null && pageContextAttribute.size() > 0) {
//			int attCount = pageContextAttribute.size();
//			for (int i=0; i<attCount; i++) {
//				// System.out.println("Attribute 삭제 전 : " + request.getAttribute(pageContextAttribute.get(i)));
//				request.removeAttribute(pageContextAttribute.get(i));
//				// System.out.println("Attribute 삭제 후 : " + request.getAttribute(pageContextAttribute.get(i)));
//			}
//			pageContextAttribute = null;
//		}
	}
	
//	/**
//	 * pageContextAttribute를 set 한다.
//	 * 실제 pageContext에 넣지만, 해당 attribute는 forward 된 후 지워지도록 처리된다. (StringList 로 관리한다.)
//	 * 
//	 * @param name
//	 * @param obj
//	 */
//	public void setPageContextAttribute(String name, Object obj) {
//		if (pageContextAttribute == null) {
//			pageContextAttribute = new StringList();
//		}
//		
//		pageContextAttribute.add(name);
//		request.setAttribute(name, obj);
//	}
//	
//	public void forwardErrorPage(Exception e) {
//		setPageContextAttribute("errorKey", UniqueKeyUtil.createErrorKey());
//		setPageContextAttribute("errorMsg", e.getMessage());
//		forward("/ddoc/common/errorPage.jsp");
//	}
}
