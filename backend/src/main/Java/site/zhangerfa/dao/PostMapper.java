package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import site.zhangerfa.entity.Post;

/**
 * 帖子的数据层接口
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Update("update post set comment_num = comment_num + 1 where id = #{id}")
    int commentNumPlusOne(int id);

    @Update("update post set comment_num = comment_num - 1 where id = #{id}")
    int commentNumMinusOne(int id);

    @Select("select type from post where id = #{id}")
    Integer getPostType(int id);
}

