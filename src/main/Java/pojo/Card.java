package pojo;

import java.time.LocalDateTime;

/*
征友墙上的卡片的抽象
 */
public class Card {
    private String posterName; // 发帖人姓名
    private String posterId; // 发帖人ID（学号）
    private LocalDateTime createTime; // 发帖时间
    private String title;
    private String content; // 帖子中文字

    public Card(String posterName, String posterId,String title, String content){
        this.posterId = posterId;
        this.posterName = posterName;
        this.title = title;
        this.content = content;
        createTime = LocalDateTime.now();
    }

    public String getPosterId() {
        return posterId;
    }

    public String getPosterName() {
        return posterName;
    }

    public LocalDateTime getCreateTime() {
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
