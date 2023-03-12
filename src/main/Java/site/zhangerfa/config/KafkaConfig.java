package site.zhangerfa.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {
    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("comment")// 指定主题名称
                .partitions(30) // 指定分区数量，这个数量通常要大于消费者的数量，按消费者线程数计算
                .replicas(2) // 指定副本数量
                .compact()
                .build();
    }
}
