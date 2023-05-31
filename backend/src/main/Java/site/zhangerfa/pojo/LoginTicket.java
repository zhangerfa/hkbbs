package site.zhangerfa.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "登录凭证")
@Data
public class LoginTicket {
    @TableId
    private String stuId;
    @Schema(description = "登录凭证码")
    private String ticket;
    @Schema(description = "登录凭证状态码 0 无效， 1有效")
    private int status;
    @Schema(description = "登录凭证过期时间")
    private Date expired;

    public LoginTicket() {
    }

    public LoginTicket(String stuId, int status) {
        this.stuId = stuId;
        this.status = status;
    }

    public LoginTicket(String stuId, Date expired) {
        this.stuId = stuId;
        this.expired = expired;
    }
}
