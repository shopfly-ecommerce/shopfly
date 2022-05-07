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
package cloud.shopfly.b2c.core.passport.service;


/**
 * Account management
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-04-8 11:33:56
 */
public interface PassportManager {
    /**
     * Send the registration SMS verification code
     *
     * @param mobile Mobile phone number
     */
    void sendRegisterSmsCode(String mobile);

    /**
     * Send an SMS verification code to retrieve the password
     *
     * @param mobile
     */
    void sendFindPasswordCode(String mobile);

    /**
     * Send the verification code for login SMS
     *
     * @param mobile
     */
    void sendLoginSmsCode(String mobile);


    /**
     * throughrefreshToken To obtainaccessToken
     *
     * @param refreshToken
     * @returna ccessToken
     */
    String exchangeToken(String refreshToken);

    /**
     * Clear tag cache
     *
     * @param mobile Mobile phone number
     * @param scene  The business scenario
     */
    void clearSign(String mobile, String scene);


}
