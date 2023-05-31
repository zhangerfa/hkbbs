package site.zhangerfa;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void test(){
        loginTicketMapper.updateById(new LoginTicket("M202271503", 0));
    }
}
