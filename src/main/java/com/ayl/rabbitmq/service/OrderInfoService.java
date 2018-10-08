package com.ayl.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.ayl.rabbitmq.constant.MessageStatus;
import com.ayl.rabbitmq.dao.OrderInfoDao;
import com.ayl.rabbitmq.entity.MessageLog;
import com.ayl.rabbitmq.entity.OrderInfo;
import com.ayl.rabbitmq.publisher.OrderSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author AYL    2018/10/7 10:24
 */
@Service
@Transactional
public class OrderInfoService {
    @Autowired
    OrderInfoDao orderInfoDao;

    @Autowired
    MessageLogService messageLogService;

    @Autowired
    OrderSenderService orderSenderService;

    /**
     * 创建并发送消息
     * @throws Exception
     */
    public void createOrderInfo() throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("201809120001");
        orderInfo.setName("test");
        String messageId = System.currentTimeMillis() + "$" + UUID.randomUUID().toString().replace("-", "");
        orderInfo.setMessageId(messageId);

        insert(orderInfo);

        MessageLog messageLog = new MessageLog();
        messageLog.setMessageId(messageId);
        messageLog.setContent(JSON.toJSONString(orderInfo));
        Date now = new Date();
        messageLog.setCreateTime(now);
        messageLog.setUpdateTime(now);
        messageLog.setLastSendTime(now);
        messageLog.setStatus(MessageStatus.SENDING.getStatus());
        messageLog.setSendCount(1);
        messageLogService.insert(messageLog);

        orderSenderService.senderOrder(orderInfo);
    }

    public int insert(OrderInfo orderInfo) throws Exception {
        int result = orderInfoDao.insertOrderInfo(orderInfo);
        if (result < 1) {
            throw new Exception("orderInfo insert failed.");
        }
        return result;
    }
}
