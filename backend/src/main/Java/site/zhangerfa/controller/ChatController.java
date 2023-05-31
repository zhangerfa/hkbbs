package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.controller.tool.ChatInfo;
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
    public Result<ChatInfo> getOnePageMessagesForChat(int currentPage, int pageSize,
                                                      @PathVariable @Parameter(description = "聊天对象的学号") String chatToStuId){
        String stuId = hostHolder.getUser().getStuId();
        if (!userService.isExist(chatToStuId))
            return new Result<>(Code.GET_ERR, "聊天对象不存在");
        ChatInfo chatInfo = chatService.selectOnePageMessagesForChat(stuId, chatToStuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chatInfo);
    }

    @Operation(summary = "获取当前用户的一页聊天列表",
            description = "获取当前用户的一页聊天列表，其中每个聊天数据中只包含该聊天最后一条消息，" +
                    "且聊天对象以最后一条消息的发送时间来降序")
    @GetMapping("/")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<List<ChatInfo>> getLatestMessages(int currentPage, int pageSize){
        String stuId = hostHolder.getUser().getStuId();
        List<ChatInfo> chatInfos = chatService.selectLatestMessages(stuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chatInfos);
    }

    @Operation(summary = "发布一条文字消息")
    @PostMapping("/")
    @Parameters({@Parameter(name = "fromStuId", description = "消息发布者学号"),
            @Parameter(name = "toStuId", description = "消息接收者学号", required = true),
            @Parameter(name = "content", description = "消息内容", required = true)})
    public Result<Boolean> sendMessage(String fromStuId, String toStuId, String content){
        // 发布消息
        boolean flag = chatService.addMessage(fromStuId, toStuId, 0, content);
        int code = flag? Code.SAVE_OK: Code.SAVE_ERR;
        return new Result<>(code, flag);
    }

    @Operation(summary = "发布一条图片消息")
    @PostMapping("/image")
    @Parameters({@Parameter(name = "fromStuId", description = "消息发布者学号"),
        @Parameter(name = "toStuId", description = "消息接收者学号", required = true),
        @Parameter(name = "image", description = "图片文件", required = true)})
    public Result<Boolean> sendImage(String fromStuId, String toStuId,
                                     MultipartFile image){
        // 发布消息
        String imageUrl = imgShackUtil.add(image);
        boolean flag = chatService.addMessage(fromStuId, toStuId, 1, imageUrl);
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
