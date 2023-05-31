package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
