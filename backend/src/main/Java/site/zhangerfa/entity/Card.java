package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装卡片数据")
public class Card{
    @TableId
    int id;
    @Schema(description = "关于我：自我介绍")
    @NotBlank
    private String aboutMe;
    @Schema(description = "期望的TA:描述期望中的理想征友对象")
    @TableField("expect")
    @NotBlank
    private String expected;
    @NotBlank
    @Schema(description = "交友目标")
    private int goal;
    @Schema(description = "发布时间")
    Date createTime;
    @Schema(description = "发布者id")
    private String posterId;
    @Schema(description = "图片url集合")
    @TableField(exist = false)
    private List<String> imageUrls;

    public Card() {
    }

    public Card(String aboutMe, String expected, int goal, String posterId) {
        this.aboutMe = aboutMe;
        this.expected = expected;
        this.goal = goal;
        this.posterId = posterId;
    }
}
