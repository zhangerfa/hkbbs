package site.zhangerfa.service.impl;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isExist(String stuId) {
        User user = userMapper.selectByStuId(stuId);
        return user != null;
    }

    @Override
    public boolean checkPassword(String stuId, String password) {
        User user = userMapper.selectByStuId(stuId);
        return user != null ? user.getPassword().equals(password) : false;
    }

    @Override
    public Map<String, String> getUsernameAndCreateTimeByStuId(String stuId) {
        Map<String, String> map = new HashMap<>();
        User user = userMapper.selectByStuId(stuId);
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
        User user = userMapper.selectByStuId(stuId);
        return user != null? user.getUsername(): null;
    }

    @Override
    public Date getCreateTimeByStuId(String stuId) {
        User user = userMapper.selectByStuId(stuId);
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
}
