package site.zhangerfa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import site.zhangerfa.util.MailClient;


@SpringBootTest
class HkbbsApplicationTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendMailTest(){
        Context context = new Context();
        // 设置thymeleaf参数
        context.setVariable("username", "张二发");
        context.setVariable("code", "3vd546");
        // 生成动态HTML
        String content = templateEngine.process("mail/forget.html", context);
        mailClient.send("M202271503@hust.edu.cn", "测试", content);
    }
}
