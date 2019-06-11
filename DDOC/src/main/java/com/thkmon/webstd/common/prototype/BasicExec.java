package com.thkmon.webstd.common.prototype;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicExec {
	
	public BasicExec (HttpServletRequest req, HttpServletResponse res, String paramE) {
		this.request = req;
		this.response = res;
	}
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public boolean setAttribute(String name, Object obj) {
		if (request == null) {
			System.out.println("request ��ü�� null�̹Ƿ� set�� ������ �� �����ϴ�.");
			return false;
		}
		
		request.setAttribute(name, obj);
		return true;
	}
	
	public Object getAttribute(String name) {
		if (request == null) {
			System.out.println("request ��ü�� null�̹Ƿ� get�� ������ �� �����ϴ�.");
			return null;
		}
		
		return request.getAttribute(name);
	}
	
	public void printExecResult(HttpServletResponse res, String text) {
		try {
			PrintWriter writer = res.getWriter();
			writer.println(text);
			writer.close();
			
		} catch (Exception e)  {
			System.err.println("printExecResult");
			e.printStackTrace();
		}
	}
}
