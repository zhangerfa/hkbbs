package site.zhangerfa.event;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private String topic; // 主题
    private String stuId; // 触发事件人的学号
    private int entityType; // 事件指向实体的实体类型
    private int entityId; // 事件指向实体的id
    private Map<String, Object> data = new HashMap<>(); // 事件相关数据

    /**
     * 增加事件相关数据
     */
    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
