package site.zhangerfa.controller.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "封装发送文本消息的数据")
public class InMessage {
    @JsonIgnore
    @Schema(description = "消息发送者的学号")
    private String fromStuId;
    @Schema(description = "消息接收者的学号")
    @NotBlank
    private String toStuId;
    @Schema(description = "消息内容")
    @NotBlank
    private String content;

    public String getFromStuId() {
        return fromStuId;
    }

    public void setFromStuId(String fromStuId) {
        this.fromStuId = fromStuId;
    }

    public String getToStuId() {
        return toStuId;
    }

    public void setToStuId(String toStuId) {
        this.toStuId = toStuId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
