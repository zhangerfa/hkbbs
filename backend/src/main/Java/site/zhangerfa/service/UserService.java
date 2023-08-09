package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.controller.in.LoginUser;
import site.zhangerfa.controller.in.RegistUser;
import site.zhangerfa.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 判断用户是否已经注册
     *
     * @param stuId
     * @return
     */
    boolean isExist(String stuId);

    /**
     * 检查给定学号的用户密码是否正确
     *
     * @param stuId
     * @param password
     * @return 用户存在且密码匹配返回true
     */
    boolean checkPassword(String stuId, String password);

    User getUserByStuId(String stuId);

    /**
     * 添加用户
     *
     * @param user
     */
    boolean add(RegistUser user);

    /**
     * 删除指定学号的用户
     *
     * @param stuId 学号
     * @return 如果删除成功返回 true ，其他情况都返回false，如学号不正确
     */
    boolean deleteByStuId(String stuId);

    /**
     * 修改指定学号的用户信息，只修改字段不为null的值
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 给传入学号的教育邮箱发送验证码
     *
     * @param stuId
     */
    boolean sendCode(String stuId);

    /**
     * 检查指定学号的用户的输入密码、图像验证码是否正确
     * @param rememberMe 是否勾选记住密码
     * @return 如果学号或密码错误返回null，否则返回登录凭证码
     */
    String login(LoginUser user, boolean rememberMe);

    /**
     * 检查用户注册时传入的验证码是否正确
     *
     * @param code
     * @param stuId
     * @return
     */
    boolean checkCode(String code, String stuId);

    /**
     * 退出登录
     * @param ticket
     */
    void logout(String ticket);

    /**
     * 获取用户总数
     * @return
     */
    Integer getUserCount();

    /**
     * 获取用户列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    Result<List<User>> getUserList(int currentPage, int pageSize);

    /**
     * 获取用户性别比例
     * @return
     */
    String getGenderRatio();
}
