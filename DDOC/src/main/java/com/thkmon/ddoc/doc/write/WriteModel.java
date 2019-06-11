package com.thkmon.ddoc.doc.write;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.common.prototype.BasicModel;

@Controller
public class WriteModel extends BasicModel {
	
	
	@RequestMapping(value = "/w", method = {RequestMethod.GET, RequestMethod.POST})
	public void write(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String userId = String.valueOf(req.getSession().getAttribute("userId"));
		
			if (userId == null || !userId.equals("bb_")) {
				PrintWriter writer = res.getWriter();
				writer.println("You don't have the permission.");
				writer.close();
				
				return;
			}
			
			res.sendRedirect("/ddoc/write/write.jsp");
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}