package site.zhangerfa.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.entity.Card;

@Data
public class CardVo extends Card {
    @Schema(title = "发布者信息")
    UserVo poster;
    @Schema(description = "年级")
    String age;

    public CardVo(Card card, UserVo poster, String age) {
        this.setId(card.getId());
        this.setPosterId(card.getPosterId());
        this.setAboutMe(card.getAboutMe());
        this.setGoal(card.getGoal());
        this.setExpected(card.getExpected());
        this.setCreateTime(card.getCreateTime());
        this.poster = poster;
        this.age = age;
    }
}
