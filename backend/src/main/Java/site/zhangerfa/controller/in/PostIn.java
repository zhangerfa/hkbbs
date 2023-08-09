package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostIn extends BasePost{
    @Schema(description = "帖子中图片集合")
    private List<MultipartFile> images;
}
