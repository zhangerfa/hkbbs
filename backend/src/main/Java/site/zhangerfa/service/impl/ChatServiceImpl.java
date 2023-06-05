package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.ChatInfo;
import site.zhangerfa.dao.ChatMapper;
import site.zhangerfa.dao.MessageMapper;
import site.zhangerfa.service.ChatService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.pojo.Chat;
import site.zhangerfa.pojo.Message;
import site.zhangerfa.pojo.User;
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
        // 获取所有聊天
        List<Chat> chats = getChatsForUser(stuId);
        int res = 0; // 未读消息数量
        for (Chat chat : chats) {
            int chatId = chat.getId();
            // 统计每个聊天中的未读消息数量之和——聊天对象发送的状态为未读的消息数量
            res += messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                    .eq(Message::getChatId, chatId)
                    .eq(Message::getStatus, 0)
                    .eq(!Objects.equals(chatToStuId, "0"),
                            Message::getPosterId, chatToStuId));
        }
        return res;
    }

    @Override
    public ChatInfo selectOnePageMessagesForChat(String stuId, String chatToStuId, int currentPage, int pageSize) {
        int chatId = chatMapper.selectChatId(stuId, chatToStuId);
        // 查询一页消息
        Page<Message> messagePage = messageMapper.selectPage(new Page<>(currentPage, pageSize),
                new LambdaQueryWrapper<Message>()
                        .eq(Message::getChatId, chatId)
                        .orderByDesc(Message::getCreateTime));
        // 封装聊天信息
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setMessages(messagePage.getRecords());
        // 封装分页信息
        chatInfo.setPage(new site.zhangerfa.pojo.Page(messagePage));
        // 封装聊天双方的信息
        HashMap<String, User> users = new HashMap<>();
        users.put(stuId, userService.getUserByStuId(stuId));
        users.put(chatToStuId, userService.getUserByStuId(chatToStuId));
        chatInfo.setUsers(users);
        return chatInfo;
    }

    @Override
    public List<ChatInfo> selectLatestMessages(String stuId, int currentPage, int pageSize) {
        // 获取所有聊天（只包含最新消息）
        List<Chat> chats = getChatsForUser(stuId);
        ArrayList<ChatInfo> chatInfos = new ArrayList<>();
        for (Chat chat: chats) {
            int chatId = chat.getId();
            // 添加聊天信息
            ChatInfo chatInfo = new ChatInfo();
            chatInfos.add(chatInfo);
            // 添加该聊天中的最新消息
            Message message = messageMapper.selectOne(new LambdaQueryWrapper<Message>()
                    .eq(Message::getChatId, chatId)
                    .orderByDesc(Message::getCreateTime).last("limit 1"));
            chatInfo.setMessages(new ArrayList<>(Collections.singletonList(message)));
            // 添加聊天用户信息
            Map<String, User> users = new HashMap<>();
            users.put(chat.getUser1(), userService.getUserByStuId(chat.getUser1()));
            users.put(chat.getUser2(), userService.getUserByStuId(chat.getUser2()));
            chatInfo.setUsers(users);
        }
        // 聊天按最新消息的发布时间排序
        chatInfos.sort(Comparator.comparing((ChatInfo c) -> c.getMessages().get(0).getCreateTime()));
        // 只取指定范围内的聊天
        PageUtil pageUtil = new PageUtil(currentPage, pageSize, (int) getChatNumForUser(stuId));
        int[] fromTo = pageUtil.getFromTo();
        return chatInfos.subList(Math.min(fromTo[0], chatInfos.size()),
                      Math.min(fromTo[1], chatInfos.size()));
    }

    @Override
    public boolean addMessage(String fromId, String toId, int type, String content) {
        // 查询两人聊天id
        Integer chatId = chatMapper.selectChatId(fromId, toId);
        if (chatId == null){
            chatMapper.insert(new Chat(fromId, toId));
            chatId = chatMapper.selectChatId(fromId, toId);
        }
        // 发布新消息
        return messageMapper.insert(new Message(chatId, fromId, type, content)) > 0;
    }

    @Override
    public boolean readMessages(List<Integer> messageIds) {
        for (Integer messageId : messageIds) {
            messageMapper.readMessage(messageId);
        }
        return true;
    }

    @Override
    public List<Chat> getChatsForUser(String stuId) {
        if (stuId == null) return null;
        LambdaQueryWrapper<Chat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chat::getUser1, stuId).or().eq(Chat::getUser2, stuId);
        List<Chat> chats = chatMapper.selectList(wrapper);
        return chats;
    }

    public long getChatNumForUser(String stuId){
        return chatMapper.selectCount(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getUser1, stuId).or().eq(Chat::getUser2, stuId));
    }

    /**
     * 查询当前会话中另一方的学号
     * @param stuId
     * @param chatId
     * @return
     */
    private String getChatToStuId(String stuId, int chatId){
        Chat chat = chatMapper.selectById(chatId);
        if (chat.getUser1().equals(stuId)) return chat.getUser2();
        else return chat.getUser1();
    }
}
