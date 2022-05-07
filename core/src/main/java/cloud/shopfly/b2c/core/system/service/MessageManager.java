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

import cloud.shopfly.b2c.core.system.model.dos.Message;
import cloud.shopfly.b2c.core.system.model.vo.MessageVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * The intra-site message business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
public interface MessageManager {

    /**
     * Query the intra-station message list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add intra-site messages
     *
     * @param messageVO Messages
     * @return Message Messages
     */
    Message add(MessageVO messageVO);

    /**
     * throughidQuery intra-station messages
     *
     * @param id The messageid
     * @return Intra-site message object
     */
    Message get(Integer id);
}
