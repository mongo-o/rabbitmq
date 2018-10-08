package com.ayl.rabbitmq.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AYL    2018/10/7 10:03
 */
@Getter
@Setter
@ToString(callSuper = true)
public class MessageLog implements Serializable {
    private static final long serialVersionUID = 5044022013691915384L;
    /**
     * 自增长id
     */
    private int id;
    private String messageId;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Date lastSendTime;
    private int status;
    private int sendCount;
}
