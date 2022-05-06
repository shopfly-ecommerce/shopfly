/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
