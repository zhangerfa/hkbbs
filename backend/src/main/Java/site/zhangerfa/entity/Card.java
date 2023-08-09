package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import site.zhangerfa.controller.in.BaseCard;
import site.zhangerfa.controller.in.CardIn;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装卡片数据")
public class Card extends BaseCard {
    @TableId
    int id;
    @Schema(description = "发布时间")
    Date createTime;
    @Schema(description = "发布者id")
    private String posterId;
    @Schema(description = "图片url集合")
    @TableField(exist = false)
    private List<String> imageUrls;

    public Card() {
    }

    public Card(CardIn cardIn) {
        super(cardIn);
    }
}
