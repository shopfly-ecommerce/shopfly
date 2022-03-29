/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 组合商品活动模型
 *
 * @author Snow
 * @version v1.0
 * 2017年08月23日16:06:48
 * @since v6.4
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupPromotionVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3188594102818543304L;

    /**
     * 促销活动工具类型 存储PromotionTypeEnum.XXX.getType();
     */
    @ApiModelProperty(value = "促销活动工具类型")
    private String promotionType;

    /**
     * 根据以上的活动工具类型 存储对应的Vo<br>
     * 例如：上面的类型为groupbuy，那么此处的则为GroupbuyVo
     */
    @ApiModelProperty(value = "活动详情")
    private Object activityDetail;

    @ApiModelProperty(value = "商品集合")
    private List<CartSkuVO> skuList;

    /**
     * 商家价格小计 = 商品集合中小计的总和。
     */
    @ApiModelProperty(value = "商品价格小计")
    private Double subtotal;


    @ApiModelProperty(value = "是否是组合活动,1为是组合活动，2为单品活动")
    private Integer isGroup;

    @ApiModelProperty(value = "差额")
    private Double spreadPrice;

    @ApiModelProperty(value = "优惠金额")
    private Double discountPrice;

    /**
     * 购物车页-满优惠活动是否选中状态
     * 1为选中
     */
    @ApiModelProperty(value = "活动是否选中.1为选中,0未选中")
    private Integer checked;


    @Override
    public String toString() {
        return "GroupPromotionVO{" +
                "promotionType='" + promotionType + '\'' +
                ", activityDetail=" + activityDetail +
                ", skuList=" + skuList +
                ", subtotal=" + subtotal +
                ", isGroup=" + isGroup +
                ", spreadPrice=" + spreadPrice +
                ", discountPrice=" + discountPrice +
                ", checked=" + checked +
                '}';
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Object getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(Object activityDetail) {
        this.activityDetail = activityDetail;
    }

    public List<CartSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<CartSkuVO> skuList) {
        this.skuList = skuList;
    }

    public Double getSubtotal() {
        if (subtotal == null) {
            subtotal = 0.0;
        }
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    public Double getSpreadPrice() {
        return spreadPrice;
    }

    public void setSpreadPrice(Double spreadPrice) {
        this.spreadPrice = spreadPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupPromotionVO that = (GroupPromotionVO) o;

        return new EqualsBuilder()
                .append(promotionType, that.promotionType)
                .append(activityDetail, that.activityDetail)
                .append(skuList, that.skuList)
                .append(subtotal, that.subtotal)
                .append(isGroup, that.isGroup)
                .append(spreadPrice, that.spreadPrice)
                .append(discountPrice, that.discountPrice)
                .append(checked, that.checked)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(promotionType)
                .append(activityDetail)
                .append(skuList)
                .append(subtotal)
                .append(isGroup)
                .append(spreadPrice)
                .append(discountPrice)
                .append(checked)
                .toHashCode();
    }
}
