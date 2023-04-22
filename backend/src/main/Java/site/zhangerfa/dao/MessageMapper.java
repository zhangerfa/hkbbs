package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import site.zhangerfa.pojo.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    /**
     * 查询用户的聊天未读消息数量，
     * @param stuId 聊天对象的学号
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
     * 获取用户指定聊天中最新的消息
     * @param chatId 聊天id
     * @return
     */
    Message selectLatestMessages(int chatId);

    /**
     * 查询该聊天总的聊天消息数
     * @param chatId
     * @return
     */
    int selectMessagesNumForChat(int chatId);

    /**
     * 发布消息
     * @param fromId
     * @param chatId
     * @param type
     * @param content
     * @return
     */
    int insertMessage(String fromId, int chatId, int type, String content);

    /**
     * 将消息状态改为已读
     * @param id
     * @return
     */
    @Update("update message set status = 1 where id = #{id}")
    int readMessage(int id);
}
