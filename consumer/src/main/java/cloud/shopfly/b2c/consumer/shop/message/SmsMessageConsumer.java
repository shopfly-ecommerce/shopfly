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
package cloud.shopfly.b2c.consumer.shop.message;

import cloud.shopfly.b2c.consumer.core.event.SmsSendMessageEvent;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.client.system.SmsClient;
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
