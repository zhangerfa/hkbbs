package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.Post;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装帖子的详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息")
public class PostDetails {
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "发布时间")
    private Date createTime;
    @Schema(description = "评论数量")
    private int commentNum;
    @Schema(description = "图片集合")
    private List<String> images;
    @Schema(description = "发布者用户名")
    private String posterName;
    @Schema(description = "发布者头像url")
    private String posterHeaderUrl;
    @Schema(description = "评论详细信息集合")
    private List<CommentDetails> commentDetails;
    @Schema(description = "帖子中评论的分页信息")
    private Page page;

    public PostDetails(){}

    public PostDetails(Post post){
        title = post.getTitle();
        content = post.getContent();
        createTime = post.getCreateTime();
        commentNum = post.getCommentNum();
        images = post.getImages();
    }
}
