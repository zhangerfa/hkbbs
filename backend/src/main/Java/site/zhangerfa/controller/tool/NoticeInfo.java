package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "谁对一个实体进行了什么动作" +
        "actionUsername + actionType + \"了您的\" + entityType + \"(\" +\n" +
        "        entityContent + \"):\" + actionContent" +
        "举例：东九小韭菜评论了您的帖子（今晚三国杀）:约呀")
public class NoticeInfo {
    @Schema(description = "通知Id")
    private int id;
    @Schema(description = "动作发起者")
    private UserDTO actionUser;
    @Schema(description = "被动作指向实体的类型，如树洞，评论等")
    private String entityType;
    @Schema(description = "被动作指向实体的内容，如树洞、评论的内容")
    private String entityContent;
    @Schema(description = "动作内容，如评论树洞的具体内容")
    private String actionContent;
    @Schema(description = "创建时间")
    private String createTime;
}
