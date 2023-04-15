package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Schema(description = "封装卡片响应数据，包含发布者的数据")
public class Card {
    int id;
    @Schema(description = "关于我：自我介绍")
    @NotBlank
    private String aboutMe;
    @Schema(description = "期望的TA:描述期望中的理想征友对象")
    @NotBlank
    private String expected;
    @Schema(description = "年级")
    String age;
    @Schema(description = "发布时间")
    Date create_time;
    @Schema(description = "图片url集合")
    private List<String> imageUrls;
    @Schema(description = "交友目标")
    private int goal;

    public Card(String aboutMe, String expect, int goal) {
        this(aboutMe, expect);
        this.goal = goal;
    }

    public Card(String aboutMe, String expected) {
        this.aboutMe = aboutMe;
        this.expected = expected;
    }

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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
