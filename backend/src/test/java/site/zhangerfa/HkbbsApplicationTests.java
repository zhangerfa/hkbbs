package site.zhangerfa;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.util.NoticeUtil;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private NoticeService noticeService;

    @Test
    public void test(){
        noticeService.getUnreadNoticesForUser("M202271503", -1, 1, 10).forEach(System.out::println);
    }
}
