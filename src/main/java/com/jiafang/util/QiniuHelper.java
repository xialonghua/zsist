package com.jiafang.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
/**
 * 
 * @author zj
 *jdk 1.7 +
 */
public class QiniuHelper {
	static QiniuHelper helper = new QiniuHelper();
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String ACCESS_KEY = "Oo0xoMRiwPxyVjzIq0bNynY6YCh2ZSc8m5CbBJiT";
	private static final String SECRET_KEY = "KJt0Is640mwbJ9sj6Aov6MMcbDACfH9xOjd8Duvq";
	private static final String LOAD_TOKEN = Config.getBucket();
	private static final String DOWN_URL = Config.getFileUrlWithoutHttp();
	private static Auth auth;
	// doc http://developer.qiniu.com/docs/v6/sdk/java-sdk.html
	private static UploadManager uploadManager = new UploadManager();
	private static OperationManager operater;
	public QiniuHelper() {
		if(auth == null){
			auth = Auth.create(ACCESS_KEY, SECRET_KEY);
			operater = new OperationManager(auth);
		}
	}

	/**
	 * 上传图片消息
	 * 
	 * @param key
	 * @param filePath
	 * @return
	 */
	public boolean uploadImageFile(String key, byte[] fileBytes) {
		try {
			Response response = uploadManager.put(fileBytes, key, getLiveVideoToken());
			return response.isOK();
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return false;
	}


	private String getLiveVideoToken() {
		return auth.uploadToken(LOAD_TOKEN);
	}


	public String getLiveVideoDownUrl(String filename) {
		return DOWN_URL+filename;
	}

	/**
	 * 生成安全的url
	 * 
	 * @param downUrl
	 * @param expireSecond
	 *            url过期时间.单位 小时。默认1
	 * @return
	 */
	public String genSaftUrl(String downUrl, Integer expireHour) {
		StringBuffer downloadUrl = new StringBuffer();
		try {
			if (expireHour == null||0==expireHour) {
				expireHour = 1;
			}
			downloadUrl.append(downUrl);
			downloadUrl.append("?e=" + (System.currentTimeMillis() / 1000 + 3600*expireHour));// 过期时间1小时
			byte[] sign = getSignature(downloadUrl.toString(), SECRET_KEY);
			String urlBase64 = Base64.encodeBase64URLSafeString(sign);
			downloadUrl.append("&token=").append(ACCESS_KEY).append(":").append(urlBase64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadUrl.toString();
	}
	
	public static String getToken(){
		return auth.uploadToken(LOAD_TOKEN);
	}

	public static byte[] getSignature(String data, String key) throws Exception {
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		return mac.doFinal(data.getBytes());
	}

	public static void main(String[] args) throws Exception {
		QiniuHelper qiniuSeviceImpl = new QiniuHelper();
		String filename = "aa.jpeg";
//		if (qiniuSeviceImpl.uploadLiveVideoFile(filename, "d://aa.jpeg")) {
//
//		}
//		
		String url = qiniuSeviceImpl.genSaftUrl(qiniuSeviceImpl.getLiveVideoDownUrl(filename), 1);
		System.out.println(url);
	}
	
	public static void thumbVideo(String key){
		String notifyURL = "";
		boolean force = true;
		String pipeline = "";

		StringMap params = new StringMap().putNotEmpty("notifyURL", notifyURL)
		        .putWhen("force", 1, force).putNotEmpty("pipeline", pipeline);

		String fops = "avthumb/mp4/vcodec/libx264/acodec/libfaac/stripmeta/1/s/320x480";
		fops += "|saveas/" + UrlSafeBase64.encodeToString(LOAD_TOKEN + ":" + key + "_thumb");

		try {
		        // 针对指定空间的文件触发 pfop 操作
		        String id = operater.pfop(LOAD_TOKEN, key, fops, params);
		        // 可通过下列地址查看处理状态信息。
		        // 实际项目中设置 notifyURL，接受通知。通知内容和处理完成后的查看信息一致。
		        String url = "http://api.qiniu.com/status/get/prefop?id=" + id;
		    } catch (QiniuException e) {
		    	e.printStackTrace();
		        Response r = e.response;
		        // 请求失败时简单状态信息
//		        log.error(r.toString());
//		        try {
//		            // 响应的文本信息
//		            log.error(r.bodyString());
//		        } catch (QiniuException e1) {
//		            //ignore
//		        }
		    }

	}
	
}
