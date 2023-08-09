package site.zhangerfa.controller.in;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginUser {
    @TableId
    @Schema(description = "学号") @NotNull
    @Pattern(regexp = "[UMD][0-9]{9}", message = "学号第一位为学历缩写，后九位为数字")
    private String stuId;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{6,16}", message = "密码由6到16位的数字和字母组成")
    private String password; // 密码

    public LoginUser(){}

    public LoginUser(String stuId, String password){
        this.stuId = stuId;
        this.password = password;
    }
}
