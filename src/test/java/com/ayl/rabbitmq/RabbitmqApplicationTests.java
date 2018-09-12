package com.ayl.rabbitmq;

import com.ayl.rabbitmq.consumer.OrderConsumerService;
import com.ayl.rabbitmq.publisher.OrderSenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testSender() {
        orderSenderService.senderOrder();
    }

}
