package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.User;

public class CardContainsPoster extends Card {
    @Schema(title = "发布者信息")
    User poster;

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}
