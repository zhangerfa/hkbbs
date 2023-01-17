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
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 根据登录凭证码查询登录凭证信息
     * @param ticket 登录凭证码
     * @return
     */
    LoginTicket selectByTicket(String ticket);

    /**
     * 获取该用户的登录凭证信息
     * @param stuId 学号
     * @return
     */
    LoginTicket selectByStuId(String stuId);

    /**
     * 修改该用户的登录凭证状态
     * @param
     * @return
     */
    int updateStatus(String stuId, int status);

    /**
     * 修改登录凭证过期时间
     * @param stuId
     * @param expired
     * @return
     */
    int updateExpired(String stuId, Date expired);
}
