/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.message;

import dev.shopflix.consumer.core.event.SmsSendMessageEvent;
import com.enation.app.javashop.core.base.model.vo.SmsSendVO;
import com.enation.app.javashop.core.client.system.SmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送短信
 *
 * @author zjp
 * @version v7.0
 * @since v7.0
 * 2018年3月25日 下午3:15:01
 */
@Component
public class SmsMessageConsumer implements SmsSendMessageEvent {

    @Autowired
    private SmsClient smsClient;

    @Override
    public void send(SmsSendVO smsSendVO) {
        smsClient.send(smsSendVO);
    }
}
