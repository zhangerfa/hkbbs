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

/**
 * 事件消费者，用于消费用户点赞，评论，关注等事件，将事件信息转换为站内通知
 */
@Component
public class NoticeConsumer {
    private static final Logger logger = LoggerFactory.getLogger(NoticeConsumer.class);
    @Resource
    private NoticeService noticeService;

    /**
     * 发布系统通知，当用户点赞，评论，关注其他人时，将事件信息转换为站内通知
     * @param record 事件相关信息
     */
    @KafkaListener(topics = {Constant.TOPIC_COMMENT,
                             Constant.TOPIC_FOLLOW, Constant.TOPIC_LIKE})
    public void releaseNote(ConsumerRecord<String, String> record){
        // 判断消息是否为空
        if (record == null || record.value() == null){
            logger.error("消息为空");
            return;
        }
        // 将传入的json字符串转换为Notice对象
        Notice notice = JSON.parseObject(record.value(), Notice.class);
        if (notice == null){
            logger.error("消息格式错误");
            return;
        }
        // 发布消息
        noticeService.add(notice);
    }
}
