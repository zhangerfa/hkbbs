package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Post;

import java.util.List;

/**
 * 帖子的数据层接口
 */
@Mapper
public interface PostMapper {

    /**
     * 查询一页帖子，当传入学号不为0时，学号为合法学号，查询该学号用户所发的一页卡片
     * 当传入学号为0时，查询一页卡片
     * 一页为10张卡片
     *
     * @param postType 帖子类型
     * @param posterId 0 或 合法学号
     * @param offset   要查询页的起始行
     * @param limit    要查询页的末位行
     * @return 一页卡片对象的 list
     */
    List<Post> selectOnePagePosts(int postType, String posterId, int offset, int limit);

    /**
     * 获取传入类型的卡片总数
     * @return
     */
    int getNumOfPosts(int postType);

    /**
     * 新增帖子
     *
     * @param post     @return
     * @param postType
     */
    int add(Post post, int postType);

    Post selectPostById(int id);

    int deletePostById(int id);

    int commentNumPlusOne(int id);

    int commentNumMinusOne(int id);

    Integer getPostType(int id);
}

