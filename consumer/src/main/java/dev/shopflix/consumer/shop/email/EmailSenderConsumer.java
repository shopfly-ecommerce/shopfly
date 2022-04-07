/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.email;

import dev.shopflix.consumer.core.event.SendEmailEvent;
import dev.shopflix.core.base.model.vo.EmailVO;
import dev.shopflix.core.client.system.EmailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-24
 */
@Service
public class EmailSenderConsumer implements SendEmailEvent {

    @Autowired
    private EmailClient emailClient;

    @Override
    public void sendEmail(EmailVO emailVO) {
        emailClient.sendEmail(emailVO);
    }
}