package site.zhangerfa.service;

import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    boolean add(Post post, int postType);

    /**
     * 删除卡片，并所有该卡片的评论
     * @param id
     * @return 将删除结果封装为{result : boolean， msg: "提示信息"}
     */
    Map<String, Object> deleteById(int id);

    /**
     * 获取指定类型的帖子总数
     * @param postType 当传入 -1 时获取所有卡片总数，否则获取指定类型卡片总数
     * @param posterId 当传入 "0" 时获取所有用户卡片总数，否则获取指定用户卡片总数
     * @return
     */
    int getTotalNums(int postType, String posterId);

    /**
     * 删除帖子的评论
     * @param commentId
     * @return 返回一个map，result存储是否删除成功，msg存储提示信息
     */
    Map<String, Object> deleteComment(int commentId);

    /**
     * 获取该实体的指定范围的评论
     * @param entityType
     * @param entityId
     * @param page
     * @return
     */
    List<Comment> getComments(int entityType, int entityId, Page page);

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

    /**
     * 获取id为传入id 的帖子的类型
     * @param id
     * @return
     */
    int getPostType(int id);

    /**
     * 查询一页帖子，需要指定帖子类型
     * 学号为 0时获取一页帖子
     * 学号不为 0时获取所有该学号用户发的帖子中的某一页
     * 一页帖子是指 从第 offset 张到第 limit 张
     *
     * @param stuId
     * @param page     封装了 offset和limit
     * @param postType 帖子类型
     * @return
     */
    Result<List<Post>> getOnePagePosts(String stuId, Page page, int postType);
}
