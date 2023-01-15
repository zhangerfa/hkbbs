package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Hole;

import java.util.List;

@Mapper
public interface HoleMapper {
    /**
     * 查询一页树洞，当传入学号不为0时，学号为合法学号，查询该学号用户所发的一页树洞
     *            当传入学号为0时，查询一页树洞
     * 一页为10张卡片
     * @param posterId 0 或 合法学号
     * @param offset 要查询页的起始行
     * @param limit 要查询页的末位行
     * @return 一页卡片对象的 list
     */
    List<Hole> selectOnePageHoles(String posterId, int offset, int limit);

    int getNumOfHoles();

    /**
     * 新增树洞
     * @param hole
     * @return
     */
    int addHole(Hole hole);

    Hole selectHoleById(int id);

    int commentNumPlusOne(int id);

    int commentNumMinusOne(int id);

    int deleteCardById(int id);
}
