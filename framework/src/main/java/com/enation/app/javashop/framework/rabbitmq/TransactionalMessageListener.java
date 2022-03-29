/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 对ApplicationEventPublisher的publishEvent的监听，默认在事务提交后执行
 *
 * @author fk
 * @version v7.2.0
 * @since v7.2.0
 * 2020-06-15 21:50:52
 */
@Component
public class TransactionalMessageListener {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 默认在事务提交后执行
     * @param message
     */
    @TransactionalEventListener(fallbackExecution = true)
    public void handleSupplierBillPush(MqMessage message){

        this.amqpTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());

    }
}
