package site.zhangerfa.dao;

import org.apache.ibatis.annotations.*;
import site.zhangerfa.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据学号查用户除了密码外的所有信息
     * @param stuId
     * @return
     */
    User selectUserByStuId(String stuId);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int add(User user);

    int updateUsername(String stuId, String username);

    int updatePassword(String stuId, String password);

    int updateHeaderUrl(String stuId, String headerUrl);

    int delete(String stuId);

    List<User> selectAllUsers();

    int updateSalt(String stuId, String salt);
}
