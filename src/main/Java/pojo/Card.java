package pojo;

import java.time.LocalDateTime;
import java.util.Date;

/*
征友墙上的卡片的抽象
 */
public class Card {
    private String posterName; // 发帖人姓名
    private String posterId; // 发帖人ID（学号）
    private String title;
    private String content; // 帖子中文字
    private Date createTime; // 发帖时间

    public Card(){}

    // 不传入卡片创建时间，视为新卡片以当前时间作为创建时间
    public Card(String posterId, String title, String content){
        this.posterId = posterId;
        this.title = title;
        this.content = content;
    }
    public Card(String posterName, String posterId,String title, String content){
        this(posterId, title, content);
        createTime = new Date();
    }

    public Card(String posterName, String posterId,String title, String content, Date createTime){
        this(posterName, posterId, title, content);
        this.createTime = createTime;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterId() {
        return posterId;
    }

    public String getPosterName() {
        return posterName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Card{" +
                "poster=" + posterName +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
