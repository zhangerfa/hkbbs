package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "封装新建卡片时的数据")
public class BaseCard {
    @Schema(description = "关于我：自我介绍")
    @NotBlank
    private String aboutMe;
    @Schema(description = "期望的TA:描述期望中的理想征友对象")
    @NotBlank
    private String expected;

    public BaseCard(String aboutMe, String expect) {
        this.aboutMe = aboutMe;
        this.expected = expect;
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
}
