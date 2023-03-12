package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.MailClient;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private MailClient mailClient;
    @Resource
    private LoginTicketService loginTicketService;


    @Override
    public Map<String, Object> login(User user, boolean rememberMe) {
        Map<String, Object> res = new HashMap<>();
        if (!checkPassword(user.getStuId(), user.getPassword())){
            res.put("result", false);
            res.put("msg", "密码错误");
            return res;
        }
        // 密码正确，查询是否有登录凭证
        LoginTicket loginTicket = loginTicketService.getLoginTicketByStuId(user.getStuId());
        if (loginTicket == null){
            // 首次登录添加登录验证
            loginTicket = new LoginTicket();
            loginTicket.setStuId(user.getStuId());
            // 生成登录凭证码
            String ticket = UUID.randomUUID().toString().replace("-", "");
            loginTicket.setTicket(ticket);
            loginTicket.setStatus(1); // 设置登录凭证状态为有效
            long expired; // 登录凭证到期时间
            if (rememberMe){
                // 记住密码有效时间设为两个月
                expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 60;
            }else {
                // 默认状态有效时间设为一天
                expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24;
            }
            loginTicket.setExpired(new Date(expired));
            // 保存登录凭证码
            loginTicketService.add(loginTicket);
        }else {
            // 非首次登录，将登录验证状态改为有效，并更新过期时间
            loginTicketService.updateStatus(user.getStuId(), 1);
            long expired; // 登录凭证到期时间
            if (rememberMe){
                // 记住密码有效时间设为两个月
                expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 60;
            }else {
                // 默认状态有效时间设为一天
                expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24;
            }
            loginTicket.setExpired(new Date(expired));
            loginTicketService.updateExpired(user.getStuId(), new Date(expired));
        }

        res.put("result", true);
        res.put("msg", "登录成功");
        res.put("ticket", loginTicket);
        return res;
    }

    @Override
    public boolean isExist(String stuId) {
        User user = userMapper.selectUserByStuId(stuId);
        return user != null;
    }

    @Override
    public boolean checkPassword(String stuId, String password) {
        User user = userMapper.selectUserByStuId(stuId);
        if (user == null) return false;
        String md5Password = DigestUtils.md5DigestAsHex((password + user.getSalt()).getBytes());
        return md5Password.equals(user.getPassword());
    }

    @Override
    public Map<String, String> getUsernameAndCreateTimeByStuId(String stuId) {
        Map<String, String> map = new HashMap<>();
        User user = userMapper.selectUserByStuId(stuId);
        Result result;
        if (user != null){
            map.put("username", user.getUsername());
            map.put("createTime", user.getCreateTime().toString());
            map.put("stuId", stuId);
        }
        return map;
    }

    @Override
    public boolean add(User user) {
        // 生成6位随机数作为salt
        String salt = UUID.randomUUID().toString().substring(0, 6);
        user.setSalt(salt);
        // 用户密码 + salt经过md5加密作为存储密码
        String password = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
        user.setPassword(password);
        int addNum = userMapper.add(user);
        return addNum == 1;
    }

    @Override
    public boolean deleteByStuId(String stuId) {
        int deleteNum = userMapper.delete(stuId); // 删除的行数
        return deleteNum != 0;
    }

    @Override
    public boolean updateUsername(String stuId, String username) {
        int updateNum = userMapper.updateUsername(stuId, username); // 更新的行数
        return updateNum != 0;
    }

    @Override
    public boolean updatePassword(String stuId, String password) {
        int updateNum = userMapper.updatePassword(stuId, password);
        return updateNum != 0;
    }

    @Override
    public boolean checkCode(String code, HttpSession session){
        String sendCode = (String) session.getAttribute("code");
        if (sendCode != null) return sendCode.equals(code);
        return false;
    }

    @Override
    public boolean sendCode(String stuId, HttpSession session) {
        String subject = "张二发给您发的验证码";
        String code = UUID.randomUUID().toString().substring(0, 6);
        // 存储用户最后一次获取的验证码
        session.setAttribute("code", code);
        System.out.println(code);
        // 设置thymeleaf参数
        Context context = new Context();
        context.setVariable("code", code);
        // 生成动态HTML
        String content = templateEngine.process("mail/register.html", context);
        // 发送邮件
        return mailClient.send(stuId + "@hust.edu.cn", subject, content);
    }

    @Override
    public User getUserByStuId(String stuId) {
        return userMapper.selectUserByStuId(stuId);
    }

    @Override
    public boolean updateHeaderUrl(String stuId, String headerUrl) {
        int flag = userMapper.updateHeaderUrl(stuId, headerUrl);
        return flag > 0;
    }
}
