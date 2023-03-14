package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "封装评论的详细数据，包括评论内容，发布者信息，评论信息，评论的分页信息")
public class CommentDetails extends PostDetails<Comment>{
    @Schema(description = "评论深度")
    private int deep;

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
