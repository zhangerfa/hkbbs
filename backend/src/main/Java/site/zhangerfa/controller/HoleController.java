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
import site.zhangerfa.controller.in.InComment;
import site.zhangerfa.controller.tool.*;
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
    @Resource(name = "postServiceImpl")
    private PostService postService;
    @Resource
    private PostUtil postUtil;
    @Resource
    private UserService userService;

    @DeleteMapping("/delete/post/{holeId}")
    @Operation(summary = "删除树洞", description = "删除卡片及卡片中所有评论")
    public Result<Boolean> delete(@PathVariable int holeId){
        Map<String, Object> map = holeService.deleteById(holeId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @Operation(summary = "获取一页树洞", description = "返回一页树洞，包含id,标题，内容和作者id，发帖时间，评论数量，热度")
    @GetMapping("/{stuId}")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小"),
            @Parameter(name = "stuId", description = "当要获取指定用户发送的树洞时，传入其学号，当要获取最新发布的一页树洞时，传入'0'")})
    public Result<List<HoleInfo>> getOnePageHoles(int currentPage, int pageSize, @PathVariable String stuId){
        if (stuId == null || (!stuId.equals("0") && !userService.isExist(stuId)))
            return new Result<>(Code.SAVE_ERR, null, "学号错误");
        Result<List<Post>> result = postService.getOnePagePosts(stuId ,Constant.ENTITY_TYPE_HOLE, currentPage ,pageSize);
        if (result.getCode() == Code.GET_ERR) return new Result<>(Code.GET_ERR, null, result.getMsg());
        List<HoleInfo> postInfos = postUtil.completeHoleInfo(result);
        return new Result<>(Code.GET_OK, postInfos, "查询成功");
    }
}
