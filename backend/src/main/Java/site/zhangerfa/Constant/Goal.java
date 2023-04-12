package site.zhangerfa.Constant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "交友目标")
public enum Goal {
    DEFAULT(-1, "未设置"),
    LOVE(0, "恋爱"),
    GAME(1, "电子游戏"),
    BOARD_GAME(2, "桌游"),
    STUDY(3, "学习"),
    SPORT(4, "运动"),
    TRAVEL(5, "旅游"),
    WALK(6, "散步");

    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    Goal(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Goal valueOf(int code){
        for (Goal goal : values()) {
            if (goal.code == code) return goal;
        }
        return DEFAULT;
    }

    @Override
    public String toString() {
        return this.name() + "(" + desc + ")";
    }
}
