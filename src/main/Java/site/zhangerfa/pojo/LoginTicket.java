package site.zhangerfa.pojo;

import java.util.Date;

public class LoginTicket {
    private int id;
    private String stuId;
    private String ticket; // 登录凭证码
    private int status; // 登录凭证状态码 0 无效， 1有效
    private Date expired; // 登录凭证过期时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
