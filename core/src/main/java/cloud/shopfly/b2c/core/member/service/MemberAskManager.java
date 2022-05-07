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

import cloud.shopfly.b2c.core.member.model.dos.MemberAsk;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Consulting business layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-04 17:41:18
 */
public interface MemberAskManager {

    /**
     * Query query list
     *
     * @param param Query conditions
     * @return Page
     */
    Page list(CommentQueryParam param);

    /**
     * Add consulting
     *
     * @param askContent consulting
     * @param goodsId    productid
     * @return MemberAsk consulting
     */
    MemberAsk add(String askContent, Integer goodsId);

    /**
     * Delete the consulting
     *
     * @param id Consulting the primary key
     */
    void delete(Integer id);

    /**
     * Get advice
     *
     * @param id Consulting the primary key
     * @return MemberAsk  consulting
     */
    MemberAsk getModel(Integer id);

    /**
     * Reply to consulting
     *
     * @param replyContent
     * @param askId
     * @return
     */
    MemberAsk reply(String replyContent, Integer askId);

    /**
     * The seller gets the number of unanswered inquiries
     *
     * @return
     */
    Integer getNoReplyCount();

    /**
     * The management end reviews member consultation information
     *
     * @param askId
     * @param authStatus WAIT_AUDIT("To audit"),PASS_AUDIT("approved"),REFUSE_AUDIT("Audit refused to");
     */
    void auth(Integer askId, String authStatus);
}
