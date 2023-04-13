package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "封装聊天信息")
public class Chat {
    int id;
    @Schema(description = "聊天双方的信息的哈希表，key为学号")
    Map<String, User> users = new HashMap<>();
    @Schema(description = "聊天中的消息列表")
    List<Message> messages;
    @Schema(description = "封装消息的分页信息")
    Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
