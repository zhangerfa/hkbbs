package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.pojo.LoginTicket;

import java.util.Date;

@Service
public class LoginTicketImpl implements LoginTicketService {
    @Resource
    private LoginTicketMapper loginTicketMapper;

    @Override
    public boolean add(LoginTicket loginTicket) {
        return loginTicketMapper.insert(loginTicket) != 0;
    }

    @Override
    public LoginTicket getLoginTicketByTicket(String ticket) {
        if (ticket == null) return null;
        return loginTicketMapper.selectOne(
                new LambdaQueryWrapper<LoginTicket>().
                        eq(LoginTicket::getTicket, ticket));
    }

    @Override
    public boolean update(LoginTicket loginTicket) {
        return loginTicketMapper.updateById(loginTicket) != 0;
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
                loginTicket.setStatus(0);
                update(loginTicket);
            }
        }
        return false;
    }

    @Override
    public LoginTicket getLoginTicketByStuId(String stuId){
        return loginTicketMapper.selectOne(
                new LambdaQueryWrapper<LoginTicket>()
                        .eq(LoginTicket::getStuId, stuId));
    }
}
