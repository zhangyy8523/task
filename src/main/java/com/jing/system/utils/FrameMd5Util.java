package com.jing.system.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * Md5工具类<br>
 * 示例：<br>
 * public static void main(String args[]) {
		String str1 = "admin";
		String str2 = getInstance().encodePassword(str1, "admin");
		System.out.println((new StringBuilder("明文:")).append(str1).toString());
		System.out.println((new StringBuilder("密文:")).append(str2).toString());
	}
 * @author yuejing
 * @date 2016年4月30日 下午7:18:16
 * @version V1.0.0
 */
public class FrameMd5Util {

	private static final Logger LOGGER = Logger.getLogger(FrameMd5Util.class);

	private static FrameMd5Util fiveClass;
	private static final String HEX_DIGITS[] = {
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"A", "B", "C", "D", "E", "F"
	};

	private FrameMd5Util() {
	}

	public static synchronized FrameMd5Util getInstance() {
		if (fiveClass == null) {
			fiveClass = new FrameMd5Util();
		}
		return fiveClass;
	}

	private String byteArrayToHexString(byte b[]) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			result.append(byteToHexString(b[i]));
		}
		return result.toString().toUpperCase();
	}

	private String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder(String.valueOf(HEX_DIGITS[d1]))).append(HEX_DIGITS[d2]).toString();
	}

	private byte[] md5Digest(byte src[]) throws NoSuchAlgorithmException {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		return alg.digest(src);
	}

	public String encodePassword(String string) {
		return encodePassword(string, null);
	}

	public String encodePassword(String string, String expStr) {
		String resultString = null;
		if(expStr == null) {
			resultString = new String(string);
		} else {
			resultString = new String(string + expStr);
		}
		try {
			resultString = byteArrayToHexString(md5Digest(resultString.getBytes()));
		} catch (Exception e) {
			LOGGER.error("加密异常: " + e.getMessage());
		}
		return resultString.toLowerCase();
	}

}