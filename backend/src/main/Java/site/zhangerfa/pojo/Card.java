package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Schema(description = "卡片")
public class Card {
    int id;
    @Schema(description = "照片集合")
    List<Image> images;
    @Schema(description = "年级")
    String age;
    @Schema(title = "关于我", description = "自我介绍")
    String aboutMe;
    @Schema(title = "交友目标", description = "0-恋爱， 1-电子游戏， 2-桌游， 3-学习, -1-默认")
    int goal = -1;
    @Schema(description = "期望的TA", defaultValue = "描述期望中的理想征友对象")
    String expected;
    @Schema(description = "发布时间")
    Date create_time;

    /**
     * 由学号推出年级（如果本科毕业两年称为大六）
     * @param user
     */
    public void setAge(User user){
        StringBuilder age = new StringBuilder();
        String stuId = user.getStuId();
        switch (stuId.charAt(0)) {
            case 'U' -> age.append("大");
            case 'M' -> age.append("研");
            case 'D' -> age.append("博");
        }
        // 目前是上学的第几年
        int years = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(stuId.substring(1, 5));
        age.append(String.valueOf(years));
        this.age = age.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
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
