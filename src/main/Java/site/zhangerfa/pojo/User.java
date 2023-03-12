package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Schema(description = "用户信息")
public class User {
    @Schema(description = "学号") @NotNull @Email(regexp = "[UMD][0-9]{9}")
    private String stuId;
    @NotBlank
    private String username; // 用户名
    @NotBlank
    private String password; // 密码
    @Schema(description = "头像地址")
    private String headerUrl = "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
    private Date createTime; // 创建时间
    @Schema(hidden = true)
    private String salt;

    @Override
    public String toString() {
        return "User{" +
                "stuId='" + stuId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public User(){}

    public User(String stuId, String username, String password){
        this.password = password;
        this.username = username;
        this.stuId = stuId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStuId() {
        return stuId;
    }

    public String getPassword() {
        return password;
    }

    // 修改密码
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
