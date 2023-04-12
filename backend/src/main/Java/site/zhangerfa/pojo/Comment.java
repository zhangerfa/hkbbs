package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "评论")
public class Comment {
    @JsonIgnore
    private int id;
    @Schema(description = "发布评论人的学号")
    private String posterId;
    @Schema(description = "被评论实体的类型")
    private int entityType;
    @Schema(description = "被评论实体的ID")
    private int entityId;
    @Schema(description = "评论内容")
    private String content;
    @Schema(description = "评论时间")
    private Date createTime = new Date();

    public Comment(int entityType, int entityId, String content) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", posterId='" + posterId + '\'' +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
