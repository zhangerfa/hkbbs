package site.zhangerfa.event;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.Constant.Constant;

@Component
public class EventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Resource
    private NoticeService noticeService;

    /**
     * 发布系统通知，当用户点赞，评论，关注其他人时触发
     * @param record 事件相关信息
     */
    @KafkaListener(topics = {Constant.TOPIC_COMMENT,
                             Constant.TOPIC_FOLLOW, Constant.TOPIC_LIKE})
    public void releaseNote(ConsumerRecord record){
        if (record == null || record.value() == null){
            logger.error("消息为空");
            return;
        }
        // 生产者将事件信息以JSON对象发送，转换为notice对象
        Notice notice = JSON.parseObject(record.value().toString(), Notice.class);
        if (notice == null){
            logger.error("消息格式错误");
            return;
        }
        // 调用业务层对象，依据事件信息将站内通知发送给特定用户
        noticeService.add(notice); // 记录通知信息
    }
}
