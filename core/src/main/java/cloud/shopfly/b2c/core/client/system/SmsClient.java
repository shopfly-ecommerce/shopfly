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
 * 发短信客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 上午11:48
 * @since v7.0
 */

public interface SmsClient {
    /**
     * 验证手机验证码
     *
     * @param scene  业务场景
     * @param mobile 手机号码
     * @param code   手机验证码
     * @return 是否通过校验 true通过，false不通过
     */
    boolean valid(String scene, String mobile, String code);


    /**
     * 发送(发送手机短信)消息
     *
     * @param byName    操作，替换内容
     * @param mobile    手机号码
     * @param sceneType 操作类型
     */
    void sendSmsMessage(String byName, String mobile, SceneType sceneType);


    /**
     * 真实发送手机短信
     *
     * @param smsSendVO
     */
    void send(SmsSendVO smsSendVO);

}
