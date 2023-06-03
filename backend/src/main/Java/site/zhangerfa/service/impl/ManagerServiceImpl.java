package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.ManagerMapper;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.dao.UserMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Manager;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.ManagerService;
import site.zhangerfa.util.UserUtil;

import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private PostMapper postMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public Result<Boolean> addManager(Manager manager) {
        // 判断学号是否合法
        if (!UserUtil.isStuIdValid(manager.getStuId()))
            return new Result<>(Code.SAVE_ERR, false, "学号不合法");
        // 判断学号是否已经存在
        if (managerMapper.selectById(manager.getStuId()) != null)
            return new Result<>(Code.SAVE_ERR, false, "学号已存在");
        // 添加管理员
        managerMapper.insert(manager);
        return new Result<>(Code.SAVE_OK, true, "添加成功");
    }

    @Override
    public Result<Boolean> deleteManager(String stuId) {
        // 判断传入用户是否为管理员
        if (managerMapper.selectById(stuId) == null)
            return new Result<>(Code.DELETE_ERR, false, "该用户不是管理员");
        // 删除管理员
        managerMapper.deleteById(stuId);
        return new Result<>(Code.DELETE_OK, true, "删除成功");
    }

    @Override
    public List<Manager> getManagerList() {
        List<Manager> managers = managerMapper.selectList(null);
        // 补充昵称和头像
        for (Manager manager : managers) {
            User user = userMapper.selectById(manager.getStuId());
            manager.setUsername(user.getUsername());
            manager.setHeaderUrl(user.getHeaderUrl());
        }
        return managers;
    }

    @Override
    public Integer getPv() {
        return 0;
    }

    @Override
    public Long getUserNum() {
        return userMapper.selectCount(null);
    }

    @Override
    public Long getPostNum() {
        return postMapper.selectCount(null);
    }

    @Override
    public Double getSexRatio() {
        return null;
    }

    @Override
    public Map<String, List<Card>> getCardList() {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public int getHoleNum() {
        return 0;
    }

    @Override
    public List<Post> getHoleList() {
        return null;
    }

    @Override
    public Map<String, Integer> getCardNum() {
        return null;
    }
}
