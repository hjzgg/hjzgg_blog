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
        //���ö�Ӧ����Դ�ļ���Ψһ��ʶ������ MIME Э������ʼ��Ľṹ��֯��ʽ�е� Content-ID ͷ�ֶΣ�
        mbp.setHeader("Content-ID", imageName);
        mbp.setFileName(MimeUtility.encodeText(fds.getName()));  
        return mbp;
	}
	
	public String sendMail(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String pwd = "hjzgg5211314";//��������������
	    	String mailfrom = "15670637914@163.com"; //���׵�����
	    	String wangyiFrom = mailfrom.substring(0, mailfrom.indexOf('@'));//����������û���
	    	String tu = "163.com"; //����������ĺ�׺����
	    	String tto= "2570230521@qq.com"; //�����ʼ�������
	    	String ttitle= "������ϵ��---���Ժ���῵ĸ�����վ";
	    	
	    	//����������·�����õ�XML��ģ��
	    	String XML_path = ServletActionContext.getServletContext().getRealPath("/mailTemplate")+"/myMailTemplete.xml";  
            String str=new ReaderXML().read(XML_path);  
            Object[] obj=new Object[]{mailForm.getName(), mailForm.getPhone(), mailForm.getE_mail(), mailForm.getContent(), "e_mail", "left", "right", "tw", "fb"};  
            //MessageFormat���Ը�ʽ����������Ϣ��Ȼ�󽫸�ʽ������ַ������뵽ģʽ�е��ʵ�λ��
            String tcontent = MessageFormat.format(str, obj);
	    	
	    	Properties props=new Properties();
	
	    	props.put("mail.smtp.host","smtp."+tu);//����SMTP��������ַ�˿�
	
	    	props.put("mail.smtp.auth","true");//��������ͨ����֤
	
	    	Session s=Session.getInstance(props);
	
	    	s.setDebug(true);
	
	    	MimeMessage message=new MimeMessage(s);
	
	    	//����Ϣ�������÷�����/�ռ���/����/����ʱ��
	
	    	InternetAddress from;
		
			from = new InternetAddress(mailfrom);//�����˵�qq����
			message.setFrom(from);
			InternetAddress to=new InternetAddress(tto);//�ռ��˵�����
			message.setRecipient(Message.RecipientType.TO,to);
			message.setSubject(ttitle);
			message.setSentDate(new Date());
			//����Ϣ������������
			BodyPart mbp=new MimeBodyPart();//�½�һ������ż����ݵ�BodyPart����
			mbp.setContent(tcontent,"text/html;charset=gb2312");//��BodyPart�����������ݺ͸�ʽ/���뷽ʽ
			// ��������ı���ͼƬ��"related"�͵�MimeMultipart����  
			Multipart mm=new MimeMultipart("related");//�½�һ��MimeMultipart�����������BodyPart����(��ʵ�Ͽ��Դ�Ŷ��)
			mm.addBodyPart(mbp);//��BodyPart���뵽MimeMultipart������(���Լ�����BodyPart)
			
			//���ͼƬ
			mm.addBodyPart(createImageMimeBodyPart("e_mail"));
			mm.addBodyPart(createImageMimeBodyPart("left"));
			mm.addBodyPart(createImageMimeBodyPart("right"));
			mm.addBodyPart(createImageMimeBodyPart("tw"));
			mm.addBodyPart(createImageMimeBodyPart("fb"));
			
			message.setContent(mm);//��mm��Ϊ��Ϣ���������
			message.saveChanges();
			Transport transport=s.getTransport("smtp");
			transport.connect("smtp."+tu, wangyiFrom, pwd); //�����wangyiFromΪ�����������˺�
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			ActionContext.getContext().getSession().put("operations", "�ʼ����ͳɹ�, �����ĵȴ��ظ�!");
		} catch (Exception e) {
			System.out.println(e.toString());
			ActionContext.getContext().getSession().put("errors",  e.toString());
			return "errors";
		} 
		return "sendMail";
	}
}
