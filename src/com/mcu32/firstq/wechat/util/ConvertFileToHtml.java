package com.mcu32.firstq.wechat.util;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.ConnectException;  
import java.nio.charset.Charset;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.artofsolving.jodconverter.DocumentConverter;  
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;  
  
/** 
 * * 端口启动命令： 
 * soffice -headless -accept="socket,port=8100;urp; 
 * 
 *  
 * author  牟云飞 
 * company 海颐软件股份有限公司 
 * tel     15562579597 
 * qq      1147417467 
 * team    客服产品中心/于洋 
 *  
 */  
public class ConvertFileToHtml {  
    /** 
     * 将word文档转换成html文档 
     * @param docFile   需要转换的word文档 
     * @param filepath  转换之后html的存放路径 
     * @return 转换之后的html文件 
     */  
    public static File convert(File docFile, String filepath) {  
  
        // 创建保存html的文件  
        String fileName=docFile.getName();  
        File htmlFile = new File(filepath + "/" + fileName + ".html");  
        // 创建Openoffice连接  
        OpenOfficeConnection con = new SocketOpenOfficeConnection(8100);  
        try {  
            // 连接  
            con.connect();  
        } catch (ConnectException e) {  
            System.out.println("获取OpenOffice连接失败...");  
            e.printStackTrace();  
        }  
          
        // 创建转换器  
        DocumentConverter converter = new OpenOfficeDocumentConverter(con);  
        // 转换文档问html
        converter.convert(docFile, htmlFile);  
        // 关闭openoffice连接  
        con.disconnect();  
        return htmlFile;  
    }  
  
    /** 
     *  
     * 将word转换成html文件，并且获取html文件代码。 
     * @param docFile  需要转换的文档 
     * @param filepath  文档中图片的保存位置 
     * @return 转换成功的html代码 
     */  
    public static String toHtmlString(File docFile, String filepath) {  
        // 转换word文档  
        File htmlFile = convert(docFile, filepath);  
        System.out.println(htmlFile.getAbsolutePath());  
        // 获取html文件流  
        StringBuffer htmlSb = new StringBuffer();  
        try {  
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile),Charset.forName("gb2312")));  
            while (br.ready()) {
            	String utfStr=br.readLine();
            	if(StringUtils.isNotEmpty(utfStr)&& utfStr.indexOf("gb2312")>0){
            		utfStr=utfStr.replace("gb2312", "UTF-8");
            	}
                htmlSb.append(utfStr);  
            }  
            br.close();  
            // 删除临时文件  
            //htmlFile.delete();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        // HTML文件字符串  
        String htmlStr = htmlSb.toString();  
        //System.out.println("htmlStr=" + htmlStr);  
        // 返回经过清洁的html文本  
        return clearFormat(htmlStr, filepath);  
    }  
  
    /** 
     *  
     * 清除一些不需要的html标记 
    */  
  
    public static String clearFormat(String htmlStr, String docImgPath) {  
  
        // 获取body内容的正则  
        String bodyReg = "<BODY .*</BODY>";  
        Pattern bodyPattern = Pattern.compile(bodyReg);  
        Matcher bodyMatcher = bodyPattern.matcher(htmlStr);  
        if (bodyMatcher.find()) {  
            // 获取BODY内容，并转化BODY标签为DIV  
            htmlStr = bodyMatcher.group().replaceFirst("<BODY", "<DIV").replaceAll("</BODY>", "</DIV>");  
        }  
  
        // 调整图片地址,这里将图片路径改为网络路径  
        htmlStr = htmlStr.replaceAll("<IMG SRC=\"", "<IMG SRC=\"" + docImgPath + "/"); 
        /*htmlStr = htmlStr.replaceAll("<IMG SRC=\"../","<IMG SRC=\""+MessageUtil.webUrl+"/******.do?action=***");  
        //特殊处理一下+号，因为网络传输+会变成空格，用%2B替换+号  
        String temp1=htmlStr.substring(htmlStr.indexOf("action=***"), htmlStr.length());  
        String temp2=temp1.substring(0,temp1.indexOf("."));  
        String temp3=temp2.replaceAll("\\+", "%2B");  
        htmlStr=htmlStr.substring(0,htmlStr.indexOf("action=***"))+temp3+temp1.substring(temp1.indexOf("."), temp1.length());*/
          
        // 把<P></P>转换成</div></div>保留样式  
        // content = content.replaceAll("(<P)([^>]*>.*?)(<\\/P>)",  
        // "<div$2</div>");  
        // 把<P></P>转换成</div></div>并删除样式  
        htmlStr = htmlStr.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");  
        // 删除不需要的标签  
        htmlStr = htmlStr.replaceAll("<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>","");  
        // 删除不需要的属性  
        htmlStr = htmlStr.replaceAll("<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>","<$1$2>");  
  
        return htmlStr;  
  
    }  
}
