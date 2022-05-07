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

import cloud.shopfly.b2c.core.member.model.dos.MemberNoticeLog;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Member station message history business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */
public interface MemberNoticeLogManager {

    /**
     * Query the message history list of member sites
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param read     Have read,1read0unread
     * @return Page
     */
    Page list(int page, int pageSize, Integer read);

    /**
     * Add member site message history
     *
     * @param content  The message content
     * @param sendTime Send time
     * @param memberId membersid
     * @param title     title
     * @return Message history
     */
    MemberNoticeLog add(String content, long sendTime, Integer memberId, String title);

    /**
     * Setup has been read
     *
     * @param ids The messageid
     */
    void read(Integer[] ids);

    /**
     * Deleting Historical Messages
     *
     * @param ids
     */
    void delete(Integer[] ids);

}
