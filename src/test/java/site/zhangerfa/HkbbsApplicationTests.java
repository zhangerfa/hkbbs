package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CardService;

import java.util.UUID;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private CardService cardService;

    @Test
    public void test() {
    }
}
