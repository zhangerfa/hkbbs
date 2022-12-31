import config.SpringConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import service.UserService;

@RunWith(SpringJUnit4ClassRunner.class) // 为Junit绑定Spring提供的测试器
@ContextConfiguration(classes = SpringConfig.class) // 加载Spring容器
public class UserServiceTest {
    private UserService userService;

    @Test
    public void isExistTest(){
        String stuId = "M202271503";
        boolean exist = userService.isExist(stuId);
        Assert.assertEquals("判断用户是否注册测试失败", true, exist);
    }

    @Test
    public void checkPasswordTest(){

    }

    @Test
    public void getByStuIdTest(){

    }

    @Test
    public void addTest(){

    }

    @Test
    public void deleteByStuIdTest(){

    }

    @Test
    public void updateTest(){

    }
}
