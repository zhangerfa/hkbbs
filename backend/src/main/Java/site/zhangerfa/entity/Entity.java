package site.zhangerfa.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "实体")
public class Entity {
    @Schema(description = "实体类型：1-帖子，2-树洞，3-评论，4-卡片")
    private int entityType;
    @Schema(description = "实体id")
    private int entityId;

    public Entity() {
    }

    public Entity(int entityType, int entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
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
