package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "注册用户信息")
public class RegistUser extends LoginUser {
    @NotBlank
    @Schema(description = "用户名")
    private String username;
    @NotBlank
    @Schema(description = "用户性别：0-男，1-女")
    private int gender;

    public RegistUser(){}

    public RegistUser(String stuId, String username, String password, int gender){
        this(stuId, username, password);
        this.gender = gender;
    }

    public RegistUser(String stuId, String username, String password){
        super(stuId, password);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
