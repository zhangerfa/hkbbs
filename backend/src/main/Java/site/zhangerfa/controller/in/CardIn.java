package site.zhangerfa.controller.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CardIn extends BaseCard{
    @Schema(description = "图片")
    List<MultipartFile> images;
}
