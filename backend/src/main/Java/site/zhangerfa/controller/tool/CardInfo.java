package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.User;

import java.util.Calendar;

public class CardInfo extends Card {
    @Schema(title = "发布者信息")
    User poster;
    @Schema(description = "年级")
    String age;

    public CardInfo(Card card, User poster) {
        this.setId(card.getId());
        this.setPosterId(card.getPosterId());
        this.setAboutMe(card.getAboutMe());
        this.setGoal(card.getGoal());
        this.setExpected(card.getExpected());
        this.setCreateTime(card.getCreateTime());
        this.poster = poster;
    }

    /**
     * 由学号推出年级（如果本科毕业两年称为大六）
     */
    public void getAge(){
        StringBuilder age = new StringBuilder();
        String stuId = poster.getStuId();
        switch (stuId.charAt(0)) {
            case 'U' -> age.append("大");
            case 'M' -> age.append("研");
            case 'D' -> age.append("博");
        }
        // 目前是上学的第几年
        int years = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(stuId.substring(1, 5));
        age.append(years);
        this.age = age.toString();
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}
