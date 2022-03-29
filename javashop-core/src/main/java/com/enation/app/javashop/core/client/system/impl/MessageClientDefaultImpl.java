/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system.impl;

import com.enation.app.javashop.core.client.system.MessageClient;
import com.enation.app.javashop.core.system.model.dos.Message;
import com.enation.app.javashop.core.system.service.MessageManager;
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
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class MessageClientDefaultImpl implements MessageClient {

    @Autowired
    private MessageManager messageManager;

    @Override
    public Message get(Integer id) {

        return messageManager.get(id);
    }
}
