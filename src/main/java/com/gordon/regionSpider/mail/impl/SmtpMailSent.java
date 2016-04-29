package com.gordon.regionSpider.mail.impl;

import com.gordon.regionSpider.constants.CrawlerParams;
import com.gordon.regionSpider.mail.MailSent;
import com.gordon.regionSpider.util.TimeUtil;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by wwz on 2016/3/3.
 */
public class SmtpMailSent implements MailSent {
    private static final Logger log = Logger.getLogger(SmtpMailSent.class.getName());
    private static final Properties prop = new Properties();

    public void send(String msg) throws MessagingException {
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", CrawlerParams.SMTP_ADDRESS);
        prop.put("mail.user", CrawlerParams.OUTBOX);
        prop.put("mail.password", CrawlerParams.PASSWORD);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.user"), prop.getProperty("mail.password"));
            }
        };
        //创建邮件会话
        Session mailSession = Session.getInstance(prop, authenticator);

        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);

        //设置发件人
        InternetAddress from = new InternetAddress(prop.getProperty("mail.user"));

        message.setFrom(from);

        //设置收件人
        InternetAddress to = new InternetAddress(CrawlerParams.MAIl_ADDRESS);
        message.setRecipient(MimeMessage.RecipientType.TO, to);

        //设置邮件标题
        message.setSubject(TimeUtil.getTimeStamp() + "更新的折扣商品信息");

        //设置邮件内容
        message.setContent(msg, "text/html;charset=UTF-8");

        //发送
        Transport.send(message);

        System.out.println("send to " + CrawlerParams.MAIl_ADDRESS);
    }
}
