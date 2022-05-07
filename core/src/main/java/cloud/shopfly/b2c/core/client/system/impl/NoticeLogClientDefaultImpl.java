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
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.NoticeLogClient;
import cloud.shopfly.b2c.core.system.model.dos.NoticeLogDO;
import cloud.shopfly.b2c.core.system.service.NoticeLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description: Stores the message
 * @date 2018/8/14 10:22
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class NoticeLogClientDefaultImpl implements NoticeLogClient {


    @Autowired
    private NoticeLogManager noticeLogManager;

    @Override
    public NoticeLogDO add(NoticeLogDO shopNoticeLog) {
        return noticeLogManager.add(shopNoticeLog);
    }
}
