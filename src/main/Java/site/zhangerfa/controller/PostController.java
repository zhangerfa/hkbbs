package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.PostDetails;
import site.zhangerfa.controller.tool.PostInfo;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.PostUtil;

import java.util.ArrayList;
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

    @Tag(name = "卡片")
    @Operation(summary = "发布卡片")
    @Parameters({
            @Parameter(name = "title", description = "标题", required = true),
            @Parameter(name = "content", description = "内容", required = true),
            @Parameter(name = "images", description = "帖子中的图片集合")})
    @PostMapping
    public Result<Boolean> addCard(@RequestBody @Parameter(hidden = true) Card card, MultipartFile[] images){
        if (hostHolder.getUser() == null) return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        // 将传入图片上传到图床，并将url集合添加到card中
        List<Image> imageUrl = new ArrayList<>();
        for (MultipartFile image : images) {
            imageUrl.add(new Image(card.getId(), imgShackUtil.add(image, "")));
        }
        card.setImages(imageUrl);
        // 发布卡片
        String stuId = hostHolder.getUser().getStuId();
        card.setPosterId(stuId);
        boolean flag = postService.add(card, Constant.ENTITY_TYPE_CARD);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    @Tag(name = "卡片")
    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "删除卡片", description = "删除卡片及卡片中所有评论")
    public Result<Boolean> delete(@PathVariable int postId){
        Map<String, Object> map = postService.deleteById(postId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @Tag(name = "帖子（包括树洞）共有")
    @DeleteMapping("/delete/comment/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论及评论中的子评论")
    @ResponseBody
    public Result<Boolean> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = postService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result<>(code, (Boolean) map.get("result"), (String)map.get("msg"));
    }

    @Tag(name = "帖子（包括树洞）共有")
    @Operation(summary = "帖子详情", description = "返回帖子详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的评论数量", required = true),
    })
    @GetMapping("/details/{postId}")
    public Result<PostDetails<Post>> getDetails(@PathVariable @Parameter(description = "帖子id") int postId,
                                                @Parameter(hidden = true) Page page){
        return postUtil.getPostAndPosterDetails(postId, new Page(1,
                page.getPageSize()), postService.getPostType(postId));
    }

    @Tag(name = "卡片")
    @Operation(summary = "发布评论（包括对评论评论）", description = "需要传入被评论实体的类型和id，以及被评论实体所属的帖子id")
    @PostMapping("/comment")
    @Parameters({
            @Parameter(name = "entityType", description = "被评论实体的类型", required = true),
            @Parameter(name = "entityId", description = "被评论实体的id", required = true),
            @Parameter(name = "content", description = "评论内容", required = true),
            @Parameter(name = "cardId", description = "被评论实体所属帖子的id", required = true)
    })
    public Result<Boolean> addComment(@Parameter(hidden = true) Comment comment, int cardId){
        // 增加评论
        postService.addComment(comment, cardId);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @Tag(name = "卡片")
    @Operation(summary = "获取一页卡片", description = "返回一页卡片，包含id,标题，内容和作者id，发帖时间，评论数量，热度")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的卡片数量", required = true),
            @Parameter(name = "stuId", description = "当要获取指定用户发送的卡片时，传入其学号，当要获取最新发布的一页卡片时，传入'0'")
    })
    @GetMapping("/{stuId}")
    public Result<List<PostInfo>> getOnePageCards(@Parameter(hidden = true)Page page, @PathVariable String stuId){
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.SAVE_ERR, null, "学号错误");
        Result<List<Post>> result = postService.getOnePagePosts(stuId, page, Constant.ENTITY_TYPE_CARD);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        List<PostInfo> postInfos = completePostInfo(result);
        return new Result<>(Code.GET_OK, postInfos, "查询成功");
    }

    @Tag(name = "树洞")
    @Operation(summary = "获取一页树洞", description = "返回一页树洞，包含id,标题，内容和作者id，发帖时间，评论数量，热度")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的卡片数量", required = true),
            @Parameter(name = "stuId", description = "当要获取指定用户发送的树洞时，传入其学号，当要获取最新发布的一页树洞时，传入'0'")
    })
    @GetMapping("/holes/{stuId}")
    public Result<List<PostInfo>> getOnePageHoles(@Parameter(hidden = true)Page page, @PathVariable String stuId){
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.SAVE_ERR, null, "学号错误");
        Result<List<Post>> result = postService.getOnePagePosts(stuId, page, Constant.ENTITY_TYPE_HOLE);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        List<PostInfo> postInfos = completePostInfo(result);
        return new Result<>(Code.GET_OK, postInfos, "查询成功");
    }

    private List<PostInfo> completePostInfo(Result<List<Post>> result){
        List<PostInfo> postInfos = new ArrayList<>();
        for (Post post : result.getData()) {
            PostInfo postInfo = new PostInfo(post);
            // 补充发帖人信息
            postInfo.setPoster(userService.getUserByStuId(post.getPosterId()));

            postInfos.add(postInfo);
        }
        return postInfos;
    }
}
