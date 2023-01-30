package site.zhangerfa.pojo;

/**
 * 包含通知对象和通知内容
 * 通知内容为哪个用户对一个实体做了什么动作
 */
public class Notice {
    private int id;
    private String receivingUserId; // 被通知用户的学号
    // 通知内容为哪个用户对一个实体做了什么动作
    private String actionUserId; // 做出动作的用户的学号
    private int entityType; // 动作指向实体的类型
    private int entityId; // 动作指向实体的id
    private int entityOwnerId; // 实体发布者id，如帖子的发布者
    private int actionType; // 动作类型
    private int commentId; // 评论内容id
    private int status = 0; // 通知状态：0-未读，1-已读，2-删除

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
