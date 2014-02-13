package com.hao.weixin.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hao.weixin.face.service.FaceService;
import com.hao.weixin.message.resp.TextMessage;
import com.hao.weixin.utils.MessageUtil;

/** 
 * 核心服务类 
 *  
 */
public class WeixinService {
	/** 
	 * 处理微信发来的请求 
	 *  
	 * @param request 
	 * @return 
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容  
			String respContent = "the is damiwan！";

			// xml请求解析  
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）  
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号  
			String toUserName = requestMap.get("ToUserName");
			// 消息类型  
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");

			// 回复文本消息  
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			// 文本消息  
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				if(content.trim().startsWith("翻译")){
					respContent = MessageText.translate(content.trim());
				} else if(content.trim().startsWith("歌曲")){
					Map<String, String> map = MessageText.music(content.trim(), fromUserName, toUserName);
					if(null == map.get("respXml")){
						if(null == map.get("respContent")){
							respContent = MessageText.getUsage();
						}else{
							respContent = MessageText.getUsage();
						}
					}else{
						return map.get("respXml");
					}
				} else if(content.trim().startsWith("人脸检测")){
					respContent = MessageText.getFaceUsage();
				} else{
					//respContent = MessageText.a();
					//respContent = MessageText.qqFace(content);
					respContent = MessageText.xiaoQ(content);
				}
				
			}
			// 图片消息  
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// 取得图片地址  
                String picUrl = requestMap.get("PicUrl");  
                
				respContent = FaceService.detect(picUrl);
			}
			// 地理位置消息  
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息  
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息  
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送  
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型  
				String eventType = requestMap.get("Event");
				// 订阅  
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅  
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
				}
				// 自定义菜单点击事件  
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
  
                    if (eventKey.equals("11")) {  
                        respContent = "天气预报菜单项被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "公交查询菜单项被点击！";  
                    } else if (eventKey.equals("13")) {  
                        respContent = "周边搜索菜单项被点击！";  
                    } else if (eventKey.equals("14")) {  
                        respContent = TodayInHistoryService.getTodayInHistoryInfo();  
                    } else if (eventKey.equals("21")) {  
                        respContent = "歌曲点播菜单项被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "经典游戏菜单项被点击！";  
                    } else if (eventKey.equals("23")) {  
                        respContent = "美女电台菜单项被点击！";  
                    } else if (eventKey.equals("24")) {  
                        respContent = "人脸识别菜单项被点击！";  
                    } else if (eventKey.equals("25")) {  
                        respContent = "聊天唠嗑菜单项被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "Q友圈菜单项被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "电影排行榜菜单项被点击！";  
                    } else if (eventKey.equals("33")) {  
                        respContent = "幽默笑话菜单项被点击！";  
                    }
                    
				}
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	
}
