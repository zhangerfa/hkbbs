package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.pojo.User;

@Schema(description = "封装帖子的信息，包含id，发帖人昵称、头像，帖子的标题、内容、发帖时间、评论数量、热度")
public class PostInfo {
    @Schema(description = "发帖人信息：发帖人昵称（当为树洞时为随即昵称），发帖人头像url，发帖人学号")
    private User poster;
    @Schema(description = "帖子信息")
    private Post post;

    public PostInfo(Post post) {
        this.post = post;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
