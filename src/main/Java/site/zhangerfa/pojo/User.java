package site.zhangerfa.pojo;

import java.util.Date;

public class User {
    private String stuId; // 学号
    private String username; // 用户名
    private String password; // 密码
    private String headerUrl; // 头像地址
    private Date createTime; // 创建时间

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
