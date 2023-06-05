package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import site.zhangerfa.Constant.RedisConstant;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;
import site.zhangerfa.util.MailClient;
import site.zhangerfa.util.RedisKeyUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MailClient mailClient;
    @Resource
    private LoginTicketService loginTicketService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


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
        // 计算登录凭证到期时间
        long expired;
        if (rememberMe){
            // 记住密码有效时间设为两个月
            expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 60;
        }else {
            // 默认状态有效时间设为一天
            expired = System.currentTimeMillis() + 1000L * 60 * 60 * 24;
        }
        if (loginTicket == null){
            // 首次登录添加登录验证
            loginTicket = new LoginTicket();
            loginTicket.setStuId(user.getStuId());
            // 生成登录凭证码
            String ticket = UUID.randomUUID().toString().replace("-", "");
            loginTicket.setTicket(ticket);
            loginTicket.setStatus(1); // 设置登录凭证状态为有效
            loginTicket.setExpired(new Date(expired));
            // 保存登录凭证码
            loginTicketService.add(loginTicket);
        }else {
            // 非首次登录，将登录验证状态改为有效，并更新过期时间
            loginTicketService.updateStatus(user.getStuId(), 1);
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
        User user = userMapper.selectById(stuId);
        return user != null;
    }

    @Override
    public boolean checkPassword(String stuId, String password) {
        User user = userMapper.selectById(stuId);
        if (user == null) return false;
        String md5Password = DigestUtils.md5DigestAsHex((password + user.getSalt()).getBytes());
        return md5Password.equals(user.getPassword());
    }

    @Override
    public boolean add(User user) {
        // 生成6位随机数作为salt
        String salt = UUID.randomUUID().toString().substring(0, 6);
        user.setSalt(salt);
        // 用户密码 + salt经过md5加密作为存储密码
        String password = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
        user.setPassword(password);
        return userMapper.insert(user) == 1;
    }

    @Override
    public boolean deleteByStuId(String stuId) {
        int deleteNum = userMapper.deleteById(stuId); // 删除的行数
        return deleteNum != 0;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) return false;
        // 用户密码不为空时，更新密码
        if (user.getPassword() != null){
            // 用户密码 + salt经过md5加密作为存储密码
            String Md5Password = DigestUtils.md5DigestAsHex((user.getPassword() +
                    userMapper.selectById(user.getStuId())).getBytes());
            user.setPassword(Md5Password);
        }
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean checkCode(String code, String stuId){
        // 从Redis中获取验证码
        String sendCode = stringRedisTemplate.opsForValue().get(
                RedisKeyUtil.getRegisterCodeKey(stuId));
        if (sendCode != null) return sendCode.equals(code);
        return false;
    }

    @Override
    public Integer getUserCount() {
        return userMapper.selectCount(null).intValue();
    }

    @Override
    public Result<List<User>> getUserList(int currentPage, int pageSize) {
        // 分页查询
        Page<User> userPage = userMapper.selectPage(new Page<>(currentPage, pageSize), null);
        return new Result<>(Code.GET_OK, userPage.getRecords(), "查询成功");
    }

    @Override
    public String getGenderRatio() {
        Long femaleNum = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getGender, 1));
        Long maleNum = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getGender, 0));
        double maleRadio = (double) maleNum / (femaleNum + maleNum);
        return "男女比例：" + (int)maleRadio * 10 + ":" + (10 - (int)maleRadio * 10);
    }

    @Override
    public boolean sendCode(String stuId) {
        String subject = "验证码请查收：";
        String code = UUID.randomUUID().toString().substring(0, 6);
        // 将验证码存入Redis中
        stringRedisTemplate.opsForValue().set(
                RedisKeyUtil.getRegisterCodeKey(stuId), code,
                RedisConstant.LOGIN_CODE_EXPIRE_MINUTE, TimeUnit.MINUTES);
        // 发送邮件
        return mailClient.send(stuId + "@hust.edu.cn", subject, code);
    }

    @Override
    public User getUserByStuId(String stuId) {
        return userMapper.selectById(stuId);
    }
}
