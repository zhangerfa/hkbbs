package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "封装聊天消息")
public class Message {
    int id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
