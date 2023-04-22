package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;

import java.util.Date;
import java.util.List;

@Schema(description = "封装评论的详细数据，包括评论内容，发布者信息，评论信息，评论的分页信息")
public class CommentDetails {
    @Schema(description = "内容")
    private String content;
    @Schema(description = "发布时间")
    private Date createTime;
    @Schema(description = "发布者用户名")
    private String posterName;
    @Schema(description = "发布者头像url")
    private String posterHeaderUrl;
    @Schema(description = "评论详细信息集合")
    private List<CommentDetails> commentDetails;
    @Schema(description = "评论中评论的分页信息")
    private Page page;
    @Schema(description = "评论深度")
    private int deep;

    public CommentDetails(){}

    public CommentDetails(Comment comment){
        content = comment.getContent();
        createTime = comment.getCreateTime();
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
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

    public List<CommentDetails> getCommentDetails() {
        return commentDetails;
    }

    public void setCommentDetails(List<CommentDetails> commentDetails) {
        this.commentDetails = commentDetails;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
