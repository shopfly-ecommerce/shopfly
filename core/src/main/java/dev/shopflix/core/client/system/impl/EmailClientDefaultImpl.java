/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.base.model.vo.EmailVO;
import dev.shopflix.core.base.service.EmailManager;
import dev.shopflix.core.client.system.EmailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/13 16:26
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class EmailClientDefaultImpl implements EmailClient {

    @Autowired
    private EmailManager emailManager;

    @Override
    public void sendEmail(EmailVO emailVO) {
        emailManager.sendEmail(emailVO);
    }
}
