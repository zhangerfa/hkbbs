package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.service.LoginTicketService;

import java.util.Date;

@Service
public class LoginTicketImpl implements LoginTicketService {
    @Resource
    private LoginTicketMapper loginTicketMapper;

    @Override
    public boolean add(LoginTicket loginTicket) {
        int insertNum = loginTicketMapper.insertLoginTicket(loginTicket);
        return insertNum > 0;
    }

    @Override
    public LoginTicket getLoginTicketByTicket(String ticket) {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket(ticket);
        return loginTicket;
    }

    @Override
    public boolean updateStatus(String stuId, int status) {
        int numOfUpdate = loginTicketMapper.updateStatus(stuId, status);
        return numOfUpdate != 0;
    }

    @Override
    public boolean updateExpired(String stuId, Date expired) {
        loginTicketMapper.updateExpired(stuId, expired);
        return false;
    }

    @Override
    public boolean checkLoginTicket(String ticket) {
        if (ticket == null) return false;
        LoginTicket loginTicket = getLoginTicketByTicket(ticket);
        if (loginTicket != null && loginTicket.getStatus() == 1){
            // 有效，判断是否过期
            if (loginTicket.getExpired().after(new Date())){
                // 未过期
                return true;
            }else {
                // 过期，更改登录凭证状态
                updateStatus(loginTicket.getStuId(), 0);
            }
        }
        return false;
    }

    @Override
    public LoginTicket getLoginTicketByStuId(String stuId){
        return loginTicketMapper.selectByStuId(stuId);
    }
}
