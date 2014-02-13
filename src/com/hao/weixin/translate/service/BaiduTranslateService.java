package com.hao.weixin.translate.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import com.hao.weixin.utils.UrlUTF8Util;

/**
 * 百度翻譯
 * 
 * @author damiwan
 * 
 */
public class BaiduTranslateService {
	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @return
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
		}
		return buffer.toString();
	}

	/**
	 * 翻译（中->英   英->中  日->中）
	 * 
	 * @param source
	 * @return
	 */
	public static String translate(String source) {
		String dst = null;

		// 组装查询地址
		//对于智能翻译，参数from和to的传都是auto
		//client_id是开发者百度appid
		String requestUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=chWvQqHoDPDncojcPUFZjhpP&q={keyWord}&from=auto&to=auto";
		// 对参数q的值进行urlEncode utf-8编码
		requestUrl = requestUrl.replace("{keyWord}", UrlUTF8Util.urlEncodeUTF8(source));

		// 查询并解析结果
		try {
			// 查询并获取返回结果
			String json = httpRequest(requestUrl);
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			String trans_result = jsonObject.getString("trans_result");
			JSONObject transResultJson = JSONObject.fromObject(trans_result.substring(1, trans_result.length()-1));
			dst = transResultJson.getString("dst");
			
			// 通过Gson工具将json转换成TranslateResult对象
			//TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
			// 取出translateResult中的译文
			//dst = translateResult.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == dst)
			dst = "翻译系统异常，请稍候尝试！";
		return dst;
	}

	public static void main(String[] args) {
		// 翻译结果：The network really powerful
		System.out.println(translate("さようなら"));
	}
}
