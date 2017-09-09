package com.mcu32.firstq.wechat.mail.bean;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.mcu32.firstq.wechat.util.ToolUtil;

/**
 * 
 * <p>Title:邮件发送线程类 </p>
 * 
 * <p>Description: </p>
 * 
 * <p>Company: 创智信科</p>
 * 
 * @author long
 * @version 1.0
 */
public class SendMail  implements Runnable{
	private String[] to;// 收件人

	private String[] cc;// 抄送

	private String[] bcc;// 密送

	private String from = "";// 发件人
	
	private String fromZhName = "第一象限";// 发件人中文名
	
	private String systemName = "";//运维系统全名

	private String smtpServer = "";// smtp服务器

	private String username = "";
	
	

	private String password = "";
	
	private String port = "25";

	private String filename = "";// 附件文件名

	private String subject = "";// 邮件主题

	private String content = "";// 邮件正文
	
	private String url = "";// 登陆地址
	private String startUp ="false";//是否启用
	
	private String codeUrl;


	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	private Vector<String> file = new Vector<String>();// 附件文件集合
	

	
	/**
	 * 构造方法
	 * 
	 * @param to
	 *            收件人
	 * @param from
	 *            发件人
	 * @param smtpServer
	 *            smtp服务器
	 * @param username
	 *            发件人
	 * @param password
	 *            发件人密码
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件正文
	 */


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 把邮件主题转换为中文
	 * 
	 * @param strText
	 *            邮件主题
	 * @return 邮件主题
	 */

	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	/**
	 * 增加附件
	 * 
	 * @param fname
	 *            附件名
	 */
	public void attachfile(String fname) {
		file.addElement(fname);
	}


	

	public void setTo(String[] to) {
		this.to = to;
	}

	

	public Vector<String> getFile() {
		return file;
	}

	public void setFile(Vector<String> file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public String getSubject() {
		return subject;
	}

	public String[] getTo() {
		return to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}
	
	/**
	 * 发送
	 * 
	 * @return 是否成功
	 */
	@Override
	public void run() {
		
        	System.out.println("启动发送邮件线程。。。"+Thread.currentThread().getName());
        	//		构造mail session
        	Properties props = System.getProperties();
        	props.put("mail.smtp.host", smtpServer);
        	props.put("mail.smtp.auth", "true");
        	props.put("mail.smtp.port", port);
        	
        	if("false".equals(startUp)){
        		System.out.println("要发送邮件请修改配置中的startUp为true。。。。。。。。");
        		return;
        	}
        	
        	
        	Session session = Session.getDefaultInstance(props, new Authenticator() {
        		public PasswordAuthentication getPasswordAuthentication() {
        			return new PasswordAuthentication(username, password);
        		}
        	});
        	try {
        		// 构造MimeMessage 并设定基本的值
        		MimeMessage msg = new MimeMessage(session);
        		
        		
        		
        		try {
        			String zhName=javax.mail.internet.MimeUtility.encodeText(fromZhName);
        			msg.setFrom(new InternetAddress(zhName+" <"+from+">"));  
        		} catch (UnsupportedEncodingException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        			msg.setFrom(new InternetAddress(from)); 
        		}  
        		
        		
        		// 收件人
        		if (to != null && to.length > 0) {
        			InternetAddress[] address = new InternetAddress[to.length];
        			for (int i = 0; i < to.length; i++) {
        				address[i] = new InternetAddress(to[i]);
        				
        			}
        			msg.setRecipients(Message.RecipientType.TO, address);
        		}
        		
        		//subject = transferChinese(subject);
        		msg.setSubject(javax.mail.internet.MimeUtility.encodeText(subject));
        		// 构造Multipart
        		Multipart mp = new MimeMultipart();
        		// 向Multipart添加正文
        		MimeBodyPart mbpContent = new MimeBodyPart();
        		mbpContent.setContent(content, "text/html; charset=utf-8");
        		//mbpContent.setText(content);
        		// 向MimeMessage添加（Multipart代表正文）
        		mp.addBodyPart(mbpContent);
        		
        		if (codeUrl != null && !"".equals(codeUrl)) {
        			//2、图片  
        			MimeBodyPart body_pic = new MimeBodyPart();  
        			DataHandler picDataHandler = new DataHandler(new FileDataSource(new File(codeUrl)));  
        			body_pic.setDataHandler(picDataHandler);  
        			body_pic.setContentID("<action>");//和html链接的cid一致
        			mp.addBodyPart(body_pic);  
        			((MimeMultipart) mp).setSubType("related");
        		}
        		
        		// 向Multipart添加附件
        		Enumeration efile = file.elements();
        		while (efile.hasMoreElements()) {
        			MimeBodyPart mbpFile = new MimeBodyPart();
        			filename = efile.nextElement().toString();
        			System.out.println(filename);
        			// mbpFile.setFileName(MimeUtility.encodeWord(filename));
        			FileDataSource fds = new FileDataSource(filename);
        			mbpFile.setDataHandler(new DataHandler(fds));
        			mbpFile.setFileName(MimeUtility.encodeText(fds.getName()));
        			// mbpFile.setFileName(fds.getName());
        			// 向MimeMessage添加（Multipart代表附件）
        			mp.addBodyPart(mbpFile);
        		}
        		file.removeAllElements();
        		// 向Multipart添加MimeMessage
        		msg.setContent(mp);
        		msg.setSentDate(new Date());
        		// 发送邮件
        		Transport.send(msg);
        		System.out.println(Thread.currentThread().getName()+"--->发送成功！");
        	} catch (MessagingException mex) {
        		mex.printStackTrace();
        		
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
	
	}
	public static void main(String[] args) {
		SendMail sendmail = new SendMail();
		
		
		sendmail.setSmtpServer("smtp.exmail.qq.com");
		sendmail.setUsername("rjpublic@mcu32.com");
		sendmail.setPassword("czxk2015");
		sendmail.setFrom("rjpublic@mcu32.com");
		sendmail.setPort("25");
		sendmail.setStartUp("true");
		String[] to ={"348170115@qq.com"};
		sendmail.setTo(to);
		sendmail.setSubject("测试");
		sendmail.setContent("测试1");
		sendmail.setContent("<div>" +
				"<img <img alt='' src='${ctx }/assets/i/code.png'>"+
				"</div>");
//		sendmail.attachfile("D:/attachment/基站开关电源参数列表-汇总.xlsx");
//		sendmail.attachfile("D:/attachment/第一象限-01-软件需求规格说明书-V4.0.1.doc");
		sendmail.run();
		
		
	
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getStartUp() {
		return startUp;
	}

	public void setStartUp(String startUp) {
		this.startUp = startUp;
	}

	

	public String getFromZhName() {
		return fromZhName;
	}

	public void setFromZhName(String fromZhName) {
		this.fromZhName = fromZhName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
}
