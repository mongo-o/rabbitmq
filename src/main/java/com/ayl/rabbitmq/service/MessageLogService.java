package com.ayl.rabbitmq.service;

import com.ayl.rabbitmq.dao.MessageLogDao;
import com.ayl.rabbitmq.entity.MessageLog;
import com.ayl.rabbitmq.constant.MessageStatus;
import com.ayl.rabbitmq.util.CaculateLastSendTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author AYL    2018/10/7 10:32
 */
@Service
public class MessageLogService {

    @Autowired
    private MessageLogDao messageLogDao;

    public int insert(MessageLog messageLog) throws Exception {
        int result = messageLogDao.insertMessageLog(messageLog);
        if (result <= 0) {
            throw new Exception("insert messagelog failed, messageid:" + messageLog.getMessageId());
        }
        return result;
    }

    public int updateRetryInfo(int primaryKeyId) {
        Date now = new Date();
        int result = messageLogDao.updateRetryInfo(primaryKeyId, now, now);
        return result;
    }

    /**
     * 接受到消息Ack通知后修改消息发送记录的状态
     * @param messageId
     * @return
     */
    public int updateStatusAfterSuccess( String messageId) {
        int result = messageLogDao.updateStatus(messageId, MessageStatus.SUCCESS.getStatus(), new Date());
        return result;
    }

    public int updateStatusAfterFailed(String messageId) {
        int result = messageLogDao.updateStatus(messageId, MessageStatus.FAILURE.getStatus(), new Date());
        return result;
    }

    /**
     * 查询仍然在发送中的消息列表
     * @return
     */
    public List<MessageLog> listSendingMessage() {
        Date lastSendTime = CaculateLastSendTime.caculateLastSendTime();
        List<MessageLog> messageLogs = messageLogDao.listSendingMessage(MessageStatus.SENDING.getStatus(), lastSendTime);
        return messageLogs;
    }
}
