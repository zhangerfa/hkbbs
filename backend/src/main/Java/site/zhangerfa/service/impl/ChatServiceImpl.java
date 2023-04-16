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
import site.zhangerfa.util.PageUtil;

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
    public Chat selectOnePageMessagesForChat(String stuId, String chatToStuId, int currentPage, int pageSize) {
        int chatId = chatMapper.selectId(stuId, chatToStuId);
        // 分页信息
        PageUtil pageUtil = new PageUtil(currentPage, pageSize,
                messageMapper.selectMessagesNumForChat(chatId));
        Page page = pageUtil.generatePage();
        int[] fromTo = pageUtil.getFromTo();
        List<Message> messages = messageMapper.selectOnePageMessagesForChat(stuId, chatId,
                fromTo[0], fromTo[1]);
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
    public List<Chat> selectLatestMessages(String stuId, int currentPage, int pageSize) {
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
        PageUtil pageUtil = new PageUtil(currentPage, pageSize, chatMapper.selectChatNum(stuId));
        int[] fromTo = pageUtil.getFromTo();
        return chats.subList(Math.min(fromTo[0], chats.size()),
                      Math.min(fromTo[1], chats.size()));
    }
}
