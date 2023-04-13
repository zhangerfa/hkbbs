package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.controller.in.InCard;
import site.zhangerfa.pojo.Card;

public class CardContainStuId extends Card {
    @Schema(title = "发布者学号")
    String posterId;

    public CardContainStuId(String aboutMe, String expect){
        super(aboutMe, expect);
    }

    public CardContainStuId(String aboutMe, String expect, int goal){
        super(aboutMe, expect, goal);
    }

    public CardContainStuId(String posterId, InCard inCard){
        this(inCard.getAboutMe(), inCard.getAboutMe(), inCard.getGoal().getCode());
        this.posterId = posterId;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }
}
