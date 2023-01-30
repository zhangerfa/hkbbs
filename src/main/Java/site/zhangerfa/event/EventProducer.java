package site.zhangerfa.event;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Notice;

@Component
public class EventProducer {
    @Resource
    private KafkaTemplate kafkaTemplate; // SpringBoot封装的信息发布对象

    /**
     * 启动事件：将事件发布到消息队列中
     * @param event 事件相关信息，将转换为JSON字符串
     */
    public void fireEvent(Event event){
        kafkaTemplate.send(event.getTopic(), JSON.toJSON(event).toString());
    }

    /**
     * 增加一条主题的通知
     * @param topic
     * @param notice
     */
    public void addNotice(String topic, Notice notice){
        kafkaTemplate.send(topic, JSON.toJSON(notice).toString());
    }
}
