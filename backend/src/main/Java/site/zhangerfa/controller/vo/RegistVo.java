package site.zhangerfa.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import site.zhangerfa.controller.in.RegistUser;

@Schema(description = "注册新用户所需要的信息")
public class RegistVo {
    @NotBlank
    @Schema(description = "用户信息")
    private RegistUser user;
    @NotBlank
    @Schema(description = "验证码")
    private String code;

    public RegistUser getUser() {
        return user;
    }

    public void setUser(RegistUser user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
