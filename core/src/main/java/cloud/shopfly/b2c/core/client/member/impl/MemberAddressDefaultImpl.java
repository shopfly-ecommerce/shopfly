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
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberAddressClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.member.service.MemberAddressManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Member address default implementation
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 In the afternoon3:54
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class MemberAddressDefaultImpl implements MemberAddressClient {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @Override
    public MemberAddress getModel(Integer id) {
        return memberAddressManager.getModel(id);
    }

    @Override
    public MemberAddress getDefaultAddress(Integer memberId) {
        return memberAddressManager.getDefaultAddress(memberId);
    }
}
