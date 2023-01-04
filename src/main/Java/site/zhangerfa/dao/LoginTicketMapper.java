package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.LoginTicket;

import java.util.Date;

@Mapper
public interface LoginTicketMapper {
    /**
     * 添加登录凭证
     * @param loginTicket
     * @return
     */
    int insertTicket(LoginTicket loginTicket);

    /**
     * 根据ticket查询登录凭证信息
     * @param ticket
     * @return
     */
    LoginTicket selectByTicket(String ticket);

    /**
     * 根据凭证 修改登录凭证状态
     * @param ticket
     * @return
     */
    int updateStatus(String ticket, int status);

    /**
     * 修改登录凭证过期时间
     * @param ticket
     * @param expired
     * @return
     */
    int updateExpired(String ticket, Date expired);
}
