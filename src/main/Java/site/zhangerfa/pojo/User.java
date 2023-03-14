package site.zhangerfa.pojo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Schema(description = "用户信息")
public class User {
    @Schema(description = "学号") @NotNull
    @Pattern(regexp = "[UMD][0-9]{9}", message = "学号第一位为学历缩写，后九位为数字")
    private String stuId;
    @NotBlank
    @Schema(description = "用户名")
    private String username;
    @NotBlank @Pattern(regexp = "[a-zA-Z0-9]{6,16}", message = "密码由6到16位的数字和字母组成")
    @JsonIgnore
    private String password; // 密码
    @Schema(description = "头像地址")
    private String headerUrl = "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
    private Date createTime; // 创建时间
    @JsonIgnore
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
