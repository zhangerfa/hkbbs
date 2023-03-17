package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.pojo.Post;

import java.util.List;

/**
 * 帖子的数据层接口
 */
@Mapper
public interface PostMapper {

    /**
     * 查询一页卡片，当传入学号不为0时，学号为合法学号，查询该学号用户所发的一页卡片
     *            当传入学号为0时，查询一页卡片
     * 一页为10张卡片
     * @param posterId 0 或 合法学号
     * @param offset 要查询页的起始行
     * @param limit 要查询页的末位行
     * @return 一页卡片对象的 list
     */
    List<Card> selectOnePageCards(String posterId, int offset, int limit);

    /**
     * 查询一页树洞，当传入学号不为0时，学号为合法学号，查询该学号用户所发的一页树洞
     *            当传入学号为0时，查询一页树洞
     * 一页为10张卡片
     * @param posterId 0 或 合法学号
     * @param offset 要查询页的起始行
     * @param limit 要查询页的末位行
     * @return 一页树洞对象的 list
     */
    List<Hole> selectOnePageHoles(String posterId, int offset, int limit);

    /**
     * 获取卡片总数
     * @return
     */
    int getNumOfCards();

    /**
     * 获取树洞总数
     * @return
     */
    int getNumOfHoles();

    /**
     * 新增卡片
     *
     * @param post@return
     */
    int addCard(Post post);

    /**
     * 新增树洞
     *
     * @param post@return
     */
    int addHole(Post post);

    Post selectPostById(int id);

    int deletePostById(int id);

    int commentNumPlusOne(int id);

    int commentNumMinusOne(int id);
}

