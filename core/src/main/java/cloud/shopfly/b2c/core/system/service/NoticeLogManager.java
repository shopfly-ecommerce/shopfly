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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.NoticeLogDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Message business layer in store station
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-10 10:21:45
 */
public interface NoticeLogManager {

    /**
     * Query the message list in the store
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param type     type
     * @param isRead   1 Have read,0 unread
     * @return Page
     */
    Page list(int page, int pageSize, String type, Integer isRead);

    /**
     * Add store site messages
     *
     * @param shopNoticeLog Store station message
     * @return ShopNoticeLog Store station message
     */
    NoticeLogDO add(NoticeLogDO shopNoticeLog);

    /**
     * Deleting Historical Messages
     *
     * @param ids
     */
    void delete(Integer[] ids);

    /**
     * Setup has been read
     *
     * @param ids The messageid
     */
    void read(Integer[] ids);

}
