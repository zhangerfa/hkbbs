package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Post;

import java.util.Date;
import java.util.List;

@Schema(description = "封装树洞列表信息，不包含评论")
public class HoleInfo {
    private int id;
    @Schema(description = "洞主昵称")
    private String posterNickname;
    @Schema(description = "洞主头像url")
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

    public HoleInfo(){}

    public HoleInfo(Post post){
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

    public String getPosterNickname() {
        return posterNickname;
    }

    public void setPosterNickname(String posterNickname) {
        this.posterNickname = posterNickname;
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
