package site.zhangerfa.controller.in;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCard extends CardIn{
    @Schema(description = "关于我：自我介绍")
    private String aboutMe;
    @Schema(description = "期望的TA:描述期望中的理想征友对象")
    @TableField("expect")
    private String expected;
}
