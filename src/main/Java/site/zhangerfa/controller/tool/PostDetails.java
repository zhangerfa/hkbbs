package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.controller.tool.CommentDetails;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;

import java.util.List;

@Schema(description = "封装帖子的详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息")
public class PostDetails<T> {
    @Schema(description = "帖子相关信息")
    private T post;
    @Schema(description = "发布者信息")
    private User poster;
    @Schema(description = "评论详细信息集合")
    private List<CommentDetails> commentDetails;
    @Schema(description = "帖子中评论的分页信息")
    private Page page;

    public T getPost() {
        return post;
    }

    public void setPost(T post) {
        this.post = post;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
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
