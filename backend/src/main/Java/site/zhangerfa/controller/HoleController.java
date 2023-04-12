package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.InPage;
import site.zhangerfa.controller.tool.PostInfo;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.service.impl.HoleServiceImpl;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.PostUtil;

import java.util.List;
import java.util.Map;


@RestController
@Tag(name = "树洞")
@RequestMapping("/holes")
public class HoleController{
    @Resource(type = HoleServiceImpl.class)
    private PostService holeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;
    @Resource
    private ImgShackUtil imgShackUtil;
    @Resource(name = "postServiceImpl")
    private PostService postService;
    @Resource
    private PostUtil postUtil;
    @Resource
    private UserService userService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "发布树洞", description = "传入标题和内容，图片是可选的，可以传入若干张图片")
    @Parameters({@Parameter(name = "title", description = "标题"),
            @Parameter(name = "content", description = "内容")})
    public Result<Boolean> addHole(String title, String content,
                                   @RequestPart(required = false) @Parameter(description = "图片集合，可选") List<MultipartFile> images){
        if (hostHolder.getUser() == null) return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        // 将传入图片上传到图床，并将url集合添加到card中
        List<String> imageUrls = imgShackUtil.getImageUrls(images);
        Post post = new Post(title, content);
        post.setImages(imageUrls);
        // 发布卡片
        String stuId = hostHolder.getUser().getStuId();
        post.setPosterId(stuId);
        holeService.add(post, Constant.ENTITY_TYPE_HOLE);
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @Operation(summary = "发布评论（包括对评论评论）", description = "需要传入被评论实体的类型和id，以及被评论实体所属的帖子id")
    @PostMapping("/comment")
    @Parameters({
            @Parameter(name = "entityType", description = "被评论实体的类型(2-树洞, 3-评论)", required = true),
            @Parameter(name = "entityId", description = "被评论实体的id", required = true),
            @Parameter(name = "content", description = "评论内容", required = true),
            @Parameter(name = "holeId", description = "被评论实体所属树洞的id", required = true)
    })
    public Result<Boolean> addComment(int entityType, int entityId, String content, int cardId){
        // 增加评论
        Comment comment = new Comment(entityType, entityId, content);
        holeService.addComment(comment, cardId);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @DeleteMapping("/holeId}")
    @Operation(summary = "删除树洞", description = "删除卡片及卡片中所有评论")
    public Result<Boolean> delete(@PathVariable int holeId){
        Map<String, Object> map = holeService.deleteById(holeId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @Operation(summary = "获取一页树洞", description = "返回一页树洞，包含id,标题，内容和作者id，发帖时间，评论数量，热度")
    @Parameter(name = "stuId", description = "当要获取指定用户发送的树洞时，传入其学号，当要获取最新发布的一页树洞时，传入'0'")
    @GetMapping("/{stuId}")
    public Result<List<PostInfo>> getOnePageHoles(InPage inPage, @PathVariable String stuId){
        Page page = new Page(inPage);
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.SAVE_ERR, null, "学号错误");
        Result<List<Post>> result = postService.getOnePagePosts(stuId, page, Constant.ENTITY_TYPE_HOLE);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        List<PostInfo> postInfos = postUtil.completePostInfo(result);
        return new Result<>(Code.GET_OK, postInfos, "查询成功");
    }
}
