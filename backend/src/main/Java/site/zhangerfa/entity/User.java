package site.zhangerfa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.controller.in.RegistUser;

import java.util.Date;

@Schema(description = "用户信息")
public class User extends RegistUser {
    @Schema(description = "头像地址")
    private String headerUrl = "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
    @TableField("create_at")
    private Date createTime; // 创建时间
    @JsonIgnore
    private String salt;

    public User(){}

    public User(String stuId, String username, String password, int gender){
        super(stuId, username, password, gender);
    }

    public User (RegistUser registUser){
        super(registUser.getStuId(), registUser.getUsername(), registUser.getPassword(), registUser.getGender());
    }

    public User(String stuId, String username, String password){
        super(stuId, username, password);
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

    public Date getCreateTime() {
        return createTime;
    }
}
