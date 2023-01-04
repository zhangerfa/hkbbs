package site.zhangerfa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.pojo.LoginTicket;

import java.util.Date;
import java.util.UUID;


@SpringBootTest
class HkbbsApplicationTests {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void addTest(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStuId("M222271503");
        loginTicket.setTicket("sdfsfdf");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicket.setStatus(0);

        int num = loginTicketMapper.insertTicket(loginTicket);
        System.out.println(loginTicket.getId());
        System.out.println(num);
    }

    @Test
    public void selectTest(){
        LoginTicket ticket = loginTicketMapper.selectByTicket("sdfsfdf");
        System.out.println(ticket.getStatus());
        loginTicketMapper.updateStatus("sdfsfdf", 1);
        ticket = loginTicketMapper.selectByTicket("sdfsfdf");
        System.out.println(ticket.getStatus());
    }

    @Test
    public void testUpdateExpired(){
        LoginTicket ticket = loginTicketMapper.selectByTicket("sdfsfdf");
        System.out.println(ticket.getExpired());
        long expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30;
        loginTicketMapper.updateExpired("sdfsfdf", new Date(expired));
        System.out.println(ticket.getExpired());
    }

    @Test
    public void test(){
        System.out.println(UUID.randomUUID().toString().replace("-", "").length());
    }
}
