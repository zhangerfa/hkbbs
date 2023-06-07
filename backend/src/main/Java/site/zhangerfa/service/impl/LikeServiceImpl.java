package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import site.zhangerfa.service.LikeService;
import site.zhangerfa.util.RedisUtil;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void like(String stuId, int entityType, int entityId) {
        // 判断该用户是否已经点赞
        String userLikedSetKey = RedisUtil.getUserLikedSetKey(entityType, entityId);
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
    public int getLikedCount(int entityType, int entityId) {
        String userLikedSetKey = RedisUtil.getUserLikedSetKey(entityType, entityId);
        return stringRedisTemplate.opsForSet().size(userLikedSetKey).intValue();
    }

    @Override
    public int getLikedStatus(String stuId, int entityType, int entityId) {
        // 判断该用户是否已经点赞
        String userLikedSetKey = RedisUtil.getUserLikedSetKey(entityType, entityId);
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(userLikedSetKey, stuId);
        return isMember != null && isMember ? 1 : 0;
    }
}
