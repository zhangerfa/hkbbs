package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;

import java.util.List;
import java.util.Map;

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
    boolean add(User user);

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
     *      如果正确则为第一次登录用户生成登录凭证，为之前登录过的用户更新登录凭证
     * @param rememberMe 是否勾选记住密码
     * @return 如果学号或密码错误返回null，否则返回登录凭证
     */
    LoginTicket login(User user, boolean rememberMe);

    /**
     * 检查用户注册时传入的验证码是否正确
     *
     * @param code
     * @param stuId
     * @return
     */
    boolean checkCode(String code, String stuId);

    Integer getUserCount();

    Result<List<User>> getUserList(int currentPage, int pageSize);

    String getGenderRatio();
}
