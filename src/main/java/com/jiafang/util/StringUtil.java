package com.jiafang.util;

import java.security.MessageDigest;

public class StringUtil {
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
	
	public static void main(String[] args) {
		System.out.println(encode("抗菌超纤维枕芯"));
		System.out.println(decode(encode("你好啊v")));
		System.out.println("" + getVerifyCode());
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
}
