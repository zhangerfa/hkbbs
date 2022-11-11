package dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import pojo.Card;

public interface CardMapper {

    /*
    查询用户所有发帖
     */
    @Select("select username, u.stu_id, title, content " +
            "from user as u join card as c where u.stu_id = #{stuId};")
    public Card[] selectCardByStuId(String stuId);

    /*
    发帖
     */
    @Insert("insert into card (stu_id, title, content) values(#{posterId}, #{title}, #{content})")
    public void addCard(Card card);

    /*
    查询一页cards:10个
     */
    @Select("select u.stu_id as posterId, username as posterName, title, content " +
            "from card as c join user as u order by c.create_at limit 1, 10")
    public Card[] selectOnePageCards();
}
