package com.thkmon.ddoc.book;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thkmon.webstd.common.prototype.BasicModel;
//import com.thkmon.webstd.common.util.UploadUtil;

public class BookModel extends BasicModel {

	public BookModel(HttpServletRequest req, HttpServletResponse res) {
//		super(req, res);
		
		String exec = req.getParameter("e");
		System.out.println("exec : " + exec);
		if (exec.equalsIgnoreCase("dowrite")) {
			writeBook(req, res);
		}
	}
	
	public void writeBook(HttpServletRequest req, HttpServletResponse res) {
//		try {			
//			// ������ ���ε��Ѵ�.
//			UploadUtil uploadFile = new UploadUtil();
////			uploadFile.uploadFile(req, res);
//			
//			System.out.println("���ε�");
//			// �����ͺ��̽��� �ٴ´�.
//			
//			setForward(false);
//			
//			Writer w = res.getWriter();
//			// w.write("<script>alert('����');</script>");
//			w.write("e");
//			w.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
