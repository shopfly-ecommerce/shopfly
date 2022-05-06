/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.MessageTemplateClient;
import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.service.MessageTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/27 09:44
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class MessageTemplateClientDefaultImpl implements MessageTemplateClient {

    @Autowired
    private MessageTemplateManager messageTemplateManager;

    @Override
    public MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum) {
        return messageTemplateManager.getModel(messageCodeEnum);
    }
}
