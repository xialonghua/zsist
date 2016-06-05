package com.jiafang.util.weixin.com.tenpay;

import com.jiafang.util.StringUtil;
import com.jiafang.util.weixin.com.tenpay.client.TenpayHttpClient;
import com.jiafang.util.weixin.com.tenpay.util.ConstantUtil;
import com.jiafang.util.weixin.com.tenpay.util.JsonUtil;
import com.jiafang.util.weixin.com.tenpay.util.Sha1Util;
import com.jiafang.util.weixin.com.tenpay.util.XMLUtil;
import org.apache.struts2.json.JSONException;
import org.jdom.JDOMException;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrepayIdRequestHandler extends RequestHandler {

	public PrepayIdRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	/**
	 * ����ǩ��SHA1
	 * 
	 * @return
	 * @throws Exception
	 */
	public String createMD5() {
		StringBuffer sb = new StringBuffer();
		Set<String> es = super.getAllParameters().keySet();
        List<String> ks = new ArrayList<String>();
        ks.addAll(es);
        Collections.sort(ks);
		Iterator<String> it = ks.iterator();
		while (it.hasNext()) {
			String k = it.next();
			String v = getParameter(k);
            if (!k.startsWith("sign")){
                sb.append(k + "=" + v + "&");
            }

		}
		String params = sb.substring(0, sb.lastIndexOf("&")) + "&key=c6a59acbf0574c34a483e088591534ed";

		return StringUtil.MD5(params).toUpperCase();
	}

    public String getXmlBody() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\r\n");
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"appkey".equals(k)) {
                sb.append("<" + k + ">" + v + "</" + k + ">" + "\r\n");
            }
        }
        sb.append("</xml>\r\n");
        return sb.toString();
    }

	// �ύԤ֧��
	public String sendPrepay() throws JSONException {
		String prepayid = "";
		String params = getXmlBody();

		String requestUrl = super.getGateUrl();
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
				+ requestUrl);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
		}
        return resContent;
	}

}
