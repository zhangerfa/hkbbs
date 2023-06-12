package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import site.zhangerfa.entity.Message;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 将消息状态改为已读
     * @param id
     * @return
     */
    @Update("update message set status = 1 where id = #{id}")
    int readMessage(int id);
}
