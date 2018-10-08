package com.ayl.rabbitmq.consumer;

import com.ayl.rabbitmq.entity.OrderInfo;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
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
    private static Logger logger = LogManager.getLogger();


    @RabbitListener(bindings = @QueueBinding(
                    value = @Queue(name = "order-queue", durable = "true"),
                    exchange = @Exchange(name = "order-exchange", durable = "true", type = "topic"),
                    key = "order.*"
            )
    )
    @RabbitHandler
    public void receiveMessage(@Payload OrderInfo orderInfo, Channel channel, @Headers Map<String,Object> headers) {
        logger.debug("----------接受到消息------------------");
        logger.debug("----------orderid：" + orderInfo.getId());
        //标识这条消息已经被消费
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
