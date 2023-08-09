package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import site.zhangerfa.entity.Entity;

@Schema(description = "评论信息")
@Data
public class CommentIn {
    @NotBlank
    @Schema(description = "被评论实体")
    private Entity entity;
    @NotBlank
    @Schema(description = "评论内容")
    private String content;
    @NotBlank
    @Schema(description = "被评论实体所属帖子或树洞的id")
    private int postId;
}
