package site.zhangerfa.controller.in;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePost {
    @Schema(description = "标题")
    private String title;
    @Schema(description = "正文")
    private String content;
    @Schema(description = "帖子类型：1-帖子，2-树洞")
    @TableField("type")
    private int postType;

    public BasePost() {
    }

    public BasePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
