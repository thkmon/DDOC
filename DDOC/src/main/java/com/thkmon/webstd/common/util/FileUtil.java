package com.thkmon.webstd.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtil {
	
	public static void writeFile(File file, ArrayList<String> str_list) {
		
		BufferedWriter writer = null;
		
		try {

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			// (new OutputStreamWriter(new FileOutputStream(file),"MS949"));

			for (String str : str_list) {

				writer.write(str, 0, str.length());
				writer.newLine();

			}

			// ��ü ����
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(writer);
		}
	}
	
	public static void writeFile(File file, String content) {
		
		BufferedWriter writer = null;
		
		try {

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			// (new OutputStreamWriter(new FileOutputStream(file),"MS949"));

			writer.write(content);

			// ��ü ����
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(writer);
		}
	}

	public static String readFile(String path) throws Exception {
		if (path == null || path.trim().length() == 0) {
			throw new Exception("readFile : path is null or empty");
		} else {
			path = path.trim();
		}
		
		return readFile(new File(path));
	}
	
	public static String readFile(File file) throws Exception {
		
		StringBuffer content = new StringBuffer();
		
		BufferedReader reader = null;
		
		try {
			// ArrayList<String> str_list = new ArrayList<String>();

			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			// new OutputStreamWriter(new FileOutputStream(file),"MS949";

			String oneLine = "";

			while ((oneLine = reader.readLine()) != null) {
				content.append(oneLine);
				content.append("\r\n");
			}
			
			content.deleteCharAt(content.lastIndexOf("\n"));
			content.deleteCharAt(content.lastIndexOf("\r"));

			// ��ü ����
			reader.close();

		} catch (Exception e) {
			throw e;
			
		} finally {
			close(reader);
		}
		
		return content.toString();
	}

	public static boolean close(BufferedWriter obj) {
		try {
			if (obj != null) {
				obj.close();
			}
		} catch (Exception e) {
			obj = null;
			return false;
		}
		return true;
	}
	
	public static boolean close(BufferedReader obj) {
		try {
			if (obj != null) {
				obj.close();
			}
		} catch (Exception e) {
			obj = null;
			return false;
		}
		return true;
	}
}
