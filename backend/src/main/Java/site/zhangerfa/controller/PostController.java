package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.in.CommentIn;
import site.zhangerfa.controller.in.PostIn;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.vo.PostDetailsVo;
import site.zhangerfa.controller.vo.PostVo;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.entity.Comment;
import site.zhangerfa.entity.Post;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.PostUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "帖子、树洞")
@RestController
public class PostController {
    @Resource(name = "postServiceImpl")
    private PostService postService;
    @Resource
    private PostUtil postUtil;
    @Resource
    private NoticeService noticeService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private ImgShackUtil imgShackUtil;

    @DeleteMapping("/posts/delete/{postId}")
    @Operation(summary = "删除帖子或树洞", description = "删除帖子及帖子中所有评论")
    public Result<Boolean> delete(@PathVariable int postId){
        Map<String, Object> map = postService.deleteById(postId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @Operation(summary = "发布帖子或树洞", description = "传入标题和内容，图片是可选的，可以传入若干张图片")
    @PostMapping(value = "/posts/")
    public Result<Boolean> addPost(@RequestBody PostIn postIn){
        String title = postIn.getTitle();
        String content = postIn.getContent();
        List<MultipartFile> images = postIn.getImages();
        int type = postIn.getPostType();
        // 验证学号是否登录
        if (hostHolder.getUser() == null)
            return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        // 验证帖子类型是否正确
        if (!postUtil.isPostTypeValid(type))
            return new Result<>(Code.SAVE_ERR, "帖子类型错误");
        // 将传入图片上传到图床，并将url集合添加到post中
        Post post = new Post(title, content);
        if (images != null)
            post.setImages(imgShackUtil.getImageUrls(images));
        // 发布帖子
        String stuId = hostHolder.getUser().getStuId();
        post.setPosterId(stuId);
        boolean flag = postService.add(post, type);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    @DeleteMapping("/delete/comment/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论及评论中的子评论")
    @ResponseBody
    public Result<Boolean> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = postService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result<>(code, (Boolean) map.get("result"), (String)map.get("msg"));
    }

    @Operation(summary = "帖子、树洞详情", description = "返回帖子或树洞中的详细数据，包括帖子或树洞内容，发布者信息，评论信息，评论的分页信息")
    @GetMapping("/details/{postId}")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<PostDetailsVo> getDetails(@PathVariable @Parameter(description = "帖子或树洞id") int postId,
                                            int currentPage, int pageSize){
        return postUtil.getPostAndPosterDetails(postId, currentPage, pageSize);
    }

    @Operation(summary = "发布评论（包括对评论评论）", description = "当前登录用户为指定帖子发布一条评论。需要传入被评论实体的类型和id，以及被评论实体所属的帖子id")
    @PostMapping("/posts/comment")
    public Result<Boolean> addComment(@RequestBody CommentIn commentIn){
        // 增加评论
        Comment comment = new Comment(commentIn.getEntity(), commentIn.getContent());
        postService.addComment(comment, commentIn.getPostId());
        // 发布评论通知
        noticeService.addCommentNotice(comment);
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    // ---------------------------- 以下接口部分或全部功能需要管理员权限才可以调用
    @Operation(summary = "获取一页帖子或树洞",
            description = "返回一页帖子，包含id,标题，内容和作者id，发帖时间，评论数量，热度" +
                    "当要获取最新发布的一页帖子时，传入'0'" +
                    "管理员可以传入指定学号，获取指定用户发送的帖子时")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小"),
            @Parameter(name = "stuId", description = "当要获取指定用户发送的帖子时，传入其学号，当要获取最新发布的一页帖子时，传入'0'"),
            @Parameter(name = "type", description = "要查询的类型：1-帖子，2-树洞")
    })
    @GetMapping("/posts/{stuId}")
    public Result<List<PostVo>> getOnePagePosts(int currentPage, int pageSize, int type,
                                                @PathVariable String stuId){
        // 验证学号是否正确
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.GET_ERR, null, "学号错误");
        // 验证帖子类型是否正确
        if (!postUtil.isPostTypeValid(type))
            return new Result<>(Code.GET_ERR, "帖子类型错误");
        // 查询帖子基本信息
        Result<List<Post>> result = postService.getOnePagePosts(stuId, type, currentPage, pageSize);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        // 补全发帖人信息
        List<PostVo> postVos = postUtil.completePostInfo(result);
        return new Result<>(Code.GET_OK, postVos, "查询成功");
    }

    @Operation(summary = "获取用户的帖子或树洞数量",
            description = "当传入学号为0，返回当前用户的帖子数量；" +
                    "如果当前用户为管理员，可以传入指定学号则返回指定用户的帖子或树洞数量" +
                    "以map形式返回，key为postCount或holeCount")
    @GetMapping("/posts/count/{stuId}")
    @Tag(name = "管理员")
    public Result<Map<String, Integer>> getPostCount(@PathVariable String stuId){
        // 验证学号是否正确
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.GET_ERR, null, "学号错误");
        // 查询帖子基本信息
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postCount", postService.getTotalNums(Constant.ENTITY_TYPE_POST, stuId));
        map.put("holeCount", postService.getTotalNums(Constant.ENTITY_TYPE_HOLE, stuId));
        return new Result<>(Code.GET_OK, map);
    }

    @Tag(name = "管理员")
    @Operation(summary = "获取帖子、树洞总数量", description = "返回帖子、树洞的总数量，以map形式返回，key为postCount或holeCount")
    @GetMapping("/posts/count")
    public Result<Map<String, Integer>> getPostCount(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("postCount", postService.getTotalNums(Constant.ENTITY_TYPE_POST, "0"));
        map.put("holeCount", postService.getTotalNums(Constant.ENTITY_TYPE_HOLE, "0"));
        return new Result<>(Code.GET_OK, map);
    }
}
