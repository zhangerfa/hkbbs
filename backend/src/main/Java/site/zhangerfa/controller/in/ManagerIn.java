package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "新建管理员信息")
public class ManagerIn {
    @Schema(description = "学号") @NotNull
    @Pattern(regexp = "[UMD][0-9]{9}", message = "学号第一位为学历缩写，后九位为数字")
    private String stuId;
    @Schema(description = "管理员级别")
    private int level = 0;
}
