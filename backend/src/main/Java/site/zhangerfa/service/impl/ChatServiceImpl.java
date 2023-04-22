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
        if (chatToStuId.equals("0")){
            // 统计所有聊天中的未读消息数量之和
            List<Integer> chatIds = chatMapper.selectChatsForUser(stuId);
            int res = 0; // 未读消息数量
            for (Integer chatId : chatIds) {
                res += messageMapper.selectUnreadMessageNum(getChatToStuId(stuId, chatId), chatId);
            }
            return res;
        }else {
            int chatId = chatMapper.selectChatId(stuId, chatToStuId);
            return messageMapper.selectUnreadMessageNum(chatToStuId, chatId);
        }
    }

    @Override
    public Chat selectOnePageMessagesForChat(String stuId, String chatToStuId, int currentPage, int pageSize) {
        int chatId = chatMapper.selectChatId(stuId, chatToStuId);
        Chat chat = new Chat();
        // 分页信息
        PageUtil pageUtil = new PageUtil(currentPage, pageSize,
                messageMapper.selectMessagesNumForChat(chatId));
        Page page = pageUtil.generatePage();
        chat.setPage(page);
        // 查询一页信息
        int[] fromTo = pageUtil.getFromTo();
        List<Message> messages = messageMapper.selectOnePageMessagesForChat(stuId, chatId,
                fromTo[0], fromTo[1]);
        chat.setMessages(messages);
        // 封装聊天双方的信息
        HashMap<String, User> users = new HashMap<>();
        users.put(stuId, userService.getUserByStuId(stuId));
        users.put(chatToStuId, userService.getUserByStuId(chatToStuId));
        chat.setUsers(users);
        return chat;
    }

    @Override
    public List<Chat> selectLatestMessages(String stuId, int currentPage, int pageSize) {
        // 获取所有聊天（只包含最新消息）
        List<Integer> chatIds = chatMapper.selectChatsForUser(stuId);
        ArrayList<Chat> chats = new ArrayList<>();
        for (Integer chatId : chatIds) {
            Chat chat = new Chat();
            chats.add(chat);
            // 添加最新消息
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(messageMapper.selectLatestMessages(chatId));
            chat.setMessages(messages);
            // 添加聊天用户信息
            Map<String, String> map = chatMapper.selectStuIdsById(chatId);
            Map<String, User> users = new HashMap<>();
            for (String s: new String[]{"user1", "user2"}){
                String chatStuId = map.get(s);
                users.put(chatStuId, userService.getUserByStuId(chatStuId));
            }
            chat.setUsers(users);
        }
        // 聊天按最新消息的发布时间排序
        chats.sort(Comparator.comparing((Chat c) -> c.getMessages().get(0).getCreateTime()));
        // 只取指定范围内的聊天
        PageUtil pageUtil = new PageUtil(currentPage, pageSize, chatMapper.selectChatNum(stuId));
        int[] fromTo = pageUtil.getFromTo();
        return chats.subList(Math.min(fromTo[0], chats.size()),
                      Math.min(fromTo[1], chats.size()));
    }

    @Override
    public boolean addMessage(String fromId, String toId, int type, String content) {
        // 查询两人聊天id
        Integer chatId = chatMapper.selectChatId(fromId, toId);
        if (chatId == null){
            chatMapper.insertChat(fromId, toId);
            chatId = chatMapper.selectChatId(fromId, toId);
        }
        // 发布新消息
        return messageMapper.insertMessage(fromId, chatId, type, content) > 0;
    }

    @Override
    public boolean readMessages(List<Integer> messageIds) {
        for (Integer messageId : messageIds) {
            messageMapper.readMessage(messageId);
        }
        return true;
    }

    /**
     * 查询当前会话中另一方的学号
     * @param stuId
     * @param chatId
     * @return
     */
    private String getChatToStuId(String stuId, int chatId){
        Map<String, String> map = chatMapper.selectStuIdsById(chatId);
        for (String id : map.values()) {
            if (!id.equals(stuId)) return id;
        }
        return null;
    }


}
