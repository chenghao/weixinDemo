package com.hao.weixin.utils;

import java.util.Arrays;

public class SignUtil {
	// 与接口配置信息中的Token要一致  
	private static String token = "damiwan";

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序  
		Arrays.sort(arr);

		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		String tmpStr = Crypto.MD5_SHA(content.toString());

		//回收垃圾
		content = null;

		return tmpStr != null ? tmpStr.equalsIgnoreCase(signature) : false;
	}
}
