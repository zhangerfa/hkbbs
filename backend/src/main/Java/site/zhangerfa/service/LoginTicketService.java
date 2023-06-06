package site.zhangerfa.service;

import site.zhangerfa.pojo.LoginTicket;

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
     * 更新登录凭证状态:
     *    当改为有效时，还需修改有效时间
     *    当改为无效时，还需修改无效时间
     * @param loginTicket
     * @return
     */
    boolean update(LoginTicket loginTicket);

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
