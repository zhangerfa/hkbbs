package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.ChatMapper;
import site.zhangerfa.dao.MessageMapper;
import site.zhangerfa.pojo.Chat;
import site.zhangerfa.pojo.Message;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.ChatService;
import site.zhangerfa.service.UserService;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private UserService userService;

    @Override
    public int getUnreadMessagesNum(String stuId, String chatToStuId) {
        int chatId = chatMapper.selectId(stuId, chatToStuId);
        return messageMapper.selectUnreadMessageNum(stuId, chatId);
    }

    @Override
    public Chat selectOnePageMessagesForChat(String stuId, String chatToStuId, Page page) {
        Chat chat = new Chat();
        // 查询一页消息
        int chatId = chatMapper.selectId(stuId, chatToStuId);
        page.completePage(messageMapper.selectMessagesNumForChat(chatId));
        List<Message> messages = messageMapper.selectOnePageMessagesForChat(stuId, chatId,
                page.getOffset(), page.getLimit());
        chat.setMessages(messages);
        if (messages.size() == 0) return null;
        // 补充聊天对象信息
        List<String> stuIds = chatMapper.selectStuIdsById(chatId);
        for (String userId : stuIds) {
            chat.getUsers().put(userId, userService.getUserByStuId(userId));
        }
        // 封装分页信息
        chat.setPage(page);
        return chat;
    }

    @Override
    public List<Chat> selectLatestMessages(String stuId, Page page) {
        // 获取所有聊天（只包含最新消息）
        page.completePage(chatMapper.selectChatNum(stuId));
        List<Message> messages = messageMapper.selectOnePageChatsForUser(stuId,
                page.getOffset(), page.getLimit());
        // 将聊天对象信息和消息信息分装为chat
        ArrayList<Chat> chats = new ArrayList<>();
        for (Message message : messages) {
            List<String> stuIds = chatMapper.selectStuIdsById(messageMapper.selectChatIdById(message.getId()));
            Chat chat = new Chat();
            for (String userId : stuIds) {
                chat.getUsers().put(userId, userService.getUserByStuId(userId));
            }
            chat.setMessages(List.of(message));
            chats.add(chat);
        }
        return chats;
    }
}
