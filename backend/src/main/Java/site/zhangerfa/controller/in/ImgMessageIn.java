package site.zhangerfa.controller.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "封装一条图片聊天消息")
@Data
public class ImgMessageIn extends MessageIn{
    @Schema(description = "图片文件")
    MultipartFile image;

    @JsonIgnore
    private String content;

    public ImgMessageIn() {
    }

    public ImgMessageIn(String posterId, String receiverId, int type, String content, MultipartFile image) {
        super(posterId, receiverId, type, content);
        this.image = image;
    }
}
