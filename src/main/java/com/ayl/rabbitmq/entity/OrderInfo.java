package com.ayl.rabbitmq.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author AYL    2018/9/12 10:28
 */
@Getter
@Setter
@ToString(callSuper = true)
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 3123255520994259592L;
    private String id;
    private String name;
    private String messageId;
}
