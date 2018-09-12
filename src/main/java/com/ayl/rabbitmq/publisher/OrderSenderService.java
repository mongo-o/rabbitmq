package com.ayl.rabbitmq.publisher;

import com.ayl.dao.Order;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author AYL    2018/9/12 10:19
 */
@Service
public class OrderSenderService {

    //自动配置，类似于RedisTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void senderOrder() {
        Order order = new Order();
        order.setId("201809120001");
        order.setName("test");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString().replace("-", ""));

        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        //调用template的convertAndSend发生数据。
        rabbitTemplate.convertAndSend("order-exchange", "order.*", order, correlationData);
    }


}
