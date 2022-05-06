/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 短信实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/31 上午11:13
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class SmsClientDefaultImpl implements SmsClient {

    @Autowired
    private SmsManager smsManager;


    @Override
    public boolean valid(String scene, String mobile, String code) {
        return smsManager.valid(scene, mobile, code);
    }

    @Override
    public void sendSmsMessage(String byName, String mobile, SceneType sceneType) {
        this.smsManager.sendSmsMessage(byName, mobile, sceneType);
    }

    @Override
    public void send(SmsSendVO smsSendVO) {
        smsManager.send(smsSendVO);
    }
}
