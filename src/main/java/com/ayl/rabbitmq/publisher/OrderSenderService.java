package com.ayl.rabbitmq.publisher;

import com.ayl.rabbitmq.entity.OrderInfo;
import com.ayl.rabbitmq.service.MessageLogService;
import com.ayl.rabbitmq.service.OrderInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author AYL    2018/9/12 10:19
 */
@Component
public class OrderSenderService {
    private static final Logger logger = LogManager.getLogger();

    //自动配置，类似于RedisTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageLogService messageLogService;

    final RabbitTemplate.ConfirmCallback confirmCallBack = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            logger.debug("===CorrelationData:" + correlationData);
            String messageId = correlationData.getId();
            if (ack) {

                //ack为true表明消息发送成功, 否则发生失败
                messageLogService.updateStatusAfterSuccess(messageId);
            } else {
                System.out.println("消息发生失败，异常处理。。。");
            }
        }
    };

    public void senderOrder(OrderInfo orderInfo) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallBack);

        CorrelationData correlationData = new CorrelationData(orderInfo.getMessageId());
        //调用template的convertAndSend发生数据。
        rabbitTemplate.convertAndSend("order-exchange", "order.*", orderInfo, correlationData);
        logger.debug("=========消息发送完成================");
    }
}
