package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Comment;

import java.util.List;

/**
 * 贴在卡片上的卡片（comment）的数据层接口
 */
@Mapper
public interface CommentMapper {
    /**
     * 查询回复传入某个对象（由实体类和实体id唯一确定）的所有帖子
     * @param entityType 被评论对象的类型
     * @param entityId 被评论对象的id
     * @param offset 起始行
     * @param limit 末尾行
     * @return
     */
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 查传入对象的总回帖数
     * @param entityType
     * @param entityId
     * @return
     */
    int getNumOfCommentsForEntity(int entityType, int entityId);

    Comment selectCommentById(int id);
}
