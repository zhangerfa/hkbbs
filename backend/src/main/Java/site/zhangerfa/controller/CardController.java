package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.Constant.Goal;
import site.zhangerfa.controller.in.InCard;
import site.zhangerfa.controller.in.InPage;
import site.zhangerfa.controller.tool.CardContainStuId;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;

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
    public Result<Boolean> addCard(InCard inCard){
        String posterId = hostHolder.getUser().getStuId();
        if (inCard.getImages().size() == 0)
            return new Result<>(Code.SAVE_ERR, false, "请至少上床一张图片");
        // 将图片上传到图床
        CardContainStuId card = new CardContainStuId(posterId, inCard);
        card.setImageUrls(imgShackUtil.getImageUrls(inCard.getImages()));
        // 发布卡片
        cardService.add(card);
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
        CardContainStuId card = new CardContainStuId(aboutMe, expected);
        card.setId(cardId);
        cardService.update(card);
        return new Result<>(Code.UPDATE_OK, true);
    }

    @Operation(summary = "获取卡片", description = "获取指定id的卡片信息，包含其发布者的信息")
    @GetMapping("/{cardId}")
    public Result<CardContainsPoster> getCard(@PathVariable int cardId){
        if (cardService.getById(cardId) == null)
            return new Result<>(Code.GET_ERR, null, "您要查看的卡片不存在");
        return new Result<>(Code.GET_OK, cardService.getById(cardId));
    }

    @Operation(summary = "获取一页卡片", description = "获取一页卡片")
    @GetMapping("/")
    @Parameter(name = "posterId", description = "当要获取指定用户发送的卡片时，传入其学号，当要获取最新发布的一页帖子时，传入'0'")
    public Result<List<CardContainsPoster>> getOnePageCards(InPage inPage,
                                                            String posterId,
                                                            Goal goal){
        Page page = new Page(inPage);
        return new Result<>(Code.GET_OK, cardService.getOnePageCards(posterId, page, goal.getCode()));
    }

}
