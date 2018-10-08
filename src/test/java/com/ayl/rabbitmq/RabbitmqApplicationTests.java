package com.ayl.rabbitmq;

import com.ayl.rabbitmq.entity.OrderInfo;
import com.ayl.rabbitmq.consumer.OrderConsumerService;
import com.ayl.rabbitmq.publisher.OrderSenderService;
import com.ayl.rabbitmq.service.OrderInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private OrderSenderService orderSenderService;

    @Autowired
    private OrderConsumerService orderConsumerService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Test
    public void testSender() throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("201809120001");
        orderInfo.setName("test");
        String messageId = System.currentTimeMillis() + "$" + UUID.randomUUID().toString().replace("-", "");
        orderInfo.setMessageId(messageId);
        orderSenderService.senderOrder(orderInfo);
    }

    @Test
    public void testCreateOrder() throws Exception {
        orderInfoService.createOrderInfo();
    }

}
