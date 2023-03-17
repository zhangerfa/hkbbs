package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import site.zhangerfa.dao.PostMapper;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PostMapper postMapper;

    @Test
    public void test() {

    }
}
