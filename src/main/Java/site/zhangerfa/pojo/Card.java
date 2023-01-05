package site.zhangerfa.pojo;

import java.util.Date;

/*
征友墙上的卡片的抽象
 */
public class Card {
    private String posterId; // 发帖人ID（学号）
    private String title;
    private String content; // 帖子中文字
    private int commentNum; // 评论数量
    private Date createTime; // 发帖时间

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Card(){}

    // 不传入卡片创建时间，视为新卡片以当前时间作为创建时间
    public Card(String posterId, String title, String content){
        this.posterId = posterId;
        this.title = title;
        this.content = content;
    }

    public Card(String posterId,String title, String content, Date createTime){
        this(posterId, title, content);
        this.createTime = createTime;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getPosterId() {
        return posterId;
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
                "posterId=" + posterId+
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
