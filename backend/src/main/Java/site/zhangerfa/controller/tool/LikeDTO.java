package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LikeDTO {
    @Schema(description = "点赞数量")
    private int likeCount;
    @Schema(description = "当前用户对该实体的点赞状态：1-点赞，0-未点赞")
    private int likeStatus;

    public LikeDTO(int likeCount, int likeStatus) {
        this.likeCount = likeCount;
        this.likeStatus = likeStatus;
    }
}
