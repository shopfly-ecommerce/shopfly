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
package cloud.shopfly.b2c.core.client.member;

import cloud.shopfly.b2c.core.member.model.vo.GoodsGrade;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: Member comment external interface
 * @date 2018/7/26 11:34
 * @since v7.0.0
 */
public interface MemberCommentClient {

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
     * According to the membershipidModifying profile Picture Information
     *
     * @param memberId membersid
     * @param face     Head portrait
     */
    void editComment(Integer memberId, String face);
}
