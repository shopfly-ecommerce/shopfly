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
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;

/**
 * SMS client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 In the morning11:48
 * @since v7.0
 */

public interface SmsClient {
    /**
     * Verify the mobile phone verification code
     *
     * @param scene  The business scenario
     * @param mobile Mobile phone number
     * @param code   Mobile verification code
     * @return Whether it passes the verificationtrueThrough the,falseNot through
     */
    boolean valid(String scene, String mobile, String code);


    /**
     * send(send手机短信)The message
     *
     * @param byName    Action, replace content
     * @param mobile    Mobile phone number
     * @param sceneType Operation type
     */
    void sendSmsMessage(String byName, String mobile, SceneType sceneType);


    /**
     * Send actual SMS messages
     *
     * @param smsSendVO
     */
    void send(SmsSendVO smsSendVO);

}
