package site.zhangerfa.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Schema(description = "帖子")
@Data
public class Post {
    @TableId
    private int id;
    @Schema(description = "发帖人学号")
    private String posterId;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "正文")
    private String content;
    @Schema(description = "发帖时间")
    @TableField("create_at")
    private Date createTime = new Date();
    @Schema(description = "评论数量")
    private int commentNum = 0;
    @Schema(description = "热度")
    private int hot = 0;
    @Schema(description = "帖子中图片URL集合")
    @TableField(exist = false)
    private List<String> images = new ArrayList<>();
    @Schema(description = "帖子类型")
    @TableField("type")
    private int postType;

    public Post(){}

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }
}
