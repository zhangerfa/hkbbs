package site.zhangerfa.event;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import site.zhangerfa.dao.NoticeMapper;
import site.zhangerfa.entity.Notice;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.service.LikeService;

/**
 * 事件消费者，用于消费用户点赞，评论，关注等事件，将事件信息转换为站内通知
 */
@Component
public class NoticeConsumer {
    private static final Logger logger = LoggerFactory.getLogger(NoticeConsumer.class);
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private LikeService likeService;

    /**
     * 发布评论通知
     * @param record 事件相关信息
     */
    @KafkaListener(topics = {Constant.NOTICE_TYPE_COMMENT})
    public void addCommentNotice(ConsumerRecord<String, String> record){
        // 将传入的json字符串转换为Notice对象
        Notice notice = getNotice(record);
        // 发布消息
        if (notice != null) noticeMapper.insert(notice);
    }

    /**
     * 发布评论通知
     * @param record 事件相关信息
     */
    @KafkaListener(topics = {Constant.NOTICE_TYPE_LIKE})
    public void addLikeNotice(ConsumerRecord<String, String> record){
        // 将传入的json字符串转换为Notice对象
        Notice notice = getNotice(record);
        if (notice == null) return;
        // 取消点赞不通知
        int likedStatus = likeService.getLikeStatus(notice.getActionUserId(), notice.getEntityType(), notice.getEntityId());
        if (likedStatus == 0) return;
        // 如果点赞的是自己的实体，不通知
        if (notice.getActionUserId().equals(notice.getReceivingUserId())) return;
        // 如果已有通知，不通知
        Notice saveNotice = noticeMapper.selectOne(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getActionUserId, notice.getActionUserId())
                .eq(Notice::getEntityType, notice.getEntityType())
                .eq(Notice::getEntityId, notice.getEntityId())
                .eq(Notice::getActionType, notice.getActionType()));
        if (saveNotice != null) return;
        // 发布消息
        noticeMapper.insert(notice);
    }

    private Notice getNotice(ConsumerRecord<String, String> record){
        // 判断消息是否为空
        if (record == null || record.value() == null){
            logger.error("消息为空");
            return null;
        }
        // 将传入的json字符串转换为Notice对象
        Notice notice = JSON.parseObject(record.value(), Notice.class);
        if (notice == null){
            logger.error("消息格式错误");
            return null;
        }
        return notice;
    }
}
