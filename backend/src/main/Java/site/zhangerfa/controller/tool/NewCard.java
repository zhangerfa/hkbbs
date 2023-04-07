package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "封装新发布卡片的数据")
public class NewCard {
    @Schema(title = "发布人学号")
    private String posterId;
    @Schema(title = "卡片中图片集合", description = "卡片中的图片集合，至少包含一张图片")
    @NotEmpty
    private List<MultipartFile> images;
    @Schema(title = "关于我", description = "自我介绍")
    private String aboutMe;
    @Schema(title = "交友目标", description = "0-恋爱， 1-电子游戏， 2-桌游， 3-学习, -1-默认")
    private int goal = -1;
    @Schema(description = "期望的TA", defaultValue = "描述期望中的理想征友对象")
    private String expected;

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
