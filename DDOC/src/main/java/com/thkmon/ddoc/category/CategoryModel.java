package com.thkmon.ddoc.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thkmon.webstd.common.prototype.BasicModel;

public class CategoryModel extends BasicModel {

	public CategoryModel(HttpServletRequest req, HttpServletResponse res) {
//		super(req, res);

		// req.getSession().Attribute("userId", "bb_");
		
		String userId = String.valueOf(req.getSession().getAttribute("userId"));
		
		if (userId == null || !userId.equals("bb_")) {
			
			try {
				res.getOutputStream().print("������ �� �����ϴ�.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
