package site.zhangerfa.util;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 用于发送邮件的工具类
 */
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Resource
    private JavaMailSender javaMailSender; // 发邮件的对象

    @Value("${spring.mail.username}")
    private String from; // 发送邮件的邮箱地址

    /**
     * 先指定邮箱发送传入的主题和内容的邮件
     * @param to 收件邮箱地址
     * @param subject
     * @param content
     */
    public boolean send(String to, String subject, String content){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); // 封装会话内容的对象
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            // 设置收发双方及邮件主题和内容
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true); // 发送HTML文本
            // 发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            logger.error("邮件发送失败" + e.getMessage());
            return false;
        }
    }
}
