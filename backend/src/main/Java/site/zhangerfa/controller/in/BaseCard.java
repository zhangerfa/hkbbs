package site.zhangerfa.controller.in;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class BaseCard {
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

    public BaseCard() {
    }

    public BaseCard(CardIn cardIn){
        this.aboutMe = cardIn.getAboutMe();
        this.expected = cardIn.getExpected();
        this.goal = cardIn.getGoal();
    }
}
