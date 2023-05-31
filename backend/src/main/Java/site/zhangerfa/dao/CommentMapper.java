package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Comment;

/**
 * 贴在卡片上的卡片（comment）的数据层接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
