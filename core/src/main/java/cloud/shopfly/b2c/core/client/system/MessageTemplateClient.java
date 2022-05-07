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

import cloud.shopfly.b2c.core.system.enums.MessageCodeEnum;
import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;

/**
 * @version v7.0
 * @Description: The message templateclient
 * @Author: zjp
 * @Date: 2018/7/27 09:42
 */
public interface MessageTemplateClient {
    /**
     * Get the message template
     * @param messageCodeEnum Message template encoding
     * @return MessageTemplateDO  The message template
     */
    MessageTemplateDO getModel(MessageCodeEnum messageCodeEnum);

}
