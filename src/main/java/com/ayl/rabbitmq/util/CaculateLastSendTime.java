package com.ayl.rabbitmq.util;

import com.ayl.rabbitmq.constant.Constant;

import java.util.Calendar;
import java.util.Date;

/**
 * @author AYL    2018/10/7 16:03
 */
public class CaculateLastSendTime {

    /**
     * 计算距离当前时间往前 Constant.MQ_MESSAGE_RETRY_INTERVAL_MINS 分钟的具体时间
     * @return
     */
    public static Date caculateLastSendTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1 * Constant.MQ_MESSAGE_RETRY_INTERVAL_MINS);
        return calendar.getTime();
    }
}
