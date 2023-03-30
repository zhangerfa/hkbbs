package site.zhangerfa.service;

import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;

import java.util.Date;

public interface LoginTicketService {
    /**
     * 增加登录凭证
     * @param loginTicket
     * @return
     */
    boolean add(LoginTicket loginTicket);

    /**
     * 根据传入的登录凭证码查询登录凭证信息
     * @param ticket 登录凭证码
     * @return
     */
    LoginTicket getLoginTicketByTicket(String ticket);

    /**
     * 根据传入的登录凭证码修改登录凭证状态
     *
     * @param stuId 学号
     * @param status 状态：1有效，0无效
     * @return
     */
    boolean updateStatus(String stuId, int status);

    boolean updateExpired(String stuId, Date expired);

    /**
     * 检查登陆凭证是否有效
     *   传入ticket为空返回false
     *      判断状态码是否有效
     *      有效还要判断是否过期
     *      有效且未过期返回true
     *      有效但已过期，将状态改为无效，返回false
     * @param ticket
     * @return
     */
    boolean checkLoginTicket(String ticket);

    /**
     * 获取该用户的登录凭证信息
     * @param stuId
     * @return
     */
    LoginTicket getLoginTicketByStuId(String stuId);
}
