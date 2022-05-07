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
package cloud.shopfly.b2c.core.member.service;

/**
 * Member Security Business
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 In the afternoon3:16
 * @since v7.0
 */
public interface MemberSecurityManager {

    /**
     * Send the verification code bound to the mobile phone number
     *
     * @param mobile
     */
    void sendBindSmsCode(String mobile);

    /**
     * Send the mobile phone verification code
     *
     * @param mobile
     */
    void sendValidateSmsCode(String mobile);

    /**
     * Change the password
     *
     * @param memberId The userid
     * @param password Password
     */
    void updatePassword(Integer memberId, String password);

    /**
     * Mobile phone binding
     *
     * @param mobile
     */
    void bindMobile(String mobile);

    /**
     * Mobile phone replacement binding
     *
     * @param mobile
     */
    void changeBindMobile(String mobile);


}
