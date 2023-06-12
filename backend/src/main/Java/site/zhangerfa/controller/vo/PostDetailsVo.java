package site.zhangerfa.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.entity.Page;
import site.zhangerfa.entity.Post;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装帖子的详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息")
public class PostDetailsVo {
    @Schema(description = "帖子id")
    private int id;
    @Schema(description = "发布者信息")
    private UserVo poster;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "发布时间")
    private Date createTime;
    @Schema(description = "评论数量")
    private int commentNum;
    @Schema(description = "点赞数量")
    private int likeNum;
    @Schema(description = "图片集合")
    private List<String> images;
    private String posterHeaderUrl;
    @Schema(description = "评论详细信息集合")
    private List<CommentDetailsVo> commentDetailVos;
    @Schema(description = "帖子中评论的分页信息")
    private Page page;

    public PostDetailsVo(){}

    public PostDetailsVo(Post post){
        title = post.getTitle();
        content = post.getContent();
        createTime = post.getCreateTime();
        commentNum = post.getCommentNum();
        images = post.getImages();
    }
}
