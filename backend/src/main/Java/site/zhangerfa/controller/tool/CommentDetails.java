package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装评论的详细数据，包括评论内容，发布者信息，评论信息，评论的分页信息")
public class CommentDetails {
    @Schema(description = "评论Id")
    private int id;
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
        id = comment.getId();
        content = comment.getContent();
        createTime = comment.getCreateTime();
    }
}
