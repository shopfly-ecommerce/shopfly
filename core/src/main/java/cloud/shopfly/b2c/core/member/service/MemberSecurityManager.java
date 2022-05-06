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
 * 会员安全业务
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 下午3:16
 * @since v7.0
 */
public interface MemberSecurityManager {

    /**
     * 发送绑定手机号码的验证码
     *
     * @param mobile
     */
    void sendBindSmsCode(String mobile);

    /**
     * 发送手机验证验证码
     *
     * @param mobile
     */
    void sendValidateSmsCode(String mobile);

    /**
     * 修改密码
     *
     * @param memberId 用户id
     * @param password 密码
     */
    void updatePassword(Integer memberId, String password);

    /**
     * 手机绑定
     *
     * @param mobile
     */
    void bindMobile(String mobile);

    /**
     * 手机更换绑定
     *
     * @param mobile
     */
    void changeBindMobile(String mobile);


}
