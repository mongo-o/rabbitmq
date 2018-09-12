package com.ayl.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author AYL    2018/9/12 10:28
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Order implements Serializable {
    private static final long serialVersionUID = 5011886824240620262L;
    private String id;
    private String name;
    private String messageId;
}
