package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import site.zhangerfa.dao.HoleNicknameMapper;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;

import java.util.List;
import java.util.UUID;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private HoleService holeService;

    @Resource
    private HoleNicknameService holeNicknameService;
    @Resource
    private HoleNicknameMapper holeNicknameMapper;
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
    }
}
