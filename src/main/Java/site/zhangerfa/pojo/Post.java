package site.zhangerfa.pojo;

import java.util.Date;

/**
 * 所有帖子类的祖先类
 */
public class Post {
    private int id;
    private String posterId; // 发帖人学号
    private String title; // 标题
    private String content; // 正文
    private Date createTime = new Date(); // 发帖时间
    private int commentNum = 0; // 评论数量
    private int hot = 0; // 热度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }
}
