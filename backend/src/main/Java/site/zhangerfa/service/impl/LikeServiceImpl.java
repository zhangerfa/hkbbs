package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.service.LikeService;
import site.zhangerfa.util.EntityUtil;
import site.zhangerfa.util.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private EntityUtil entityUtil;

    @Override
    public void like(Entity entity, String stuId) {
        // 判断该用户是否已经点赞
        String userLikedSetKey = RedisUtil.getLikeUserSetKey(entity);
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(userLikedSetKey, stuId);
        if (isMember != null && isMember) {
            // 如果已经点赞，则取消点赞
            stringRedisTemplate.opsForSet().remove(userLikedSetKey, stuId);
        }else {
            // 如果没有点赞，则点赞
            stringRedisTemplate.opsForSet().add(userLikedSetKey, stuId);
        }
    }

    @Override
    public void likeWithStatus(Entity entity, String stuId, int status) {
        // 点赞则将用户学号加入到点赞集合中，点踩则将用户学号从点踩集合中
        String userSetKey;
        if (status == 1) userSetKey = RedisUtil.getLikeUserSetKey(entity);
        else userSetKey = RedisUtil.getUnLikeUserSetKey(entity);
        stringRedisTemplate.opsForSet().add(userSetKey, stuId);
    }

    @Override
    public int getLikeCount(Entity entity) {
        String postLikeUserSetKey = RedisUtil.getLikeUserSetKey(entity);
        return stringRedisTemplate.opsForSet().size(postLikeUserSetKey).intValue();
    }

    @Override
    public int getLikeStatus(String stuId, Entity entity) {
        // 判断该用户是否已经点赞
        String userLikedSetKey = RedisUtil.getLikeUserSetKey(entity);
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(userLikedSetKey, stuId);
        return isMember != null && isMember ? 1 : 0;
    }

    @Override
    public List<UserVo> getLikeUsers(Entity entity) {
        String likeUserSetKey = RedisUtil.getLikeUserSetKey(entity);
        // 获取点赞用户的id集合
        Set<String> stuIds = stringRedisTemplate.opsForSet().members(likeUserSetKey);
        // 根据id查询用户信息
        if (stuIds == null) return null;
        List<UserVo> res = new ArrayList<>(stuIds.size());
        for (String stuId : stuIds) {
            res.add(entityUtil.getUserVo(entity.getEntityType(), stuId));
        }
        return res;
    }
}
