package site.zhangerfa.controller.in;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "封装一条聊天消息")
public class MessageIn {
    @JsonIgnore
    @Schema(description = "消息发送者学号")
    String posterId;
    @TableField(exist = false)
    @Schema(description = "消息接收者学号")
    String receiverId;
    @JsonIgnore
    @Schema(description = "消息类型：0-文字消息, 1-图片消息")
    private int type;
    @Schema(description = "消息内容，当消息为图片消息时存储图片url")
    String content;

    public MessageIn() {
    }

    public MessageIn(String posterId, String receiverId, int type, String content) {
        this.posterId = posterId;
        this.receiverId = receiverId;
        this.type = type;
        this.content = content;
    }
}
