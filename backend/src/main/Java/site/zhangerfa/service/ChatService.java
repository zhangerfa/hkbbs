package site.zhangerfa.service;

import site.zhangerfa.pojo.Chat;
import site.zhangerfa.pojo.Page;

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
     * @param page
     * @return 如果双方未发送消息则返回null
     */
    Chat selectOnePageMessagesForChat(String stuId, String chatToStuId, Page page);

    /**
     * 获取用户所有最新的消息
     * @param stuId
     * @return chat中messages字段中只存储一个message，即最新的消息
     */
    List<Chat> selectLatestMessages(String stuId, Page page);
}
