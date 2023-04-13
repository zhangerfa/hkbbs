package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "封装评论的输入数据")
public class InComment {
    @Schema(description = "被评论实体的类型")
    @NotBlank
    private int entityType;
    @Schema(description = "被评论实体的ID")
    @NotBlank
    private int entityId;
    @Schema(description = "评论内容")
    @NotBlank
    private String content;

    public InComment(int entityType, int entityId, String content) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.content = content;
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
}
