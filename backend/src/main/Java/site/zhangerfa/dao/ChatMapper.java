package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import site.zhangerfa.pojo.Chat;

@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
    /**
     * 返回聊天id，如果两个人未聊天则返回null
     * @param fromId
     * @param toId
     * @return
     */
    @Select("select id from chat where " +
            "(user1 = #{fromId} and user2 = #{toId}) " +
            "or (user1 = #{toId} and user2 = #{fromId})")
    Integer selectChatId(String fromId, String toId);
}
