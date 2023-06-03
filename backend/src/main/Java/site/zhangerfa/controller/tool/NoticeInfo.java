package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.pojo.User;

@Data
@Schema(description = "谁对一个帖子中的实体进行了什么动作" +
        "actionUser.username + actionType + \"了您的\" + entityType + \"(\" +\n" +
        "        entityContent + \"):\" + actionContent" +
        "举例：东九小韭菜评论了您的帖子（今晚三国杀）:约呀")
public class NoticeInfo {
    @Schema(description = "通知Id")
    private int id;
    @Schema(description = "动作发起者昵称")
    private String actionUsername;
    @Schema(description = "动作发起者头像")
    private String actionUserHeadUrl;
    @Schema(description = "帖子Id")
    private int postId;
    @Schema(description = "被动作指向实体的类型，如树洞，评论等")
    private String entityType;
    @Schema(description = "被动作指向实体的内容，如树洞、评论的内容")
    private String entityContent;
    @Schema(description = "动作内容，如评论树洞的具体内容")
    private String actionContent;
}
