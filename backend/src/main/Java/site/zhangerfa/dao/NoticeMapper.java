package site.zhangerfa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import site.zhangerfa.entity.Notice;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    /**
     * 将传入 id 对应的通知标记为已读
     * @param id
     * @return
     */
    @Update("update notice set status = 1 where id = #{id}")
    int updateStatusById(int id);
}
