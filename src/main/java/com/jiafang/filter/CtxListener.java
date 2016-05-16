package com.jiafang.filter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.jiafang.util.Config;
import com.jiafang.util.QiniuHelper;

public class CtxListener implements ServletRequestListener {

    public CtxListener() {
    	
    }
    
	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre) {
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
	public void requestInitialized(ServletRequestEvent sre) {
    	HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
    	
    	String ctx = Config.getContexUrl();
    	req.setAttribute("_ctx_", ctx);
        String baseUrl = Config.getBaseUrl();
        String cssUrl = Config.getCssUrl();
		req.setAttribute("baseUrl", baseUrl);
		req.setAttribute("cssUrl", cssUrl);
    	
    	req.setAttribute("domain", Config.getFileUrl());
		
//    	req.setAttribute("_ctx_", "http://www.zsist.com/baihuo");
//    	req.setAttribute("_ctx_", "http://localhost:8080/bu");
		req.setAttribute("fileURL", Config.getFileUrl());
		req.setAttribute("videoURL", Config.getVideoUrl());
		
		req.setAttribute("bucket", Config.getBucket());
		req.setAttribute("token", QiniuHelper.getToken());
		
		req.setAttribute("SysName", Config.getShortName());
    	//baihuo
//    	req.setAttribute("fileURL", "http://7xp0dt.com2.z0.glb.qiniucdn.com" );
//		req.setAttribute("videoURL", "http://7xp0dt.com2.z0.glb.qiniucdn.com" );
    	
	}
}
	
