package com.thkmon.ddoc.doc.modify;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.common.prototype.BasicModel;

@Controller
public class ModifyModel extends BasicModel {

	@RequestMapping(value = "/m", method = {RequestMethod.GET, RequestMethod.POST})
	public void modify(HttpServletRequest req, HttpServletResponse res) {
		
		String userId = String.valueOf(req.getSession().getAttribute("userId"));
		
		if (userId == null || !userId.equals("bb_")) {
			
			try {
				PrintWriter writer = res.getWriter();
				writer.println("접근권한이 없습니다.");
				writer.close();
				
				return;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		String paramDocId = req.getParameter("docId");
		if (paramDocId == null || paramDocId.trim().length() == 0) {

			try {
				PrintWriter writer = res.getWriter();
				writer.println("docId 파라미터가 없습니다.");
				writer.close();
				
				return;
			} catch (Exception e){
				e.printStackTrace();
			}
			
		} else {
			paramDocId = paramDocId.trim();
		}
		
		forward(req, res, "/ddoc/write/write.jsp?docId=" + paramDocId);
	}

}
