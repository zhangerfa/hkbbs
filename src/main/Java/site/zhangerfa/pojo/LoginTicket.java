package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "登录凭证")
public class LoginTicket {
    private int id;
    private String stuId;
    @Schema(description = "登录凭证码")
    private String ticket;
    @Schema(description = "登录凭证状态码 0 无效， 1有效")
    private int status;
    @Schema(description = "登录凭证过期时间")
    private Date expired;

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
