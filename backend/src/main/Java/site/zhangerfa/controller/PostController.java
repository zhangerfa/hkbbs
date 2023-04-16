package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.in.InComment;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.PageUtil;
import site.zhangerfa.util.PostUtil;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    @Resource(name = "postServiceImpl")
    private PostService postService;
    @Resource
    private PostUtil postUtil;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private ImgShackUtil imgShackUtil;
    @Resource
    private CommentService commentService;

    @Tag(name = "帖子")
    @Operation(summary = "发布帖子", description = "传入标题和内容，图片是可选的，可以传入若干张图片")
    @PostMapping(value = "/posts/")
    @Parameters({@Parameter(name = "title", description = "标题", required = true),
            @Parameter(name = "content", description = "内容", required = true)})
    public Result<Boolean> addPost(String title, String content,
                                   @RequestPart(required = false)@Parameter(description = "图片集合，可选") List<MultipartFile> images){
        if (hostHolder.getUser() == null) return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        // 将传入图片上传到图床，并将url集合添加到post中
        Post post = new Post(title, content);
        post.setImages(imgShackUtil.getImageUrls(images));
        // 发布帖子
        String stuId = hostHolder.getUser().getStuId();
        post.setPosterId(stuId);
        boolean flag = postService.add(post, Constant.ENTITY_TYPE_POST);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    @Tag(name = "帖子")
    @DeleteMapping("/posts/delete/{postId}")
    @Operation(summary = "删除帖子", description = "删除帖子及帖子中所有评论")
    public Result<Boolean> delete(@PathVariable int postId){
        Map<String, Object> map = postService.deleteById(postId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @Tag(name = "帖子、树洞共有")
    @DeleteMapping("/delete/comment/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论及评论中的子评论")
    @ResponseBody
    public Result<Boolean> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = postService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result<>(code, (Boolean) map.get("result"), (String)map.get("msg"));
    }

    @Tag(name = "帖子、树洞共有")
    @Operation(summary = "帖子、树洞详情", description = "返回帖子或树洞中的详细数据，包括帖子或树洞内容，发布者信息，评论信息，评论的分页信息")
    @GetMapping("/details/{postId}")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<PostDetails<Post>> getDetails(@PathVariable @Parameter(description = "帖子或树洞id") int postId,
                                                int currentPage, int pageSize){
        return postUtil.getPostAndPosterDetails(postId, currentPage, pageSize,
                Constant.ENTITY_TYPE_POST);
    }

    @Tag(name = "帖子")
    @Operation(summary = "发布评论（包括对评论评论）", description = "需要传入被评论实体的类型和id，以及被评论实体所属的帖子id")
    @PostMapping("/posts/comment")
    public Result<Boolean> addComment(InComment inComment,
                                      @Parameter(description = "被评论实体所属树洞的id") int postId){
        // 增加评论
        Comment comment = new Comment(inComment.getEntityType(), inComment.getEntityId(), inComment.getContent());
        postService.addComment(comment, postId);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, postId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @Tag(name = "帖子")
    @Operation(summary = "获取一页帖子", description = "返回一页帖子，包含id,标题，内容和作者id，发帖时间，评论数量，热度")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小"),
            @Parameter(name = "stuId", description = "当要获取指定用户发送的帖子时，传入其学号，当要获取最新发布的一页帖子时，传入'0'")
    })
    @GetMapping("/posts/{stuId}")
    public Result<List<PostInfo>> getOnePagePosts(int currentPage, int pageSize, @PathVariable String stuId){
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.SAVE_ERR, null, "学号错误");
        Result<List<Post>> result = postService.getOnePagePosts(stuId, Constant.ENTITY_TYPE_POST, currentPage, pageSize);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        List<PostInfo> postInfos = postUtil.completePostInfo(result);
        return new Result<>(Code.GET_OK, postInfos, "查询成功");
    }
}
