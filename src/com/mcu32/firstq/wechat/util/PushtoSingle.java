package com.mcu32.firstq.wechat.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 给用户推送消息到微信群
 * @author chenyan
 *
 */
public class PushtoSingle {
	
	static String appId = "qyzobxRjsT6x8qrmrq9aS8";
    static String appkey = "3Yqej174UR6mWgiyeV8FS1";
    static String master = "vVe0sdgkuAA5KKhmbJeI79";
    //static String CID = "cdefd624452ef366956e0914af9dbaa0";
    //static String CID = "bcc6d1d58f763240996c7d9d4d4a511d";
   // static String CID = "bcc6d1d58f763240996c7d9d4d4a511d";
    //static String CID="6a39033314a6ddc279716cf163cb2c29"; 陈艳手机
     //测试手机
    static String CID="89cc14276d97a4ca573d0e5e2b18c12c";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
     
    public static void sentMessage(String groupName,String content,String title,String msg,String url,int type) throws Exception {
        IGtPush push = new IGtPush(host, appkey, master);
         
       //LinkTemplate template = linkTemplateDemo();
        TransmissionTemplate template=transmissionTemplateDemo(groupName,content,title,msg,url,type);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
       
        Target target = new Target();
 
        target.setAppId(appId);
        target.setClientId(CID);
        //用户别名推送，cid和用户别名只能2者选其一
        //String alias = "个";
        //target.setAlias(alias);
        IPushResult ret = null;
        try{
            ret = push.pushMessageToSingle(message, target);
        }catch(RequestException e){
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if(ret != null){
            System.out.println(ret.getResponse().toString());
        }else{
            System.out.println("服务器响应异常");
    }
}
    public static LinkTemplate linkTemplateDemo() {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle("请输入通知栏标题");
        template.setText("请输入通知栏内容");
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        return template;
}

    
    public static TransmissionTemplate transmissionTemplateDemo(String groupName,String content,String title,String msg,String url,int type) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        String json="";
        //type=1 文本，tyep=2 是链接
        if(type==1){
        	 json= "{\"groupName\":\""+groupName+"\",\"content\":\""+content+"\",\"link\":{\"title\":\"\",\"msg\":\"\",\"url\":\"\"},\"type\":1}"; 
            
        }else{
        	 json= "{\"groupName\":\""+groupName+"\",\"content\":\"\",\"link\":{\"title\":\""+title+"\",\"msg\":\""+msg+"\",\"url\":\""+url+"\"},\"type\":2}"; 
            
        }
        System.out.println(json);
        template.setTransmissionContent(json);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
}
