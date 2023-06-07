package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import site.zhangerfa.service.WebDataService;

@Service
public class WebDataServiceImpl implements WebDataService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int getPv() {
        String pv = stringRedisTemplate.opsForValue().get("pv");
        if (pv == null){
            stringRedisTemplate.opsForValue().set("pv", "0");
            return 0;
        }
        return Integer.parseInt(pv);
    }

    @Override
    public void addPvForToday() {
        stringRedisTemplate.opsForValue().increment("pv");
    }
}
