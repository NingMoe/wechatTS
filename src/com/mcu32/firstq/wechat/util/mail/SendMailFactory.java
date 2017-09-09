package com.mcu32.firstq.wechat.util.mail;

import java.util.List;

import com.mcu32.firstq.common.util.SpringContextManager;
import com.mcu32.firstq.wechat.mail.bean.SendMail;

/**
 * 
 * <p>Title:邮件发送类 </p>
 * 
 * 
 * @version 1.0
 */
public class SendMailFactory {
	
	/*
	 * 直接发送邮件到用户
	 */
	public static void sendMail(String title,String toEmail,String codeUrl,String[] files){
		SendMail sendmail = (SendMail) SpringContextManager.getCommonContext().getBean("mail");
		//发送的地址
		String[] to ={toEmail};
		// 发送人
		sendmail.setTo(to);
		sendmail.setCodeUrl(codeUrl);
		if (files != null && files.length > 0) {
			for (String file : files) {
				sendmail.attachfile(file);
			}
		}
		// 设置内容

		sendmail.setSubject(title);
		
    	sendmail.setContent("您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用第一象限隐患排查工具对基站隐患进行排查。<br>您此次排查的结果已经生成，附件为结果，请查收。<br>"
    			+ "<br><br><br><br>以下是公众号二维码，关注“第一象限运维”公众号，可直接在web平台查看排查报表，更有众多实用功能等你来。<br>"
    			+ "&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"cid:action\">");
    	new Thread(sendmail).start();
	}
	public static void sendMailContent(String content,String title,List<String> to,List<String> cc,List<String> files){
		String[] toArray=new String[to.size()];
		to.toArray(toArray);
		
		String[] ccArray=new String[cc.size()];
		cc.toArray(ccArray);
		
		String[] filesArray=new String[files.size()];
		files.toArray(filesArray);
		sendMailContent(content,title,toArray,ccArray,filesArray);
	}
	
	public static void sendMailContent(String content,String title,String[] to,String [] cc,String[] files){
		SendMail sendmail = (SendMail) SpringContextManager.getCommonContext().getBean("mail");
		//发送的地址
		sendmail.setTo(to);
		sendmail.setCc(cc);
		//sendmail.setCodeUrl(codeUrl);
		if (files != null && files.length > 0) {
			for (String file : files) {
				sendmail.attachfile(file);
			}
		}
		// 设置内容
		sendmail.setSubject(title);
		sendmail.setContent(content);
    	new Thread(sendmail).start();
	}
	/*
	 * 抄送邮件到用户
	 */
	/*public void copyMail(Object[] objs,DataObject commonMain,Map commonSheet){

		Map  map =  getIdsUtil(objs);

		if(map!=null && map.size()>0){
			
			List infoList = getSendInfoList(map);
			
			if(infoList!=null && infoList.size()>0){
				java.text.DateFormat format0 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String createdates = format0.format(new Date());
		        List<Map> emailList = new ArrayList<Map>();
			
			System.out.println("启动抄送邮件线程。。。");
			for(int i =0;i<infoList.size();i++){
				
				SendMail sendmail = (SendMail)cxt.getBean("sendMail");
				Map<String,String> emailMap = new HashMap<String,String>();
				
				Map infoMap = (Map)infoList.get(i);
				
				String name = (String)infoMap.get("name");
				
				String email = (String)infoMap.get("email");
				
				String id = (String)infoMap.get("id");
				//发送的地址
				String[] to ={email};
				sendmail.setTo(to);

				// 发送人
				
				// 设置标题
				sendmail.setSubject("请阅知“" + title + "”的工单");
				
				java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		        String time = format1.format(new Date());

		        
				// 设置内容
		        sendmail.setContent("<div><p>"+name+"：</p>\n" +
						"<p style='text-indent:21.0pt;'>您好！ 工单号为“"+sheetId+"”，主题为“"+title+"”的工单需要您阅知。</p>\n" +
						"<p style='text-indent:21.0pt;'>详情请登陆"+sendmail.getUrl()+"<p>\n" +
						"<p style='margin-left:210.0pt;text-indent:21.0pt;'>（本邮件由系统自动发出，请勿回复）</p>\n" +
						"<p style='text-indent:21.0pt'>&nbsp;</p>\n<p style='text-indent:21.0pt'>&nbsp;</p>\n<p style='text-indent:21.0pt'>&nbsp;</p>\n" +
						"<p style='margin-left:210.0pt;text-indent:21.0pt'>"+sendmail.getSystemName()+"</p>\n" +
						"<p style='margin-left:280.0pt;text-indent:21.0pt;'>"+time +"</p></div>");
				//sendmail.setContent("尊敬的 "+name+" 先生/女士\n \t\t您好，工单号为\""+sheetId+"\",主题为\""+title+"\"的工单现抄送给您，请关注！\n \t\t详情请登录"+sendmail.getUrl()+"\n\n\n\n\n\n\t\t信息中心\n\t\t"+time);

				sendmail.start();	
				emailMap.put("tomail",email);
				emailMap.put("text", name+":\n \t\t\n\t\t您好！ \n \t\t工单号为\""+sheetId+"\",主题为\""+title+"\"的工单需要您阅知。\n \t\t详情请登陆"+sendmail.getUrl()+"\n \t\t（本邮件由系统自动发出，请勿回复）\n\n\n\n\n\n\t\t"+sendmail.getSystemName()+"\n\t\t"+time);
				emailMap.put("id", id);
				emailList.add(emailMap);
			}
			OperationLogsUtil.addOperationLogs(dealEmailOperationLogs(emailList,createdates,commonMain,commonSheet));
		
			
		}
	 }
	}*/
	
}
