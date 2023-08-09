package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.in.ImgMessageIn;
import site.zhangerfa.controller.in.MessageIn;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.controller.vo.ChatVo;
import site.zhangerfa.service.ChatService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;

import java.util.List;


@RestController
@RequestMapping("/chats")
@Tag(name = "私聊")
public class ChatController {
    @Resource
    private ChatService chatService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private ImgShackUtil imgShackUtil;

    @Operation(summary = "获取当前用户的未读消息数量",
            description = "当聊天对象学号传为 ‘0’ 时查询该用户所有聊天的总未读消息数量")
    @GetMapping("/unreadNum")
    public Result<Integer> getUnreadMessagesNum(@Parameter(description = "聊天对象的学号") String chatToStuId){
        if (!chatToStuId.equals("0") && userService.isExist(chatToStuId))
            return new Result<>(Code.GET_ERR, "聊天对象不存在");
        String stuId = hostHolder.getUser().getStuId();
        int unreadMessagesNum = chatService.getUnreadMessagesNum(stuId, chatToStuId);
        return new Result<>(Code.GET_OK, unreadMessagesNum);
    }

    @Operation(summary = "获取当前用户的一页聊天记录", description = "获取当前用户和指定聊天对象的一页聊天记录")
    @GetMapping("/{chatToStuId}")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<ChatVo> getOnePageMessagesForChat(int currentPage, int pageSize,
                                                    @PathVariable @Parameter(description = "聊天对象的学号") String chatToStuId){
        String stuId = hostHolder.getUser().getStuId();
        if (!userService.isExist(chatToStuId))
            return new Result<>(Code.GET_ERR, "聊天对象不存在");
        ChatVo chatVo = chatService.selectOnePageMessagesForChat(stuId, chatToStuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chatVo);
    }

    @Operation(summary = "获取当前用户的一页聊天列表",
            description = "获取当前用户的一页聊天列表，其中每个聊天数据中只包含该聊天最后一条消息，" +
                    "且聊天对象以最后一条消息的发送时间来降序")
    @GetMapping("/")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<List<ChatVo>> getLatestMessages(int currentPage, int pageSize){
        String stuId = hostHolder.getUser().getStuId();
        List<ChatVo> chatVos = chatService.selectLatestMessages(stuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chatVos);
    }

    @Operation(summary = "发布一条文字消息")
    @PostMapping("/")
    public Result<Boolean> sendMessage(@RequestBody MessageIn messageIn){
        messageIn.setType(0);
        // 判断能否发送消息
        messageIn.setPosterId(hostHolder.getUser().getStuId());
        Result<Boolean> result = chatService.canSendMessage(messageIn.getPosterId(),
                messageIn.getReceiverId());
        if (!result.getData())
            return result;
        // 发布消息
        messageIn.setPosterId(hostHolder.getUser().getStuId());
        boolean flag = chatService.addMessage(messageIn);
        int code = flag? Code.SAVE_OK: Code.SAVE_ERR;
        return new Result<>(code, flag);
    }

    @Operation(summary = "发布一条图片消息")
    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public Result<Boolean> sendImage(@RequestBody ImgMessageIn imgMessageIn){
        imgMessageIn.setType(1);
        // 判断能否发送消息
        imgMessageIn.setPosterId(hostHolder.getUser().getStuId());
        Result<Boolean> result = chatService.canSendMessage(imgMessageIn.getPosterId(),
                imgMessageIn.getReceiverId());
        if (!result.getData())
            return result;
        // 发布消息
        String imageUrl = imgShackUtil.add(imgMessageIn.getImage());
        imgMessageIn.setContent(imageUrl);
        boolean flag = chatService.addMessage(imgMessageIn);
        int code = flag? Code.SAVE_OK: Code.SAVE_ERR;
        return new Result<>(code, flag);
    }

    @Operation(summary = "消息已读")
    @PutMapping("/read")
    public Result<Boolean> readMessages(@Parameter(description = "已读消息的id列表")
                                            @RequestBody List<Integer> readMessageIds){
        chatService.readMessages(readMessageIds);
        return new Result<>(Code.UPDATE_OK);
    }
}
