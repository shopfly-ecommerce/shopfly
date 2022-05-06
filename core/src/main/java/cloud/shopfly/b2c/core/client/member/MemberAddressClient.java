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
package cloud.shopfly.b2c.core.client.member;

import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;

/**
 * 会员地址客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午3:51
 * @since v7.0
 */

public interface MemberAddressClient {

    /**
     * 获取会员地址
     *
     * @param id 会员地址主键
     * @return MemberAddress  会员地址
     */
    MemberAddress getModel(Integer id);

    /**
     * 获取会员默认地址
     *
     * @param memberId 会员id
     * @return 会员默认地址
     */
    MemberAddress getDefaultAddress(Integer memberId);
}
