package site.zhangerfa.service;

import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    boolean add(Post post);

    /**
     * 删除卡片，并所有该卡片的评论
     * @param id
     * @return 将删除结果封装为{result : boolean， msg: "提示信息"}
     */
    Map<String, Object> deleteById(int id);

    /**
     * 获取帖子总数
     * @return
     */
    int getTotalNums();

    /**
     * 删除帖子的评论
     * @param commentId
     * @return 返回一个map，result存储是否删除成功，msg存储提示信息
     */
    Map<String, Object> deleteComment(int commentId);

    /**
     * 获取该帖子所有评论
     * @param id 帖子id
     * @return
     */
    List<Comment> getComments(int id);

    /**
     * 获取该帖子的指定范围的评论
     * @param id 帖子id
     * @param offset 起始行
     * @param limit 末尾行
     * @return
     */
    List<Comment> getComments(int id, int offset, int limit);

    /**
     * 获取指定 id 的帖子数据
     */
    Post getPostById(int id);


    /**
     * 为帖子增加一条评论
     *
     * @param comment
     * @param postId
     * @return
     */
    boolean addComment(Comment comment, int postId);
}
