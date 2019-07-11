package com.recse4cloud.core.config.mq;

import lombok.Data;

import javax.jms.Queue;

@Data
public class QueueMessage {
    /**
     * 队列
     */
    private Queue queue;
    /**
     * 时间，格式yyyyMMddHHmmss
     */
    private String createtime;
}
