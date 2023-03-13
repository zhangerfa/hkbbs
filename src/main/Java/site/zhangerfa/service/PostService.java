package site.zhangerfa.service;

import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;

import java.util.List;
import java.util.Map;

public interface PostService {
    /**
     * 补全分页信息，前端传入当前页页码，每页大小，
     *     补全：总帖子数，当前页帖子集合
     *          当前页上的帖子数，总页数，分页页码开始页，分页页码结束页均可由以上数算出
     *          （已在Page的getter方法中实现）
     * @param page
     */
    void completePage(Page page);

    /**
     * 删除指定id的帖子
     * @param id
     * @return
     */
    boolean deleteById(int id);

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
}
