package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Schema(description = "卡片")
public class Card extends BaseCard {
    int id;
    @Schema(description = "年级")
    String age;
    @Schema(description = "发布时间")
    Date create_time;
    @Schema(description = "交友目标")
    private int goal;
    @Schema(description = "图片url集合")
    private List<String> imageUrls;

    public Card(String aboutMe, String expect) {
        super(aboutMe, expect);
    }

    public Card(String aboutMe, String expect, int goal) {
        super(aboutMe, expect);
        this.goal = goal;
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

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
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
}
