package site.zhangerfa;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void test(){
        Hole hole = new Hole();
        hole.setPosterId("M202271503");
        hole.setTitle("测试树洞");
        hole.setContent("测试测试");

        holeService.addHole(hole);
        List<Hole> holes = holeService.getOnePageHoles(hole.getPosterId(), 0, Integer.MAX_VALUE);
        for (Hole h : holes) {
            System.out.println(holeNicknameService.getHoleNickname(h.getId(), h.getPosterId()) + ":");
            System.out.println(h);
        }
    }
}
