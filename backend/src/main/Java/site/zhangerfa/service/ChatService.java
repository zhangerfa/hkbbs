package site.zhangerfa.service;

import site.zhangerfa.controller.tool.ChatInfo;
import site.zhangerfa.pojo.Chat;

import java.util.List;


public interface ChatService {
    /**
     * 查询用户的聊天未读消息数量，
     * @param stuId 要查询用户的学号
     * @param chatToStuId 聊天对象学号，要传入为'0'则查询该用户所有聊天总的未读消息数量
     * @return
     */
    int getUnreadMessagesNum(String stuId, String chatToStuId);

    /**
     * 返回一页聊天数据
     * @param stuId 查询的用户学号
     * @param chatToStuId 聊天对象学号
     * @return
     */
    ChatInfo selectOnePageMessagesForChat(String stuId, String chatToStuId, int currentPage, int pageSize);

    /**
     * 获取用户所有最新的消息
     * @param stuId
     * @return chat中messages字段中只存储一个message，即最新的消息
     */
    List<ChatInfo> selectLatestMessages(String stuId, int currentPage, int pageSize);

    /**
     * 发布消息
     * @param fromId
     * @param toId
     * @param type
     * @param content
     * @return
     */
    boolean addMessage(String fromId, String toId, int type, String content);

    /**
     * 已读消息的id
     * @param messageIds
     * @return
     */
    boolean readMessages(List<Integer> messageIds);

    /**
     * 获取传入用户的所有聊天
     */
    List<Chat> getChatsForUser(String stuId);

    /**
     * 获取传入用户的聊天数量
     * @param stuId
     * @return
     */
    public long getChatNumForUser(String stuId);
}
