package site.zhangerfa.service;

import org.springframework.transaction.annotation.Transactional;
import site.zhangerfa.pojo.User;

import java.util.Date;
import java.util.Map;

@Transactional
public interface UserService {

    /**
     * 判断用户是否已经注册
     *
     * @param stuId
     * @return
     */
    public boolean isExist(String stuId);

    /**
     * 检查给定学号的用户密码是否正确
     *
     * @param stuId
     * @param password
     * @return 用户存在且密码匹配返回true
     */
    public boolean checkPassword(String stuId, String password);

    /**
     * 查询指定学号的用户的用户名、注册时间
     *
     * @param stuId 学号
     * @return 以字典形式返回用户名和注册时间
     */
    public Map<String, String> getUsernameAndCreateTimeByStuId(String stuId);

    /**
     * 查询指定学号用户的用户名
     *
     * @param stuId
     * @return 返回用户名，如果传入学号未注册则返回null
     */
    public String getUsernameByStuId(String stuId);

    /**
     * 查询指定学号用户的注册时间
     *
     * @param stuId
     * @return 返回注册时间，如果传入学号未注册则返回null
     */
    public Date getCreateTimeByStuId(String stuId);

    /**
     * 添加用户
     *
     * @param user
     */
    public boolean add(User user);

    /**
     * 删除指定学号的用户
     *
     * @param stuId 学号
     * @return 如果删除成功返回 true ，其他情况都返回false，如学号不正确
     */
    public boolean deleteByStuId(String stuId);

    /**
     * 修改指定学号的用户信息
     *
     * @param stuId
     * @param username 修改后信息封装在user对象中
     * @return 如果传入学号不存在则返回 “用户不存在”，否则返回 “true”
     */
    public boolean updateUsername(String stuId, String username);

    /**
     * 修改指定学号用户的密码
     *
     * @param stuId
     * @param password
     * @return 如果传入学号不存在则返回 “用户不存在”，否则返回 “true”
     */
    public boolean updatePassword(String stuId, String password);
}
