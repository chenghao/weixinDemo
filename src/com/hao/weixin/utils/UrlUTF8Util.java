package com.hao.weixin.utils;

import java.io.UnsupportedEncodingException;

public class UrlUTF8Util {

	/**
	 * UTF-8编码
	 * 
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
