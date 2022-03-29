/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.rabbitmq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * mq的publisher
 *
 * @author fk
 * @version v7.2.0
 * @since v7.2.0
 * 2020-06-15 21:50:52
 */
@Service
public class MessageSenderImpl implements MessageSender {

    @Autowired
    private ApplicationEventPublisher publisher;


    @Override
    public void send(MqMessage message) {

        publisher.publishEvent(message);
    }
}
