package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import site.zhangerfa.pojo.User;

import java.util.UUID;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        String salt = UUID.randomUUID().toString().substring(0, 6);
        String password = DigestUtils.md5DigestAsHex(("z15147001162" + salt).getBytes());
        System.out.println(salt);
        System.out.println(password);
    }
}
