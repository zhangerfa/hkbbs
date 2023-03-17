package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.User;

@Schema(name = "通知信息", description = "谁对一个帖子中的实体进行了什么动作" +
        "actionUser.username + actionType + \"了您的\" + entityType + \"(\" +\n" +
        "        entityContent + \"):\" + actionContent" +
        "举例：东九小韭菜评论了您的帖子（今晚三国杀）:约呀")
public class NoticeInfo {
    @Schema(description = "做出动作的用户信息")
    private User actionUser;
    @Schema(description = "帖子Id")
    private int postId;
    @Schema(description = "被动作指向实体的类型，如树洞，评论等")
    private String entityType;
    @Schema(description = "被动作指向实体的内容，如树洞、评论的内容")
    private String entityContent;
    @Schema(description = "动作内容，如评论树洞的具体内容")
    private String actionContent;

    public NoticeInfo(User actionUser){
        this.actionUser = actionUser;
    }

    public User getActionUser() {
        return actionUser;
    }

    public void setActionUser(User actionUser) {
        this.actionUser = actionUser;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityContent() {
        return entityContent;
    }

    public void setEntityContent(String entityContent) {
        this.entityContent = entityContent;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }
}
