package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "更新用户信息")
public class UpdateUser {
    @Schema(description = "用户名")
    private String username;

    @Pattern(regexp = "[a-zA-Z0-9]{6,16}", message = "密码由6到16位的数字和字母组成")
    private String password; // 密码

    @Schema(description = "头像图片")
    private MultipartFile headerImage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(MultipartFile headerImage) {
        this.headerImage = headerImage;
    }
}
