package com.hao.weixin.utils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA1";
	public static final String SHA_256 = "SHA-256";
	public static final String SHA_384 = "SHA-384";
	public static final String SHA_512 = "SHA-512";

	/**
	 * MD5、SHA加密
	 * 
	 * @param encryptionType
	 *            要加密的类型 (MD5、SHA1、SHA-256、SHA-384、SHA-512)
	 * @param s
	 *            要加密的数据
	 * @return 加密后的数据
	 * @throws GeneralSecurityException 
	 */
	public final static String MD5_SHA(String encryptionType, String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] strTemp = s.getBytes();
		char str[] = null;
		try {
			MessageDigest mdTemp = MessageDigest.getInstance(encryptionType);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
	}

	/**
	 * 默认MD5加密
	 * @param s	要加密的数据
	 * @return	加密后的数据
	 * @throws GeneralSecurityException
	 */
	public final static String MD5_SHA(String s) {
		return MD5_SHA(SHA1, s);
	}

}
