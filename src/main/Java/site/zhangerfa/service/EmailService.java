package site.zhangerfa.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailService {
    private static String sendEmail = "877319363@qq.com";
    public static void sendFileMail(String subject, String content, String stuId) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 设置自己登陆email的服务商提供的host
        senderImpl.setHost("smtp.qq.com");
        // 设置自己登陆邮箱账号
        senderImpl.setUsername(sendEmail);
        // 邮箱密码
        senderImpl.setPassword("ehypoybrqxbebdjf");
        try {
            // 建立HTML邮件消息
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            // true表示开始附件模式.如果邮件不需要附件设置成false即可
            MimeMessageHelper messageHelper = new MimeMessageHelper(
                    mailMessage, false, "utf-8");
            // 设置收信人的email地址
            messageHelper.setTo(stuId + "@hust.edu.cn");
            // 设置寄信人的email地址{与上面登陆的邮件一致}
            messageHelper.setFrom(sendEmail);
            // 设置邮件发送内容的主题
            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText("<html><title>这是一封邮件</title><body>"
                    + content + "</body></html>", false);
            // 发送邮件
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
