package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import site.zhangerfa.pojo.Message;

import java.util.List;

@Mapper
public interface ChatMapper {
    /**
     * 获取聊天id
     * @param stuId
     * @param chatToStuId
     * @return
     */
    @Select("select id from chat where (user1 = #{stuId} and user2 = #{chatToStuId}) " +
            "                        or (user2 = #{stuId} and user1 = #{chatToStuId});")
    int selectId(String stuId, String chatToStuId);

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
    List<Integer> selectAllChatsForUser(String stuId);

    /**
     * 获取聊天双方的学号
     * @param chatId
     * @return
     */
    @Select("select user1, user2 from chat where id = #{chatId}}")
    List<String> selectStuIdsById(int chatId);
}
