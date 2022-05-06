/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;

/**
 * @version v7.0
 * @Description: 消息模版client
 * @Author: zjp
 * @Date: 2018/7/27 09:42
 */
public interface MessageTemplateClient {
    /**
     * 获取消息模版
     * @param messageCodeEnum 消息模版编码
     * @return MessageTemplateDO  消息模版
     */
    MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum);

}
