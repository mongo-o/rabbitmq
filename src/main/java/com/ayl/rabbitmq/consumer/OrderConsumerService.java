package com.ayl.rabbitmq.consumer;

import com.ayl.dao.Order;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author AYL    2018/9/12 11:09
 */
@Service
public class OrderConsumerService {


    @RabbitListener(bindings = @QueueBinding(
                    value = @Queue(name = "order-queue", durable = "true"),
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),
                    key = "order.*"
            )
    )
    @RabbitHandler
    public void receiveMessage(@Payload Order order, Channel channel,@Headers Map<String,Object> headers) {
        System.out.println("----------接受到消息------------------");
        System.out.println("----------orderid：" + order.getId());
        //标识这条消息已经被消费
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
