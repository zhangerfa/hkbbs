package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * "包含通知对象和通知内容,
 *  通知内容为哪个用户对一个实体做了什么动作：要将信息-A对实体B做了动作C-通知给用户D"
 */
public class Notice {
    private int id;
    @Schema(description = "被通知用户的学号（D）")
    @JsonIgnore
    private String receivingUserId;
    @Schema(description = "做出动作（触发通知C）的用户的学号")
    @JsonIgnore
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public void setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(String receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public String getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(String actionUserId) {
        this.actionUserId = actionUserId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
