package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.Constant.Goal;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.UserUtil;

import java.util.List;

@RestController
@Tag(name = "卡片")
@RequestMapping("/cards")
public class CardController {
    @Resource
    private CardService cardService;
    @Resource
    private ImgShackUtil imgShackUtil;
    @Resource
    private HostHolder hostHolder;

    @Operation(summary = "发布卡片", description = "图片至少上传一张")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> addCard(String aboutMe, String expected, @Parameter(description = "交友目标：") @RequestParam Goal goal,
                                   @RequestPart @Parameter(description = "图片文件集合，至少发布一张图片") List<MultipartFile> images){
        // 判断用户是否登录
        if (!UserUtil.isLogin(hostHolder))
            return new Result<>(Code.SAVE_ERR, "用户未登录");
        // 获取用户学号
        String posterId = hostHolder.getUser().getStuId();
        // 检查是否上传照片
        if (images.size() == 0)
            return new Result<>(Code.SAVE_ERR, false, "请至少上床一张图片");
        // 将图片上传到图床
        Card card = new Card(aboutMe, expected, goal.getCode());
        card.setImageUrls(imgShackUtil.getImageUrls(images));
        // 发布卡片
        cardService.add(posterId, card);
        return new Result<>(Code.SAVE_OK, true);
    }

    @Operation(summary = "删除卡片", description = "删除卡片")
    @DeleteMapping("/{cardId}")
    public Result<Boolean> deleteCard(@PathVariable int cardId){
        if (cardService.getById(cardId) == null)
            return new Result<>(Code.DELETE_ERR, false, "您要删除的卡片不存在");
        cardService.deleteById(cardId);
        return new Result<>(Code.DELETE_OK, true);
    }

    @Operation(summary = "修改卡片", description = "修改卡片的文字内容，包括修改 关于我 期望中的TA。只需为需要修改值的字段传入新值")
    @PutMapping("/{cardId}")
    public Result<Boolean> updateCard(@PathVariable int cardId, @RequestParam(required = false) String aboutMe,
                                      @RequestParam(required = false) String expected){
        if (cardService.getById(cardId) == null)
            return new Result<>(Code.UPDATE_ERR, false, "您要修改的卡片不存在");
        Card card = new Card(aboutMe, expected);
        card.setId(cardId);
        cardService.update(card);
        return new Result<>(Code.UPDATE_OK, true);
    }

    @Operation(summary = "获取卡片", description = "获取指定id的卡片信息，包含其发布者的信息")
    @GetMapping("/{cardId}")
    public Result<CardInfo> getCard(@PathVariable int cardId){
        CardInfo cardInfo = cardService.getById(cardId);
        if (cardInfo == null)
            return new Result<>(Code.GET_ERR, null, "您要查看的卡片不存在");
        return new Result<>(Code.GET_OK, cardInfo);
    }

    @Operation(summary = "获取一页卡片", description = "获取一页卡片")
    @GetMapping(value = "/")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小"),
            @Parameter(name = "posterId", description = "当要获取指定用户发送的卡片时，传入其学号，当要获取最新发布的一页帖子时，传入'0'")})
    public Result<List<CardInfo>> getOnePageCards(int currentPage, int pageSize, String posterId, Goal goal){
        Page page = new Page(currentPage, pageSize);
        return new Result<>(Code.GET_OK, cardService.getOnePageCards(posterId, page, goal.getCode()));
    }

}
