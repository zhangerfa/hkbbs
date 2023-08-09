package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.controller.in.BaseCard;
import site.zhangerfa.controller.in.BasePost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Schema(description = "帖子")
@Data
public class Post extends BasePost {
    @TableId
    private int id;
    @Schema(description = "发帖人学号")
    private String posterId;
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

    public Post(){}

    public Post(String title, String content){
        super(title, content);
    }
}
