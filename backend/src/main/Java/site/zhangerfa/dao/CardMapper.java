package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.entity.Card;

@Mapper
public interface CardMapper extends BaseMapper<Card> {
}
