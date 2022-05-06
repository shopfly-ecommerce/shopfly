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
package cloud.shopfly.b2c.core.member.model.dto;

/**
 * 会员数据附属数据统计
 *
 * @author zh
 * @version v7.0
 * @date 18/6/13 下午3:07
 * @since v7.0
 */
public class MemberStatisticsDTO {
    /**
     * 订单数
     */
    private Integer orderCount;
    /**
     * 商品收藏数
     */
    private Integer goodsCollectCount;
    /**
     * 待评论数
     */
    private Integer pendingCommentCount;

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getGoodsCollectCount() {
        return goodsCollectCount;
    }

    public void setGoodsCollectCount(Integer goodsCollectCount) {
        this.goodsCollectCount = goodsCollectCount;
    }

    public Integer getPendingCommentCount() {
        return pendingCommentCount;
    }

    public void setPendingCommentCount(Integer pendingCommentCount) {
        this.pendingCommentCount = pendingCommentCount;
    }

    @Override
    public String toString() {
        return "MemberStatisticsDTO{" +
                "orderCount=" + orderCount +
                ", goodsCollectCount=" + goodsCollectCount +
                ", pendingCommentCount=" + pendingCommentCount +
                '}';
    }
}
