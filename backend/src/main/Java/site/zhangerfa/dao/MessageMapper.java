package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import site.zhangerfa.pojo.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    /**
     * 查询用户的聊天未读消息数量，
     * @param stuId 要查询用户的学号
     * @param chatId 聊天id，如果传入0则查询所有聊天总的未读消息数量
     * @return
     */
    int selectUnreadMessageNum(String stuId, int chatId);

    /**
     * 返回聊天的一页私信
     * @param stuId 查询的用户学号
     * @param chatId 聊天id
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectOnePageMessagesForChat(String stuId, int chatId, int offset, int limit);

    /**
     * 查询该聊天总的聊天消息数
     * @param chatId
     * @return
     */
    int selectMessagesNumForChat(int chatId);

    /**
     * 查询用户一页聊天，聊天是以最新发送的信息的时间来排序
     * @param stuId
     * @return
     */
    @Select("select * from message where id in" +
            "(select max(id) from message group by chat_id) " +
            "order by create_time limit #{offset}, #{limit}")
    List<Message> selectOnePageChatsForUser(String stuId, int offset, int limit);

    @Select("select * from message where id = #{id}}")
    Message selectMessageById(int id);

    /**
     * 查询消息所属的聊天id
     * @param id
     * @return
     */
    @Select("select chat_id from message where id = #{id}}")
    int selectChatIdById(int id);
}
