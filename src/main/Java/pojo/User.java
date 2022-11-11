package pojo;

import java.util.Date;

public class User {
    private String stuId; // 学号
    private String username; // 用户名
    private String password; // 密码
    Date createTime; // 创建时间

    @Override
    public String toString() {
        return "User{" +
                "stuId='" + stuId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public User(String stuId, String username, String password){
        this.password = password;
        this.username = username;
        this.stuId = stuId;
    }

    public String getStuId() {
        return stuId;
    }

    public String getPassword() {
        return password;
    }

    // 修改密码
    public void changePassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
