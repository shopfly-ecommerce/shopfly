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

import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * Comment reply business layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 16:34:50
 */
public interface CommentReplyManager {

    /**
     * Query the comment reply list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Add a comment to reply
     *
     * @param commentReply Comment back
     * @return CommentReply Comment back
     */
    CommentReply add(CommentReply commentReply);

    /**
     * Modify comment reply
     *
     * @param commentReply Comment back
     * @param id           Comment reply primary key
     * @return CommentReply Comment back
     */
    CommentReply edit(CommentReply commentReply, Integer id);

    /**
     * Delete comment reply
     *
     * @param id Comment reply primary key
     */
    void delete(Integer id);

    /**
     * Get a comment reply
     *
     * @param id Comment reply primary key
     * @return CommentReply  Comment back
     */
    CommentReply getModel(Integer id);

    /**
     * Query comments for relevant responses
     *
     * @param commentIds
     * @return
     */
    Map<Integer, CommentReply> getReply(List<Integer> commentIds);

    /**
     * Reply to comment
     *
     * @param commentId
     * @param reply
     * @param permission
     * @return
     */
    CommentReply replyComment(Integer commentId, String reply, Permission permission);
}
