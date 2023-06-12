package site.zhangerfa.service;

import site.zhangerfa.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * 查询回复传入某个对象（由实体类和实体id唯一确定）的所有帖子
     * @param entityType 对象类型
     * @param entityId 对象id
     * @param offset 起始行
     * @param limit 末尾行
     * @return
     */
    List<Comment> getCommentsForEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 查传入对象的总回帖数
     * @param entityType
     * @param entityId
     * @return
     */
    int getNumOfCommentsForEntity(int entityType, int entityId);

    /**
     * 通过评论id获取评论信息
     * @param id
     * @return
     */
    Comment getCommentById(int id);

    /**
     * 增加评论
     * @param comment
     * @return
     */
    boolean addComment(Comment comment);

    /**
     * 删除评论，并删除所有该评论的评论
     * @param
     * @return
     */
    Map<String, Object> deleteComment(int commentId);
}
