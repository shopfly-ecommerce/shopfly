/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
