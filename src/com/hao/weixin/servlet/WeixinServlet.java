package com.hao.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hao.weixin.service.WeixinService;
import com.hao.weixin.utils.SignUtil;

/** 
 * 核心请求处理�?
 *  
 */
public class WeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** 
	 * 确认请求来自微信服务�?
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名  
		String signature = request.getParameter("signature");
		// 时间�? 
		String timestamp = request.getParameter("timestamp");
		// 随机�? 
		String nonce = request.getParameter("nonce");
		// 随机字符�? 
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过�?��signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}

		out.close();
		out = null;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求�?响应的编码均设置为UTF-8（防止中文乱码）  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息�?处理消息  
		String respMessage = WeixinService.processRequest(request);

		// 响应消息  
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

}
