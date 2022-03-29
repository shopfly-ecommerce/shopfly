/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.fulldiscount.model.vo;

import dev.shopflix.core.promotion.coupon.model.dos.CouponDO;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountDO;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionGoodsDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 满优惠活动VO
 *
 * @author Snow
 * @version v1.0
 * @since v7.0.0
 * 2018年4月17日 下午2:46:27
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiModel(description = "满优惠活动VO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FullDiscountVO extends FullDiscountDO implements Serializable {

    public FullDiscountVO() {
        this.goodsIdList = new ArrayList<>();
    }

    private static final long serialVersionUID = -5509785605653051033L;

    @ApiModelProperty(name = "goods_list", value = "促销商品列表")
    private List<PromotionGoodsDTO> goodsList;

    /**
     * 商品id列表
     */
    private List<Integer> goodsIdList;

    @ApiModelProperty(name = "status_text", value = "活动状态")
    private String statusText;

    @ApiModelProperty(value = "赠品")
    private FullDiscountGiftDO fullDiscountGiftDO;

    @ApiModelProperty(value = "赠优惠券")
    private CouponDO couponDO;


    @ApiModelProperty(value = "赠优积分")
    private Integer point;

    @ApiModelProperty(name = "status", value = "活动状态标识,expired表示已失效")
    private String status;


    public List<PromotionGoodsDTO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PromotionGoodsDTO> goodsList) {
        this.goodsList = goodsList;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public FullDiscountGiftDO getFullDiscountGiftDO() {
        return fullDiscountGiftDO;
    }

    public void setFullDiscountGiftDO(FullDiscountGiftDO fullDiscountGiftDO) {
        this.fullDiscountGiftDO = fullDiscountGiftDO;
    }

    public CouponDO getCouponDO() {
        return couponDO;
    }

    public void setCouponDO(CouponDO couponDO) {
        this.couponDO = couponDO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public List<Integer> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<Integer> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FullDiscountVO that = (FullDiscountVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(goodsList, that.goodsList)
                .append(goodsIdList, that.goodsIdList)
                .append(statusText, that.statusText)
                .append(fullDiscountGiftDO, that.fullDiscountGiftDO)
                .append(couponDO, that.couponDO)
                .append(point, that.point)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(goodsList)
                .append(goodsIdList)
                .append(statusText)
                .append(fullDiscountGiftDO)
                .append(couponDO)
                .append(point)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FullDiscountVO{" +
                "goodsList=" + goodsList +
                ", goodsIdList=" + goodsIdList +
                ", statusText='" + statusText + '\'' +
                ", fullDiscountGiftDO=" + fullDiscountGiftDO +
                ", couponDO=" + couponDO +
                ", point=" + point +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}
