package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.entity.Card;
import site.zhangerfa.entity.Manager;
import site.zhangerfa.entity.Post;
import site.zhangerfa.entity.User;

import java.util.List;
import java.util.Map;

public interface ManagerService {
    /**
     * 添加管理员
     * @param manager
     * @return
     */
    Result<Boolean> addManager(Manager manager);

    /**
     * 删除管理员
     * @param stuId
     * @return
     */
    Result<Boolean> deleteManager(String stuId);

    /**
     * 获取管理员列表
     * @return
     */
    List<Manager> getManagerList();

    /**
     * 获取访问量
     * @return
     */
    Integer getPv();

    /**
     * 获取用户数量
     * @return
     */
    Long getUserNum();

    /**
     * 获取帖子数量
     * @return
     */
    Long getPostNum();

    /**
     * 获取男女比例
     * @return
     */
    Double getSexRatio();

    /**
     * 获取卡片列表
     * @return
     */
    Map<String, List<Card>> getCardList();

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();

    /**
     * 获取树洞数量
     * @return
     */
    int getHoleNum();

    /**
     * 获取树洞列表
     * @return
     */
    List<Post> getHoleList();

    /**
     * 获取各类型卡片数量
     * @return
     */
    Map<String, Integer> getCardNum();
}
