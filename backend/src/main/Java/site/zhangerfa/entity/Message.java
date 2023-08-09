package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.controller.in.MessageIn;

import java.util.Date;

@Schema(description = "封装聊天消息")
@Data
public class Message extends MessageIn {
    @TableId
    int id;
    @Schema(description = "所属聊天id")
    private int chatId;
    @Schema(description = "消息发布时间")
    Date createTime;
    @Schema(description = "消息状态：0-未读，1-已读")
    int status;

    public Message() {
    }

    public Message(int chatId, MessageIn messageIn) {
        super(messageIn.getPosterId(), messageIn.getReceiverId(),
                messageIn.getType(), messageIn.getContent());
        this.chatId = chatId;
    }

}
