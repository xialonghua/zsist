package com.jiafang.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.fileupload.util.Streams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class sendsms {

	private static String Url = "http://sms.106jiekou.com/utf8/sms.aspx";

	public static boolean sendMsg(String mobile, int codee) throws Exception {

		String encoding = "UTF-8";
		int mobile_code = codee;

		// System.out.println(mobile);
		String content = new String("您的验证码是：" + mobile_code + "。如需帮助请联系客服。");

		String path = Url;
		String params = "account=xialonghua&password=fb422425" + "&mobile=" + mobile
				+ "&content=" + content;
		byte[] data = params.getBytes(encoding);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		// application/x-javascript text/xml->xml数据
		// application/x-javascript->json对象
		// application/x-www-form-urlencoded->表单数据
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setConnectTimeout(5 * 1000);
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		
		InputStream inStream = conn.getInputStream();
		String result = Streams.asString(inStream, "UTF-8");


		String code = result;
		System.out.println(code); // 响应代码 200表示成功
		if ("100".equals(code)) {
			return true;
		}
		return false;

	}

}