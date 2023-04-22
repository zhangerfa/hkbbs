package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import site.zhangerfa.controller.tool.CardInfo;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.Constant.Constant;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMapper commentMapper;

    @Test
    public void test() {
        Comment comment = commentMapper.selectCommentById(2);
        System.out.println(comment);
    }
}
