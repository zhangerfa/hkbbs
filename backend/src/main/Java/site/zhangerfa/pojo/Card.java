package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

@Schema(description = "封装卡片响应数据，包含发布者的数据")
public class Card {
    int id;
    @Schema(description = "关于我：自我介绍")
    @NotBlank
    private String aboutMe;
    @Schema(description = "期望的TA:描述期望中的理想征友对象")
    @NotBlank
    private String expected;
    @Schema(description = "发布时间")
    Date createTime;
    @Schema(description = "图片url集合")
    private List<String> imageUrls;
    @Schema(description = "交友目标")
    private int goal;

    public Card(String aboutMe, String expect, int goal) {
        this(aboutMe, expect);
        this.goal = goal;
    }

    public Card(String aboutMe, String expected) {
        this.aboutMe = aboutMe;
        this.expected = expected;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
