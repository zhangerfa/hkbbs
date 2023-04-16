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
import site.zhangerfa.pojo.Chat;
import site.zhangerfa.service.ChatService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;

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

    @Operation(summary = "获取当前用户的未读消息数量",
            description = "当聊天对象学号传为 ‘0’ 时查询该用户所有聊天的总未读消息数量")
    @GetMapping("/unreadNum")
    public Result<Integer> getUnreadMessagesNum(@Parameter(description = "聊天对象的学号") String chatToStuId){
        if (!userService.isExist(chatToStuId) && chatToStuId.equals("0"))
            return new Result<>(Code.GET_ERR, "聊天对象不存在");
        String stuId = hostHolder.getUser().getStuId();
        int unreadMessagesNum = chatService.getUnreadMessagesNum(stuId, chatToStuId);
        return new Result<>(Code.GET_OK, unreadMessagesNum);
    }

    @Operation(summary = "获取一页聊天记录", description = "获取当前用户和指定聊天对象的一页聊天记录")
    @GetMapping("/{chatToStuId}")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<Chat> getOnePageMessagesForChat(int currentPage, int pageSize,
                                                  @PathVariable @Parameter(description = "聊天对象的学号") String chatToStuId){
        String stuId = hostHolder.getUser().getStuId();
        if (!userService.isExist(chatToStuId))
            return new Result<>(Code.GET_ERR, "聊天对象不存在");
        Chat chat = chatService.selectOnePageMessagesForChat(stuId, chatToStuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chat);
    }

    @Operation(summary = "获取当前用户的一页聊天列表",
            description = "获取当前用户的一页聊天列表，其中每个聊天数据中只包含该聊天最后一条消息，" +
                    "且聊天对象以最后一条消息的发送时间来降序")
    @GetMapping("/")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<List<Chat>> getLatestMessages(int currentPage, int pageSize){
        String stuId = hostHolder.getUser().getStuId();
        List<Chat> chats = chatService.selectLatestMessages(stuId, currentPage, pageSize);
        return new Result<>(Code.GET_OK, chats);
    }

    @Operation(summary = "当前用户给指定用户发送一条消息")
    @PostMapping("/")
    @Parameters({@Parameter(name = "fromStuId", description = "发送消息者学号"),
                @Parameter(name = "toStuId", description = "接收消息者学号"),
                @Parameter(name = "content", description = "消息内容")})
    public Result<Boolean> sendMessage(String fromStuId, String toStuId, String content){
        return new Result<>();
    }

    @Operation(summary = "当前用户给指定用户发送一张图片")
    @PostMapping("/image")
    public Result<Boolean> sendImage(@Parameter(description = "消息发布者学号") String fromStuId,
                                     @Parameter(description = "消息接收者学号") String toStuId,
                                     @Parameter(description = "图片文件") MultipartFile image){
        return new Result<>();
    }
}
