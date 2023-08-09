package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import site.zhangerfa.controller.in.ManagerIn;

@Data
@Schema(name = "管理员")
public class Manager {
    @TableId
    @Schema(description = "管理员学号")
    @Pattern(regexp = "[UMD][0-9]{9}", message = "学号第一位为学历缩写，后九位为数字")
    private String stuId;
    @Schema(description = "管理员等级")
    private int level;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String headerUrl;

    public Manager(ManagerIn managerIn) {
        this.stuId = managerIn.getStuId();
        this.level = managerIn.getLevel();
    }
}
