/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.email;

import com.enation.app.javashop.consumer.core.event.SendEmailEvent;
import com.enation.app.javashop.core.base.model.vo.EmailVO;
import com.enation.app.javashop.core.client.system.EmailClient;
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
