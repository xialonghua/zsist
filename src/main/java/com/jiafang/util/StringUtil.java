package com.jiafang.util;

import com.jiafang.action.client.OrderAction;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {
    //非对称加密密钥算法
    public static final String KEY_ALGORITHM="RSA";
    //数字签名 签名/验证算法
    public static final String SIGNATURE_ALGORRITHM="SHA1withRSA";
	public static String encode(String input) {
		if (input == null)
			return null;
		StringBuilder output = new StringBuilder();
		for (int i = 0, c = input.length(); i < c; ++i) {
			char ch = input.charAt(i);

			if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= '０' && ch <= '９' || ch >= 'Ａ' && ch <= 'Ｚ'
					|| ch == '_' || ch == '-') {
				output.append(Integer.toHexString(ch)).append(' ');

			} else if (ch >= 'a' && ch <= 'z' || ch >= 'ａ' && ch <= 'ｚ') {
				output.append(Integer.toHexString((int) ch - 32)).append(' ');

			} else {
				Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
				if (block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || block == Character.UnicodeBlock.KATAKANA
						|| block == Character.UnicodeBlock.HIRAGANA) {
					output.append(Integer.toHexString(ch)).append(' ');
				} else {
					// do nothing
				}
			}

		}

		// trim blank
		int last = output.length() - 1;
		if (last > 0 && output.charAt(last) == ' ') {
			output.deleteCharAt(last);
		}

		return output.toString();
	}
	
	public static String decode(String str){
		String[] s = str.split(" ");
		StringBuffer sb = new StringBuffer();
		for(String ss : s){
			Integer c = Integer.decode("0x" + ss);
			sb.append((char)c.intValue());
		}
		return sb.toString();
	}
    static String sign = "JUAuudbPd5v9wrLMauu9scq9LOJvQGTv23xqt/ilew0JujsSSVFo40GQ48N2iR omAzJhWl4JXSq EjMN02PggIPrtJEMaisYRqd4Vj9U0W4TaDdgHi05HUGMpEVc84jroaHW7rYQfYvQOj2D c31GIo7HMobwGT6UmlsFdSvRM=";
    static String secret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    static String data = "body=爱的花海-紫&buyer_email=13671818054&buyer_id=2088122610674472&discount=0.00&gmt_create=2016-06-02 11:49:51&is_total_fee_adjust=Y&notify_id=f338e6b85e42cec95047641f6f989a9jmm&notify_time=2016-06-02 11:49:51&notify_type=trade_status_sync&out_trade_no=1000000042146483938147825&payment_type=1&price=0.01&quantity=1&seller_email=2827168@qq.com&seller_id=2088121694110155&subject=全棉12868系列&total_fee=0.01&trade_no=2016060221001004470284912897&trade_status=WAIT_BUYER_PAY&use_coupon=N";
    public static void main(String[] args) throws Exception {
//		System.out.println(encode("抗菌超纤维枕芯"));
//		System.out.println(decode(encode("你好啊v")));
//		System.out.println("" + getVerifyCode());
        ;

        System.out.println(verify(data.getBytes("utf-8"), secret, sign));
	}
	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * ×ª»»×Ö½ÚÊý×éÎª16½øÖÆ×Ö´®
	 * @param b ×Ö½ÚÊý×é
	 * @return 16½øÖÆ×Ö´®
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static int getVerifyCode(){
		StringBuffer sb = new StringBuffer();
		sb.append("" + (int)(Math.random() * 10));
		sb.append("" + (int)(Math.random() * 10));
		sb.append("" + (int)(Math.random() * 10));
		sb.append("" + (int)(Math.random() * 10));
		return Integer.parseInt(sb.toString());
	}

    public static boolean verify(byte[] data,String pk,String sign) throws Exception
    {
        BASE64Decoder base64Decoder= new BASE64Decoder();
        byte[] buffer= base64Decoder.decodeBuffer(pk);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
        RSAPublicKey publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
        java.security.Signature signature = java.security.Signature
                .getInstance("SHA1WithRSA");

        signature.initVerify(publicKey);
        signature.update(data);

        boolean bverify = signature.verify(base64Decoder.decodeBuffer(sign));
        return bverify;
    }

    public static Map<String, String> parseParam(String params){

        String[] ps = params.split("&");

        Map<String, String> map = new HashMap<>();

        for (String p : ps){
            String[] pps = p.split("=");
            map.put(pps[0], pps[1]);
        }
        return map;
    }

    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
