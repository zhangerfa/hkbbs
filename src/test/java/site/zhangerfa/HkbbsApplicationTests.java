package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.Constant;

import java.util.List;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private NoticeService noticeService;

    @Test
    public void test() throws InterruptedException {

    }
}
