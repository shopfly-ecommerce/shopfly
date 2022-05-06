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
package cloud.shopfly.b2c.consumer.core.event;

import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;

/**
 * 会员注册事件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:22:56
 */
public interface MemberRegisterEvent {

    /**
     * 会员注册
     *
     * @param memberRegisterMsg
     */
    void memberRegister(MemberRegisterMsg memberRegisterMsg);
}
