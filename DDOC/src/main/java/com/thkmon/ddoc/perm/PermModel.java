package com.thkmon.ddoc.perm;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.common.util.PropertiesUtil;

@Controller
public class PermModel {
	
	
	/**
	 * 권한 부여를 위한 변수
	 */
	private static String pwd = null;
	
	
	@RequestMapping(value = "/setperm", method = {RequestMethod.GET, RequestMethod.POST})
	public void setperm(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			res.sendRedirect("/ddoc/perm/setperm.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/reqsetperm", method = {RequestMethod.POST})
	public void reqsetperm(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String paramPwd = req.getParameter("pwd");
			
			if (pwd == null) {
				readPwd();
			}
			
			if (pwd != null && pwd.equals(paramPwd)) {
				req.getSession().setAttribute("userId", "bb_");
				res.sendRedirect("/index.jsp");
				
			} else {
				PrintWriter writer = res.getWriter();
				writer.println("You don't have the permission.");
				writer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	synchronized public static void readPwd() {
		
		try {
			if (pwd == null) {
				pwd = PropertiesUtil.readPropertiesFile("/webstd_config/database.properties").get("pw");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
