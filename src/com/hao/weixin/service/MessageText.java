package com.hao.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hao.weixin.message.resp.Music;
import com.hao.weixin.message.resp.MusicMessage;
import com.hao.weixin.music.service.BaiduMusicService;
import com.hao.weixin.translate.service.BaiduTranslateService;
import com.hao.weixin.utils.MessageUtil;
import com.hao.weixin.utils.QQFaceUtil;

public class MessageText {

	public static String a(){
		return "<a href=\"chengh.sinaapp.com\">程豪的博客</a>";
	}
	
	public static String xiaoQ(String content){
		StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好，我是小q，提供如下服务：").append("\n\n");  
	    buffer.append("翻译  输入：翻译你好  输入翻译查看详细信息").append("\n\n");  
	    buffer.append("音乐  输入：歌曲存在@汪峰  输入歌曲查看详细信息").append("\n\n");  
	    buffer.append("人脸检测  发送一张清晰照片  输入人脸检测查看详细").append("\n\n");  
	    buffer.append("聊天唠嗑").append("\n\n");  
	    buffer.append("回复“?”显示此帮助菜单");  
	    return buffer.toString();
	}
	
	public static String qqFace(String content){
		String[] strs = content.split("/:");
		String faceStr = "";
		for(int i = 1; i < strs.length; i++){
			if(QQFaceUtil.isQqFace("/:"+strs[i])){
				faceStr += "/:"+strs[i];
			}
		}
		return faceStr;
	}
	
	public static String translate(String content){
		if (content.startsWith("翻译")) {  
	        String keyWord = content.replaceAll("^翻译", "").trim();
	        
	        if ("".equals(keyWord)) {  
	            return getTranslateUsage();  
	        } else {  
	            return BaiduTranslateService.translate(keyWord);  
	        }  
	    }
		return content;  
	}
	/** 
	 * Q译通使用指南 
	 * @return 
	 */  
	private static String getTranslateUsage() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("Q译通使用指南").append("\n\n");  
	    buffer.append("Q译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");  
	    buffer.append("    中 -> 英").append("\n");  
	    buffer.append("    英 -> 中").append("\n");  
	    buffer.append("    日 -> 中").append("\n\n");  
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("    翻译我是中国人").append("\n");  
	    buffer.append("    翻译dream").append("\n");  
	    buffer.append("    翻译さようなら").append("\n\n");  
	    buffer.append("回复“?”显示主菜单");  
	    return buffer.toString();  
	}  
	
	public static Map<String, String> music(String content, String fromUserName, String toUserName){
		String respXml = null;
		String respContent = null;
		// 如果以“歌曲”2个字开头  
        if (content.startsWith("歌曲")) {  
            // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
            String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");  
            // 如果歌曲名称为空  
            if ("".equals(keyWord)) {  
            	respContent = getUsage();  
            } else {  
                String[] kwArr = keyWord.split("@");  
                // 歌曲名称  
                String musicTitle = kwArr[0];  
                // 演唱者默认为空  
                String musicAuthor = "";  
                if (2 == kwArr.length)  
                    musicAuthor = kwArr[1];  

                // 搜索音乐  
                Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);  
                // 未搜索到音乐  
                if (null == music) {  
                	respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";  
                } else {  
                    // 音乐消息  
                	MusicMessage musicMessage = new MusicMessage();  
                    musicMessage.setToUserName(fromUserName);  
                    musicMessage.setFromUserName(toUserName);  
                    musicMessage.setCreateTime(new Date().getTime());  
                    musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);  
                	musicMessage.setMusic(music);
                	
                    respXml = MessageUtil.musicMessageToXml(musicMessage);  
                }  
            }  
        }
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("respXml", respXml);
        map.put("respContent", respContent);
		return map;
        
	}
	/** 
     * 歌曲点播使用指南 
     * @return 
     */  
    public static String getUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("歌曲点播操作指南").append("\n\n");  
        buffer.append("回复：歌曲+歌名").append("\n");  
        buffer.append("例如：歌曲存在").append("\n");  
        buffer.append("或者：歌曲存在@汪峰").append("\n\n");  
        buffer.append("回复“?”显示主菜单");  
        return buffer.toString();  
    }  
    
    /** 
     * 人脸检测帮助菜单 
     */  
    public static String getFaceUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("人脸检测使用指南").append("\n\n");  
        buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");  
        buffer.append("快来试试你是不是长得太着急");  
        return buffer.toString();  
    }  
}
