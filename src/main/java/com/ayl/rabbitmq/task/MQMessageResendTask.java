package com.ayl.rabbitmq.task;

import com.alibaba.fastjson.JSON;
import com.ayl.rabbitmq.constant.Constant;
import com.ayl.rabbitmq.entity.MessageLog;
import com.ayl.rabbitmq.entity.OrderInfo;
import com.ayl.rabbitmq.publisher.OrderSenderService;
import com.ayl.rabbitmq.service.MessageLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AYL    2018/10/7 18:19
 */
@Component
public class MQMessageResendTask {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private MessageLogService messageLogService;

    @Autowired
    private OrderSenderService orderSenderService;

    @Scheduled(initialDelay = 1000, fixedRate = 100)
    public void reSend() {
        logger.debug("==========定时任务开始================");
        List<MessageLog> messageLogList = messageLogService.listSendingMessage();

        messageLogList.forEach(
                (item) -> {
                    if (item.getSendCount() == Constant.MQ_MESSAGE_MAX_RETRIES) {
                        messageLogService.updateStatusAfterFailed(item.getMessageId());
                    } else {
                        String orderInfoJson = item.getContent();
                        OrderInfo orderInfo = JSON.parseObject(orderInfoJson, OrderInfo.class);
                        try {
                            messageLogService.updateRetryInfo(item.getId());
                            orderSenderService.senderOrder(orderInfo);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
        );
    }
}
