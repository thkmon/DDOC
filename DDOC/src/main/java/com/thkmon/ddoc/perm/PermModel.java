package com.thkmon.ddoc.perm;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.common.database.BasicMapper;

@Controller
public class PermModel {
	
	
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
			String pwd = BasicMapper.getPass();
			
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
}