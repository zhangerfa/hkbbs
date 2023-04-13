package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.Constant.Goal;
import site.zhangerfa.pojo.BaseCard;

import java.util.List;

public class InCard extends BaseCard {
    @Schema(description = "图片文件集合，至少发布一张图片")
    @NotBlank
    private List<MultipartFile> images;
    @Schema(description = "交友目标")
    @NotBlank
    private Goal goal;

    public InCard(String aboutMe, String expect) {
        super(aboutMe, expect);
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
