drop table if exists rabbitmqSendLog.`order_info`;
CREATE TABLE rabbitmqSendLog.`order_info` (
  `id` varchar(32) NOT NULL,
  `name` varchar(1024) DEFAULT NULL,
  `message_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists rabbitmqSendLog.`message_log`;
CREATE TABLE rabbitmqSendLog.`message_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(64) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `send_count` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `last_send_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='MQ 消息发送记录';
