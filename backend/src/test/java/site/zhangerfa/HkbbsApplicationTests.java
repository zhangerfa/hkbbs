package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.Constant.Constant;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CardMapper cardMapper;

    @Test
    public void test() {
        Card card = new Card("111", "111", 1);
        cardMapper.add("M202271503", card);
    }
}
