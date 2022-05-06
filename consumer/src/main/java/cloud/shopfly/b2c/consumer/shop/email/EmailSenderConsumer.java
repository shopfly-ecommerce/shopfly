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
package cloud.shopfly.b2c.consumer.shop.email;

import cloud.shopfly.b2c.consumer.core.event.SendEmailEvent;
import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.client.system.EmailClient;
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
