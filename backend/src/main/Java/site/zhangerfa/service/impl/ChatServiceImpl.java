package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.ChatMapper;
import site.zhangerfa.dao.MessageMapper;
import site.zhangerfa.pojo.Chat;
import site.zhangerfa.pojo.Message;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;
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
        int chatId = chatMapper.selectId(stuId, chatToStuId);
        page.completePage(messageMapper.selectMessagesNumForChat(chatId));
        List<Message> messages = messageMapper.selectOnePageMessagesForChat(stuId, chatId,
                page.getOffset(), page.getLimit());
        List<String> stuIds = chatMapper.selectStuIdsById(chatId);
        Chat chat = new Chat();
        chat.setMessages(messages);
        HashMap<String, User> users = new HashMap<>();
        for (String id : stuIds) {
            users.put(id, userService.getUserByStuId(id));
        }
        chat.setUsers(users);
        chat.setPage(page);
        return chat;
    }

    @Override
    public List<Chat> selectLatestMessages(String stuId, Page page) {
        // 获取所有聊天（只包含最新消息）
        List<Integer> chatIds = chatMapper.selectChatsForUser(stuId);
        ArrayList<Chat> chats = new ArrayList<>();
        for (Integer chatId : chatIds) {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(messageMapper.selectLatestMessages(chatId));
            Chat chat = new Chat();
            chat.setMessages(messages);
            chats.add(chat);
        }
        // 聊天按最新消息的发布时间排序
        chats.sort(Comparator.comparing((Chat c) -> c.getMessages().get(0).getCreateTime()));
        // 只取指定范围内的聊天
        page.completePage(chatMapper.selectChatNum(stuId));
        return chats.subList(Math.min(page.getOffset(), chats.size()),
                      Math.min(page.getLimit(), chats.size()));
    }
}
