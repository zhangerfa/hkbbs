package site.zhangerfa.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * "包含通知对象和通知内容,
 *  通知内容为哪个用户对一个实体做了什么动作：要将信息-A对实体B做了动作C-通知给用户D"
 */
@Data
public class Notice {
    @TableId
    private int id;
    @Schema(description = "被通知用户的学号（D）")
    private String receivingUserId;
    @Schema(description = "做出动作（触发通知C）的用户的学号")
    private String actionUserId;
    @Schema(description = "动作指向实体的类型（B的类型）")
    private int entityType;
    @Schema(description = "动作指向实体的id（B的ID）")
    private int entityId;
    private int entityOwnerId;
    @Schema(description = "动作类型（C的类型）")
    private int actionType;
    @Schema(description = "当动作类型为评论时，该字段存储评论内容id")
    private int commentId;
    @Schema(description = "通知状态：0-未读，1-已读，2-删除")
    private int status = 0;
}
