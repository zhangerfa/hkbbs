package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "树洞贴")
public class Hole extends Post{
    @Schema(description = "树洞中发出该贴的用户的昵称")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
