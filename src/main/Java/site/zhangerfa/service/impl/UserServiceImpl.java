package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.MailClient;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private Map<String, String> codeMap = new HashMap<>(); // 存储用户最后一次获取的验证码
    @Resource
    private UserMapper userMapper;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private MailClient mailClient;

    @Override
    public boolean isExist(String stuId) {
        User user = userMapper.selectUserByStuId(stuId);
        return user != null;
    }

    @Override
    public boolean checkPassword(String stuId, String password) {
        User user = userMapper.selectUserByStuId(stuId);
        return user != null ? user.getPassword().equals(password) : false;
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
    public String getUsernameByStuId(String stuId) {
        User user = userMapper.selectUserByStuId(stuId);
        return user != null? user.getUsername(): null;
    }

    @Override
    public Date getCreateTimeByStuId(String stuId) {
        User user = userMapper.selectUserByStuId(stuId);
        return user != null? user.getCreateTime(): null;
    }

    @Override
    public boolean add(User user) {
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

    public boolean checkCode(String stuId, String code){
        return codeMap.containsKey(stuId) && code.equals(codeMap.get(stuId));
    }

    @Override
    public boolean sendCode(String stuId) {
        String subject = "张二发给您发的验证码";
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            code += random.nextInt(0, 10);
        }
        // 保存验证码
        codeMap.put(stuId, code);
        System.out.println(code);
        // 设置thymeleaf参数
        Context context = new Context();
        context.setVariable("code", code);
        // 生成动态HTML
        String content = templateEngine.process("mail/register.html", context);
        // 发送邮件
        return mailClient.send(stuId + "@hust.edu.cn", subject, content);
    }
}
