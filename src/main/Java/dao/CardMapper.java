package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.Card;

import java.util.List;

@Mapper
public interface CardMapper {

    /*
    查询用户所有发帖
     */
    @Select("select username as postername, u.stu_id, title, content, c.create_at as createTime " +
            "from user u join card c on u.stu_id = c.stu_id " +
            "where u.stu_id = #{stuId} " +
            "order by createTime desc limit 0, 9")
    public List<Card> selectOnePageCardsByStuId(@Param("stuId") String stuId, int page);

    /*
    发帖
     */
    @Insert("insert into card (stu_id, title, content) values(#{posterId}, #{title}, #{content})")
    public int addCard(Card card);

    /*
    查询一页cards:10个
     */
    @Select("select u.stu_id as posterId, username as posterName, title, content," +
            "    c.create_at as createTime" +
            "    from user u join card c on u.stu_id = c.stu_id " +
            "order by createTime desc limit 0, 9")
    public List<Card> selectOnePageCards(int page);
}

