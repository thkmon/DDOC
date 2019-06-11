package com.thkmon.ddoc.doc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

@Controller
public class DocModel {
	
	public void uploadDoc(HttpServletRequest req, HttpServletResponse res) {
		
		try {			
//			// 파일을 업로드한다.
//			UploadUtil uploadFile = new UploadUtil();
//			// uploadFile.uploadFile(req, res);
//			
//			System.out.println("업로드");
//			// 데이터베이스에 붙는다.
//			
//			setForward(false);
//			
//			Writer w = res.getWriter();
//			// w.write("<script>alert('성공');</script>");
//			w.write("e");
//			w.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
