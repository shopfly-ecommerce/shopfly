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
package cloud.shopfly.b2c.consumer.shop.member;

import cloud.shopfly.b2c.consumer.core.event.MemberLoginEvent;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员登录后登录次数进行处理
 *
 * @author zh
 * @version v7.0
 * @date 18/4/12 下午5:38
 * @since v7.0
 */
@Service
public class MemberLoginNumConsumer implements MemberLoginEvent {

    @Autowired
    private MemberClient memberClient;

    /**
     * 会员登录后对会员某些字段进行更新 例如 上次登录时间、登录次数
     *
     * @param memberLoginMsg
     */
    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        this.memberClient.updateLoginNum(memberLoginMsg.getMemberId(), DateUtil.getDateline());
    }
}
