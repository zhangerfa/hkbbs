package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import site.zhangerfa.service.WebDataService;

import java.util.Objects;

@Service
public class WebDataServiceImpl implements WebDataService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer getPv() {
        return Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("pv")));
    }

    @Override
    public void addPv() {
        // 判断Redis中是否有pv
        if (stringRedisTemplate.opsForValue().get("pv") == null)
            stringRedisTemplate.opsForValue().set("pv", "0");
        // pv+1
        stringRedisTemplate.opsForValue().increment("pv");
    }

    @Override
    public Integer getUv() {
        return stringRedisTemplate.opsForHyperLogLog().size("uv").intValue();
    }

    @Override
    public void addUv(String stuId) {
        stringRedisTemplate.opsForHyperLogLog().add("uv", stuId);
    }
}
