package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.pojo.Message;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "封装聊天信息")
public class ChatInfo {
    int id;
    @Schema(description = "聊天双方的信息的哈希表，key为学号")
    Map<String, User> users = new HashMap<>();
    @Schema(description = "聊天中的消息列表")
    List<Message> messages;
    @Schema(description = "封装消息的分页信息")
    Page page;
}
