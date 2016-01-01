package com.blog.action;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.blog.actionForm.MailForm;
import com.blog.bean.ReaderXML;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MailAction extends ActionSupport{
	private MailForm mailForm;
	public MailForm getMailForm() {
		return mailForm;
	}
	public void setMailForm(MailForm mailForm) {
		this.mailForm = mailForm;
	}
	
	private MimeBodyPart createImageMimeBodyPart(String imageName) throws MessagingException, UnsupportedEncodingException{
		FileDataSource fds=new FileDataSource(ServletActionContext.getServletContext().getRealPath("/image")+"/" + imageName + ".gif");  
		MimeBodyPart mbp=new MimeBodyPart();  
        DataHandler dh=new DataHandler(fds);  
        mbp.setDataHandler(dh);  
        //设置对应的资源文件的唯一标识符，即 MIME 协议对于邮件的结构组织格式中的 Content-ID 头字段；
        mbp.setHeader("Content-ID", imageName);
        mbp.setFileName(MimeUtility.encodeText(fds.getName()));  
        return mbp;
	}
	
	public String sendMail(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String pwd = "hjzgg5211314";//发件人邮箱密码
	    	String mailfrom = "15670637914@163.com"; //网易的邮箱
	    	String wangyiFrom = mailfrom.substring(0, mailfrom.indexOf('@'));//网易邮箱的用户名
	    	String tu = "163.com"; //发件人邮箱的后缀域名
	    	String tto= "2570230521@qq.com"; //接收邮件的邮箱
	    	String ttitle= "有人联系你---来自胡峻峥的个人网站";
	    	
	    	//根据其物理路径，得到XML的模板
	    	String XML_path = ServletActionContext.getServletContext().getRealPath("/mailTemplate")+"/myMailTemplete.xml";  
            String str=new ReaderXML().read(XML_path);  
            Object[] obj=new Object[]{mailForm.getName(), mailForm.getPhone(), mailForm.getE_mail(), mailForm.getContent(), "e_mail", "left", "right", "tw", "fb"};  
            //MessageFormat可以格式化这样的消息，然后将格式化后的字符串插入到模式中的适当位置
            String tcontent = MessageFormat.format(str, obj);
	    	
	    	Properties props=new Properties();
	
	    	props.put("mail.smtp.host","smtp."+tu);//邮箱SMTP服务器地址端口
	
	    	props.put("mail.smtp.auth","true");//这样才能通过验证
	
	    	Session s=Session.getInstance(props);
	
	    	s.setDebug(true);
	
	    	MimeMessage message=new MimeMessage(s);
	
	    	//给消息对象设置发件人/收件人/主题/发信时间
	
	    	InternetAddress from;
		
			from = new InternetAddress(mailfrom);//发件人的qq邮箱
			message.setFrom(from);
			InternetAddress to=new InternetAddress(tto);//收件人的邮箱
			message.setRecipient(Message.RecipientType.TO,to);
			message.setSubject(ttitle);
			message.setSentDate(new Date());
			//给消息对象设置内容
			BodyPart mbp=new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
			mbp.setContent(tcontent,"text/html;charset=gb2312");//给BodyPart对象设置内容和格式/编码方式
			// 用于组合文本和图片，"related"型的MimeMultipart对象  
			Multipart mm=new MimeMultipart("related");//新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
			mm.addBodyPart(mbp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			
			//添加图片
			mm.addBodyPart(createImageMimeBodyPart("e_mail"));
			mm.addBodyPart(createImageMimeBodyPart("left"));
			mm.addBodyPart(createImageMimeBodyPart("right"));
			mm.addBodyPart(createImageMimeBodyPart("tw"));
			mm.addBodyPart(createImageMimeBodyPart("fb"));
			
			message.setContent(mm);//把mm作为消息对象的内容
			message.saveChanges();
			Transport transport=s.getTransport("smtp");
			transport.connect("smtp."+tu, wangyiFrom, pwd); //这里的wangyiFrom为发件人网易账号
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			ActionContext.getContext().getSession().put("operations", "邮件发送成功, 请耐心等待回复!");
		} catch (Exception e) {
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors",  e.toString());
			return "errors";
		} 
		return "sendMail";
	}
}
