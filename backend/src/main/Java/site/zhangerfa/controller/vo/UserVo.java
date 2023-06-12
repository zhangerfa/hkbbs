package site.zhangerfa.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVo {
    @Schema(description = "用户昵称，如果为匿名贴则为匿名")
    private String username;
    @Schema(description = "用户头像url，如果为匿名贴则为匿名头像")
    private String headerUrl;

    public UserVo(String username, String headerUrl) {
        this.username = username;
        this.headerUrl = headerUrl;
    }
}
