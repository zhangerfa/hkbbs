package site.zhangerfa.controller.aop;

import jakarta.annotation.Resource;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.ui.Model;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;

/**
 * 为需要显示未读消息数量的页面增加未读消息数量数据
 */
//@Component
//@Aspect
public class NoticeAspect {
    @Resource
    private NoticeService noticeService;
    @Resource
    private HostHolder hostHolder;

    // 定义切入点
    @Pointcut("execution(String site.zhangerfa.controller.*Controller.getDetails(..))")
    public void notice(){}

    // 定义通知
    @AfterReturning("notice() && args(model, ..)")
    public void addUnreadNoticeNum(Model model){
        int numOfUnreadNotice = noticeService.getNumOfUnreadNotice(hostHolder.getUser().getStuId());
        model.addAttribute("unreadNoticeNum", numOfUnreadNotice);
    }
}
