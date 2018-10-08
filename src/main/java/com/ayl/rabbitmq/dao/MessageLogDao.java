package com.ayl.rabbitmq.dao;

import com.ayl.rabbitmq.entity.MessageLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author AYL    2018/10/7 10:11
 */
@Mapper
@Component
public interface MessageLogDao {
    @Insert("insert into message_log(message_id, content, create_time, update_time, last_send_time, status, send_count) " +
            "values(#{messageLog.messageId}, #{messageLog.content}, #{messageLog.createTime}, " +
            "#{messageLog.updateTime}, #{messageLog.lastSendTime}, #{messageLog.status}" +
            ", #{messageLog.sendCount})")
    int insertMessageLog(@Param("messageLog") MessageLog messageLog);

    @Update("update message_log set send_count = send_count + 1, " +
            " update_time = #{updateTime}, last_send_time = #{lastSendTime}" +
            " where id = #{id}")
    int updateRetryInfo(@Param("id") int id,
                        @Param("updateTime") Date updateTime, @Param("lastSendTime") Date lastSendTime);

    @Update("update message_log set status= #{status}, " +
            " update_time = #{updateTime} " +
            " where message_id = #{messageId}")
    int updateStatus(@Param("messageId") String messageId, @Param("status") int status, @Param("updateTime") Date updateTiime);

    @Select("select * from message_log where message_id = #{messageId}")
    MessageLog getByMessageId(@Param("messageId") String messageId);

    @Select("select * from message_log " +
            "where status = #{status} and last_send_time < #{lastSendTime}")
    List<MessageLog> listSendingMessage(@Param("status") int status, @Param("lastSendTime") Date lastSendTime);
}
