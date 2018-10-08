package com.ayl.rabbitmq.constant;

/**
 * @author AYL    2018/10/7 11:03
 */
public enum MessageStatus {
    SENDING(0),
    SUCCESS(1),
    FAILURE(2);

    int status;

    MessageStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
