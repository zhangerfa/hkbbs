package site.zhangerfa.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Schema(description = "评论")
@Data
public class Comment {
    @TableId(type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private int id;
    @Schema(description = "被评论实体的类型")
    @NotBlank
    private int entityType;
    @Schema(description = "被评论实体的ID")
    @NotBlank
    private int entityId;
    @Schema(description = "评论内容")
    @NotBlank
    private String content;
    @Schema(description = "发布评论人的学号")
    private String posterId;
    @Schema(description = "评论所属实体的类型")
    private int ownerType;
    @Schema(description = "评论时间")
    private Date createTime = new Date();

    public Comment(){}

    public Comment(int entityType, int entityId, String content) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.content = content;
    }
}
