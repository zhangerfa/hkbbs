package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Post;

import java.util.Date;
import java.util.List;

@Schema(description = "封装帖子的信息，包含id，发帖人昵称、头像，帖子的标题、内容、发帖时间、评论数量、热度")
public class PostInfo {
    private int id;
    @Schema(description = "发帖人昵称（树洞为匿名昵称）")
    private String posterName;
    @Schema(description = "发帖人头像url（树洞为随机头像）")
    private String posterHeaderUrl;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "正文")
    private String content;
    @Schema(description = "发帖时间")
    private Date createTime;
    @Schema(description = "评论数量")
    private int commentNum;
    @Schema(description = "热度")
    private int hot;
    @Schema(description = "帖子中图片URL集合")
    private List<String> images;

    public PostInfo(){}

    public PostInfo(Post post){
        this.id = post.getId();
        this.commentNum = post.getCommentNum();
        this.content = post.getContent();
        this.hot = post.getHot();
        this.images = post.getImages();
        this.createTime = post.getCreateTime();
        this.title = post.getTitle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterHeaderUrl() {
        return posterHeaderUrl;
    }

    public void setPosterHeaderUrl(String posterHeaderUrl) {
        this.posterHeaderUrl = posterHeaderUrl;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
