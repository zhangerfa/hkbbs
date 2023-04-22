package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChatMapper {
    /**
     * 查询用户总的聊天数量
     * @param stuId
     * @return
     */
    @Select("select count(*) from chat where user1 = #{stuId} or user2 = #{stuId}")
    int selectChatNum(String stuId);

    /**
     * 查询用户的所有聊天id
     *
     * @param stuId
     * @return
     */
    @Select("select id from chat where user1 = #{stuId} or user2 = #{stuId}")
    List<Integer> selectChatsForUser(String stuId);

    /**
     * 获取聊天双方的学号
     * @param chatId
     * @return
     */
    @Select("select user1, user2 from chat where id = #{chatId}")
    Map<String, String> selectStuIdsById(int chatId);

    /**
     * 返回聊天id，如果两个人未聊天则返回null
     * @param fromId
     * @param toId
     * @return
     */
    @Select("select id from chat where " +
            "(user1 = #{fromId} and user2 = #{toId}) " +
            "or (user1 = #{toId} and user2 = #{fromId})")
    Integer selectChatId(String fromId, String toId);

    /**
     * 新建两个人的聊天
     * @param fromId
     * @param toId
     * @return
     */
    @Insert("insert into chat(user1, user2) values(#{fromId}, #{toId})")
    int insertChat(String fromId, String toId);
}
