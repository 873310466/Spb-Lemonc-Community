package com.lemonfish.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/4/5 22:26
 */
@Component
public class MailUtil {
    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 邮件发送者
     */
    public static final String SENDER = "873310466@qq.com";

    /**
     * 发送邮件
     *
     * @param from               发件人
     * @param to                 接收人
     * @param subject            邮件主题
     * @param text               邮件正文
     * @param attachmentFileName 附件名字
     * @param attachment         附件
     * @param html               是否解析html 标签
     * @throws MessagingException
     * @Author lemonfish
     * @Email 873310466@qq.com
     */
    public void sendMail(String from, String to, String subject, String text, String attachmentFileName, File attachment, Boolean html) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // 组转
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);

            // 正文
            helper.setSubject(subject);
            helper.setText(text, html);
            helper.setFrom(from);
            helper.setTo(from);

            // 附件
            if (attachment != null && attachmentFileName != null && attachmentFileName.length() > 0) {
                helper.addAttachment(attachmentFileName, attachment);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        mailSender.send(mimeMessage);
    }


    public void sendMail(String from, String to, String subject, String text, Boolean html)  {
        sendMail(from, to, subject, text, null, null, html);
    }

    /**
     * 发送邮件（自己用）---包括附件
     *
     * @param to
     * @param subject
     * @param text
     * @param attachmentName
     * @param attachment
     * @throws MessagingException
     * @Author lemonfish
     * @Email 873310466@qq.com
     */
    public void sendMail(String to, String subject, String text, String attachmentName, File attachment) {
        sendMail(SENDER, to, subject, text, attachmentName, attachment, true);
    }

    /**
     * 发送邮件（自己用）---不包括附件
     *
     * @param to
     * @param subject
     * @param text
     * @throws MessagingException
     * @Author lemonfish
     * @Email 873310466@qq.com
     */
    public void sendMail(String to, String subject, String text)  {
        sendMail(SENDER, to, subject, text, null, null, true);
    }


}
