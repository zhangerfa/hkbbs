package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Card;

public class CardContainStuId extends Card {
    @Schema(title = "发布者学号")
    String posterId;

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }
}
