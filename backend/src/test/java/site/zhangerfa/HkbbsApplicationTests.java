package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("test", "test");
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }
}

