package com.recse4cloud.core.config.mq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.JMSException;
import javax.jms.Queue;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String QUEUE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(QueueMessage message) {
        if (message == null) {
            return;
        }
        Queue queue = message.getQueue();
        if (queue == null) {
            logger.info(JSON.toJSONString(message));
            return;
        }
        String queueName = null;
        try {
            queueName = queue.getQueueName();
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
        message.setQueue(null);
        message.setCreatetime(time());
        String json = JSON.toJSONString(message);
        logger.info(queueName + "\t==" + json);
        jmsMessagingTemplate.convertAndSend(queue, json);
    }

    private String time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(QUEUE_TIME_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }
}
