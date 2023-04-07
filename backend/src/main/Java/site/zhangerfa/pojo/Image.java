package site.zhangerfa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

public class Image {
    private int id;
    @JsonIgnore
    @Schema(title = "所属实体类型")
    private int entityType;
    @JsonIgnore
    @Schema(title = "所属实体ID")
    private int entityId;
    @Schema(description = "图片URL")
    private String url;

    public Image(int entityType, int entityId, String url){
        this.entityId = entityId;
        this.entityType = entityType;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
