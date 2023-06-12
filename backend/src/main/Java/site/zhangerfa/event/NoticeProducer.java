package site.zhangerfa.event;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import site.zhangerfa.entity.Notice;

/**
 * 事件生产者，用于发布系统通知
 */
@Component
public class NoticeProducer {
    // SpringBoot封装的信息发布对象
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 增加一条主题的通知
     * @param topic
     * @param notice
     */
    public void addNotice(String topic, Notice notice){
        kafkaTemplate.send(topic, JSON.toJSON(notice).toString());
    }
}
