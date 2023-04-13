package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.controller.in.InComment;

import java.util.Date;

@Schema(description = "评论")
public class Comment extends InComment {
    @JsonIgnore
    private int id;
    @Schema(description = "发布评论人的学号")
    private String posterId;
    @Schema(description = "评论时间")
    private Date createTime = new Date();

    public Comment(int entityType, int entityId, String content) {
        super(entityType, entityId, content);
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
