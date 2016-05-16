package com.jiafang.util;

import java.io.InputStream;
import java.util.Properties;

public class Config {

	static Properties prop;
	static {
		try {
			InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
			prop = new Properties();
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getAppName(){
		return prop.getProperty("appName");
	}
	public static String getShortName(){
		return prop.getProperty("shortName");
	}
	
	public static String getAppKey(){
		return prop.getProperty("appKey");
	}
	
	public static String getFileUrlWithoutHttp(){
		return prop.getProperty("fileURLWithoutHttp");
	}
	
	public static String getBucket(){
		return prop.getProperty("bucket");
	}
	
	public static String getContexUrl(){
		return prop.getProperty("_ctx_");
	}

	public static String getBaseUrl(){
		return prop.getProperty("baseUrl");
	}

	public static String getCssUrl(){
		return prop.getProperty("cssUrl");
	}
	
	public static String getVideoUrl(){
		return prop.getProperty("videoURL");
	}
	
	public static String getFileUrl(){
		return prop.getProperty("fileURL");
	}
}
