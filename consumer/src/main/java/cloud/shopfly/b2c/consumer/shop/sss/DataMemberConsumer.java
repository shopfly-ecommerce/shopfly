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
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.consumer.core.event.MemberRegisterEvent;
import cloud.shopfly.b2c.core.base.message.MemberRegisterMsg;
import cloud.shopfly.b2c.core.client.statistics.MemberDataClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Member data collection consumption
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/20 In the afternoon2:20
 */
@Service
public class DataMemberConsumer implements MemberRegisterEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MemberDataClient memberDataClient;

    /**
     * Registered members
     *
     * @param memberRegisterMsg
     */
    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {

        try {
            this.memberDataClient.register(memberRegisterMsg.getMember());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("The member registration message is abnormal:",e);
        }
    }
}
