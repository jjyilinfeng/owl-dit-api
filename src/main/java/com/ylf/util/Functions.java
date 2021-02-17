package com.ylf.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Master 2021/2/8
 */
public class Functions {
    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getNowTime(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getNowTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getUuid(){
        return UUID.randomUUID().toString();
    }

    public static void sendEmail(String checkCode, String email){
        try {
            Properties prop = new Properties();
            // 开启debug调试，以便在控制台查看
            prop.setProperty("mail.debug", "false");
            // 设置邮件服务器主机名
            prop.setProperty("mail.host", "smtp.qq.com");
            // 发送服务器需要身份验证
            prop.setProperty("mail.smtp.auth", "true");
            // 发送邮件协议名称
            prop.setProperty("mail.transport.protocol", "smtp");
            // 开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            // 创建session
            Session session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com
            ts.connect("smtp.qq.com", "774153247", "dhzwgrftmvlzbegf");
            // 创建邮件
            Message message = createSimpleMail(session, checkCode, email);
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static MimeMessage createSimpleMail(Session session, String checkCode, String email){
        try {
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 指明邮件的发件人
            message.setFrom(new InternetAddress("774153247@qq.com"));
            // 指明邮件的收件人，发件人和收件人如果是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 邮件的标题
            message.setSubject("验证码");
            // 邮件的文本内容
            message.setContent("欢迎您使用OwlDit,本次的验证码为:"+checkCode+",请勿回复此邮箱", "text/html;charset=UTF-8");
            // 返回创建好的邮件对象
            return message;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
