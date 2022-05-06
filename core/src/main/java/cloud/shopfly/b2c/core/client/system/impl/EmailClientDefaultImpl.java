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

import cloud.shopfly.b2c.core.client.system.EmailClient;
import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.base.service.EmailManager;
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
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class EmailClientDefaultImpl implements EmailClient {

    @Autowired
    private EmailManager emailManager;

    @Override
    public void sendEmail(EmailVO emailVO) {
        emailManager.sendEmail(emailVO);
    }
}
