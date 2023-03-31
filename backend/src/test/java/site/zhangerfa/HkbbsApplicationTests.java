package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.util.Constant;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PostMapper postMapper;

    @Test
    public void test() {
        Post post = new Post();
        post.setTitle("111");
        post.setContent("222");
        postMapper.add(post, Constant.ENTITY_TYPE_POST);
        System.out.println(postMapper.getPostType(post.getId()));
    }
}
