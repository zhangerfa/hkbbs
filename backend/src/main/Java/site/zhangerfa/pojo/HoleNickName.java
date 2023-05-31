package site.zhangerfa.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;

@Schema(description = "树洞中用户的匿名昵称")
@TableName("hole_nickname")
@Data
public class HoleNickName {
    @Schema(description = "树洞id")
    int holeId;
    @Schema(description = "用户学号")
    String posterId;
    @Schema(description = "用户匿名昵称")
    String nickname;

    public HoleNickName(int holeId, String posterId, String nickname) {
        this.holeId = holeId;
        this.posterId = posterId;
        this.nickname = nickname;
    }
}
