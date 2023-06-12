package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "封装聊天消息")
@Data
public class Message {
    @TableId
    int id;
    @Schema(description = "聊天id")
    private int chatId;
    @Schema(description = "消息发送者学号")
    String posterId;
    @Schema(description = "消息类型：0-文字消息, 1-图片消息")
    private int type;
    @Schema(description = "消息内容，当消息为图片消息时存储图片url")
    String content;
    @Schema(description = "消息发布时间")
    Date createTime;
    @Schema(description = "消息状态：0-未读，1-已读")
    int status;

    public Message() {
    }

    public Message(int chatId, String posterId, int type, String content) {
        this.chatId = chatId;
        this.posterId = posterId;
        this.type = type;
        this.content = content;
    }

}
