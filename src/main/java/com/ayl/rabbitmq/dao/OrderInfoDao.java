package com.ayl.rabbitmq.dao;

import com.ayl.rabbitmq.entity.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author AYL    2018/10/7 10:06
 */
@Mapper
@Component
public interface OrderInfoDao {
    @Insert("insert into order_info(id,message_id,name) " +
            "values(#{orderInfo.id}, #{orderInfo.messageId}, #{orderInfo.name})")
    int insertOrderInfo(@Param("orderInfo")OrderInfo orderInfo);
}
