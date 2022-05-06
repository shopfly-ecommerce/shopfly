/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.CaptchaClient;
import cloud.shopfly.b2c.core.base.service.CaptchaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 验证码默认实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/31 上午10:51
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class CaptchaDefaultImpl implements CaptchaClient {

    @Autowired
    private CaptchaManager captchaManager;

    @Override
    public boolean valid(String uuid, String code, String scene) {
        return this.captchaManager.valid(uuid, code, scene);
    }

    @Override
    public void deleteCode(String uuid, String code, String scene) {
        this.captchaManager.deleteCode(uuid, code, scene);
    }
}
