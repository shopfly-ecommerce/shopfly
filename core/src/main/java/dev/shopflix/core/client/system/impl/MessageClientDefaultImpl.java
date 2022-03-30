/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.client.system.MessageClient;
import dev.shopflix.core.system.model.dos.Message;
import dev.shopflix.core.system.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description: 站内消息
 * @date 2018/8/14 10:15
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class MessageClientDefaultImpl implements MessageClient {

    @Autowired
    private MessageManager messageManager;

    @Override
    public Message get(Integer id) {

        return messageManager.get(id);
    }
}
