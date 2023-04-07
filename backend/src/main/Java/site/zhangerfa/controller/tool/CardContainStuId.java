package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Card;

public class CardContainStuId extends Card {
    public CardContainStuId(String aboutMe, String expect){
        super(aboutMe, expect);
    }

    public CardContainStuId(NewCard newCard){
        super();
        super.setAboutMe(newCard.getAboutMe());
        super.setGoal(newCard.getGoal());
        super.setExpected(newCard.getExpected());
        posterId = newCard.getPosterId();
    }
    @Schema(title = "发布者学号")
    String posterId;

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }
}
