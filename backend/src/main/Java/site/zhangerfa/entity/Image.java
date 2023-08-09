package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Image {
    @TableId
    private int id;
    @JsonIgnore
    @Schema(title = "所属实体类型")
    private int entityType;
    @JsonIgnore
    @Schema(title = "所属实体ID")
    private int entityId;
    @Schema(description = "图片URL")
    private String url;

    public Image(int id, int entityType, int entityId, String url) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.url = url;
    }

    public Image(int id, Entity entity, String url) {
        this.id = id;
        this.entityType = entity.getEntityType();
        this.entityId = entity.getEntityId();
        this.url = url;
    }

    public Image(Entity entity, String url){
        this.entityId = entity.getEntityId();
        this.entityType = entity.getEntityType();
        this.url = url;
    }

    public Image(int id) {
        this.id = id;
    }
}
