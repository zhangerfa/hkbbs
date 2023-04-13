package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "封装发布帖子、树洞的数据")
public class InPost {
    @Schema(description = "标题")
    @NotBlank
    private String title;
    @Schema(description = "内容")
    @NotBlank
    private String content;
    @Schema(description = "图片集合，可选")
    private List<MultipartFile> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
