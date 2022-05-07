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

import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import cloud.shopfly.b2c.core.member.model.dto.AdditionalCommentDTO;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.dto.CommentScoreDTO;
import cloud.shopfly.b2c.core.member.model.vo.GoodsGrade;
import cloud.shopfly.b2c.core.member.model.vo.MemberCommentCount;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Commenting on the business Layer
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
public interface MemberCommentManager {

    /**
     * Querying the Comment list
     *
     * @param param conditions
     * @return Page
     */
    Page list(CommentQueryParam param);

    /**
     * Add comments
     *
     * @param comment
     * @param permission
     * @return
     */
    MemberComment add(CommentScoreDTO comment, Permission permission);

    /**
     * Modify comments
     *
     * @param memberComment comments
     * @param id            Comment on the primary key
     * @return MemberComment comments
     */
    MemberComment edit(MemberComment memberComment, Integer id);

    /**
     * Delete the comment
     *
     * @param id Comment on the primary key
     */
    void delete(Integer id);

    /**
     * Get comments
     *
     * @param id Comment on the primary key
     * @return MemberComment  comments
     */
    MemberComment getModel(Integer id);

    /**
     * Query the percentage of good reviews
     *
     * @return
     */
    List<GoodsGrade> queryGoodsGrade();

    /**
     * According to the goodsidGet number of comments
     *
     * @param goodsId productid
     * @return comments
     */
    Integer getGoodsCommentCount(Integer goodsId);

    /**
     * Automatic high praise
     * @param detailDTOList
     */
    void autoGoodComments(List<OrderDetailDTO> detailDTOList);

    /**
     * Query the number of related reviews for an item
     *
     * @param goodsId
     * @return
     */
    MemberCommentCount count(Integer goodsId);

    /**
     * According to the membershipidModifying profile Picture Information
     *
     * @param memberId membersid
     * @param face     Head portrait
     */
    void editComment(Integer memberId, String face);

    /**
     * Additional comments from members
     *
     * @param comments
     * @param permission
     * @return
     */
    List<AdditionalCommentDTO> additionalComments(List<AdditionalCommentDTO> comments, Permission permission);
}
