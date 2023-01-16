package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.zhangerfa.dao.HoleNicknameMapper;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;

import java.util.List;


@SpringBootTest
class HkbbsApplicationTests {
    @Resource
    private HoleService holeService;

    @Resource
    private HoleNicknameService holeNicknameService;
    @Resource
    private HoleNicknameMapper holeNicknameMapper;

    @Test
    public void test(){

    }
}
