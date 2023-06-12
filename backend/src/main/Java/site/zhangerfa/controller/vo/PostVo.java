package site.zhangerfa.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import site.zhangerfa.entity.Post;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "封装帖子的信息，包含id，发帖人昵称、头像，帖子的标题、内容、发帖时间、评论数量、热度")
public class PostVo {
    private int id;
    @Schema(description = "发帖人信息")
    private UserVo poster;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "正文")
    private String content;
    @Schema(description = "发帖时间")
    private Date createTime;
    @Schema(description = "评论数量")
    private int commentNum;
    @Schema(description = "点赞数量")
    private int likeNum;
    @Schema(description = "热度")
    private int hot;
    @Schema(description = "帖子中图片URL集合")
    private List<String> images;

    public PostVo(){}

    public PostVo(Post post){
        this.id = post.getId();
        this.commentNum = post.getCommentNum();
        this.content = post.getContent();
        this.hot = post.getHot();
        this.images = post.getImages();
        this.createTime = post.getCreateTime();
        this.title = post.getTitle();
    }
}
