package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("chat")
@Schema(description = "封装聊天双方学者信息")
public class Chat {
    @TableId
    private int id;
    private String user1;
    private String user2;

    public Chat(String fromId, String toId) {
        this.user1 = fromId;
        this.user2 = toId;
    }
}
