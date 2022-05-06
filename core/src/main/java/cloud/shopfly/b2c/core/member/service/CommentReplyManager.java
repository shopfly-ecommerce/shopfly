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
 * 评论回复业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 16:34:50
 */
public interface CommentReplyManager {

    /**
     * 查询评论回复列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加评论回复
     *
     * @param commentReply 评论回复
     * @return CommentReply 评论回复
     */
    CommentReply add(CommentReply commentReply);

    /**
     * 修改评论回复
     *
     * @param commentReply 评论回复
     * @param id           评论回复主键
     * @return CommentReply 评论回复
     */
    CommentReply edit(CommentReply commentReply, Integer id);

    /**
     * 删除评论回复
     *
     * @param id 评论回复主键
     */
    void delete(Integer id);

    /**
     * 获取评论回复
     *
     * @param id 评论回复主键
     * @return CommentReply  评论回复
     */
    CommentReply getModel(Integer id);

    /**
     * 查询评论的相关回复
     *
     * @param commentIds
     * @return
     */
    Map<Integer, CommentReply> getReply(List<Integer> commentIds);

    /**
     * 回复评论
     *
     * @param commentId
     * @param reply
     * @param permission
     * @return
     */
    CommentReply replyComment(Integer commentId, String reply, Permission permission);
}