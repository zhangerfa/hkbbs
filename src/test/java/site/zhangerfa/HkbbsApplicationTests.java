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
        List<User> users = userMapper.selectAllUsers();
        for (User user : users) {
            // 生成6位随机数作为salt
            String salt = UUID.randomUUID().toString().substring(0, 6);
            user.setSalt(salt);
            userMapper.updateSalt(user.getStuId(), salt);
            // 用户密码 + salt经过md5加密作为存储密码
            String password = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
            user.setPassword(password);

            userMapper.updatePassword(user.getStuId(), password);
        }
    }
}
