package com.hao.weixin.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 历史上的今天查询服务 
 *  
 *  
 */ 
public class TodayInHistoryService {
	/** 
     * 发起http get请求获取网页源代码 
     *  
     */  
    private static String httpRequest(String requestUrl) {  
        StringBuffer buffer = null;  
  
        try {  
            // 建立连接  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            // 获取输入流  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            // 读取返回结果  
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
            // 释放资源  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            httpUrlConn.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer.toString();  
    }  
  
    /** 
     * 从html中抽取出历史上的今天信息 
     *  
     * @param html 
     * @return 
     */  
    private static String extract(String html) {  
        StringBuffer buffer = null;  
        // 日期标签：区分是昨天还是今天  
        String dateTag = getMonthDay(0);  
  
		/*我们使用括号()将正则表达式规则分成了5组，下面是这些分组的说明：
		第1组：(.*)表示网页源代码中<div class="listren">标签之前还有任意多个字符。
		第2组：(<div class=\"listren\">)中的反斜杠表示转义，所以该规则就是用于匹配<div class="listren">。
		第3组：(.*?)表示在标签<div class="listren">和</div>之间的所有内容，这才是我们真正需要的数据所在。
		第4组：(</div>)就是用于匹配<div class="listren">的结束标签。
		第5组：(.*)表示在</div>标签之后还有任意多的字符。*/
        Pattern p = Pattern.compile("(.*)(<div class=\"listren\">)(.*?)(</div>)(.*)");  
        Matcher m = p.matcher(html);  
        if (m.matches()) {  
            buffer = new StringBuffer();  
            
            /*掌握了正则表达式规则的含义，就不难理解为什么在extract()方法中全都是在使用m.group(3)，
             * 因为m.group(3)就表示匹配到数据的第3个分组。m.group(3)的内容如下：
             * <ul>     <li><a href="/news6836/" title="0690年10月16日 武则天登上皇位">0690年10月16日 武则天登上皇位</a>  （图） </li>
             * 	……
             * </ul>
             */
            if (m.group(3).contains(getMonthDay(-1)))  
                dateTag = getMonthDay(-1);  
  
            // 拼装标题  
            buffer.append("≡ ").append("历史上的").append(dateTag).append(" ≡").append("\n\n");  
  
            // 抽取需要的数据  
            for (String info : m.group(3).split("&nbsp;&nbsp;")) {  
            	//全部过滤掉大量的html标签、空格、换行、无关字符等
                info = info.replace(dateTag, "").replace("（图）", "").replaceAll("</?[^>]+>", "").trim();  
               
                // 在每行末尾追加2个换行符  
                if (!"".equals(info)) {  
                    buffer.append(info).append("\n\n");  
                }  
            }  
        }  
        // 将buffer最后两个换行符移除并返回  
        return (null == buffer) ? null : buffer.substring(0, buffer.lastIndexOf("\n\n"));  
    }  
  
    /** 
     * 获取前/后n天日期(M月d日) 
     *  
     * @return 
     */  
    private static String getMonthDay(int diff) {  
        DateFormat df = new SimpleDateFormat("M月d日");  
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DAY_OF_YEAR, diff);  
        return df.format(c.getTime());  
    }  
  
    /** 
     * 封装历史上的今天查询方法，供外部调用 
     *  
     * @return 
     */  
    public static String getTodayInHistoryInfo() {  
        // 获取网页源代码  
        String html = httpRequest("http://www.rijiben.com/");  
        // 从网页中抽取信息  
        String result = extract(html);  
  
        return result;  
    }  
}
