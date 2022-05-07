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
package cloud.shopfly.b2c.consumer.shop.message;

import cloud.shopfly.b2c.consumer.core.event.MemberMessageEvent;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.client.member.MemberNoticeLogClient;
import cloud.shopfly.b2c.core.client.system.MessageClient;
import cloud.shopfly.b2c.core.system.model.dos.Message;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Member station messagesconsumer
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years10month14The morning of11:27:15
 */
@Component
public class MemberMessageConsumer implements MemberMessageEvent {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private MemberNoticeLogClient memberNoticeLogClient;

    @Autowired
    private MemberClient memberClient;


    @Override
    public void memberMessage(int messageId) {
        Message message = messageClient.get(messageId);
        if (message != null) {
            // Send type 0 Full station 1 part
            Integer sendType = message.getSendType();
            List<String> memberIdsRes;
            String memberIds;
            if (sendType != null && sendType.equals(0)) {
                memberIds =  memberClient.queryAllMemberIds();
            } else {
                memberIds = message.getMemberIds();
            }
            String[] memberIdsArray = memberIds.split(",");
            memberIdsRes = Arrays.asList(memberIdsArray);
            if (memberIdsRes.size() > 0) {
                String msgContent = message.getContent();
                for (String id : memberIdsRes) {
                    memberNoticeLogClient.add(msgContent, message.getSendTime(), StringUtil.toInt(id, false), message.getTitle());
                }
            }
        }
    }
}
