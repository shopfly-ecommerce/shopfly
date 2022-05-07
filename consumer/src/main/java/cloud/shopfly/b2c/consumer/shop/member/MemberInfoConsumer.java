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

import cloud.shopfly.b2c.consumer.core.event.MemberInfoChangeEvent;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.member.MemberCommentClient;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Member information consumer
 *
 * @author zh
 * @version v7.0
 * @date 18/12/26 In the afternoon4:39
 * @since v7.0
 */
@Component
public class MemberInfoConsumer implements MemberInfoChangeEvent {

    @Autowired
    private MemberCommentClient memberCommentClient;
    @Autowired
    private MemberClient memberClient;

    @Override
    public void memberInfoChange(Integer memberId) {
        // Obtaining User information
        Member member = memberClient.getModel(memberId);
        // Example Modify user comment information
        memberCommentClient.editComment(member.getMemberId(), member.getFace());

    }
}
