/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.model.vo;

import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.core.trade.cart.model.enums.PromotionTarget;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by kingapex on 2018/12/12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/12
 */
public class PromotionRule implements Serializable {

    private static final long serialVersionUID = -5098604925079913257L;

    public PromotionRule(PromotionTarget target) {
        reducedTotalPrice = 0D;
        reducedPrice = 0D;
        pointGift = 0;
        usePoint = 0;
        freeShipping = false;
        tips = "";
        this.target = target;
        invalidReason = "";
        invalid = false;
    }

    /**
     * 一共要减多少钱
     */
    private Double reducedTotalPrice;

    /**
     * 单价减多少钱
     */
    private Double reducedPrice;

    private FullDiscountGiftDO goodsGift;
    private CouponVO couponGift;
    private Integer pointGift;
    private Integer usePoint;
    private CouponVO useCoupon;
    private Boolean freeShipping;
    private String tips;
    private PromotionTarget target;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromotionRule rule = (PromotionRule) o;

        return new EqualsBuilder()
                .append(invalid, rule.invalid)
                .append(reducedTotalPrice, rule.reducedTotalPrice)
                .append(reducedPrice, rule.reducedPrice)
                .append(goodsGift, rule.goodsGift)
                .append(couponGift, rule.couponGift)
                .append(pointGift, rule.pointGift)
                .append(usePoint, rule.usePoint)
                .append(useCoupon, rule.useCoupon)
                .append(freeShipping, rule.freeShipping)
                .append(tips, rule.tips)
                .append(target, rule.target)
                .append(tag, rule.tag)
                .append(invalidReason, rule.invalidReason)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(reducedTotalPrice)
                .append(reducedPrice)
                .append(goodsGift)
                .append(couponGift)
                .append(pointGift)
                .append(usePoint)
                .append(useCoupon)
                .append(freeShipping)
                .append(tips)
                .append(target)
                .append(tag)
                .append(invalid)
                .append(invalidReason)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PromotionRule{" +
                "reducedTotalPrice=" + reducedTotalPrice +
                ", reducedPrice=" + reducedPrice +
                ", goodsGift=" + goodsGift +
                ", couponGift=" + couponGift +
                ", pointGift=" + pointGift +
                ", usePoint=" + usePoint +
                ", useCoupon=" + useCoupon +
                ", freeShipping=" + freeShipping +
                ", tips='" + tips + '\'' +
                ", target=" + target +
                ", tag='" + tag + '\'' +
                ", invalid=" + invalid +
                ", invalidReason='" + invalidReason + '\'' +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 促销标签
     */
    private String tag;

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    /**
     * 是否失效了
     */
    private boolean invalid;


    /**
     * 失效原因
     */
    private String invalidReason;


    public Double getReducedTotalPrice() {
        return reducedTotalPrice;
    }

    public void setReducedTotalPrice(Double reducedTotalPrice) {
        this.reducedTotalPrice = reducedTotalPrice;
    }

    public FullDiscountGiftDO getGoodsGift() {
        return goodsGift;
    }

    public void setGoodsGift(FullDiscountGiftDO goodsGift) {
        this.goodsGift = goodsGift;
    }

    public CouponVO getCouponGift() {
        return couponGift;
    }

    public void setCouponGift(CouponVO couponGift) {
        this.couponGift = couponGift;
    }

    public Integer getPointGift() {
        return pointGift;
    }

    public void setPointGift(Integer pointGift) {
        this.pointGift = pointGift;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(Integer usePoint) {
        this.usePoint = usePoint;
    }

    public CouponVO getUseCoupon() {
        return useCoupon;
    }

    public void setUseCoupon(CouponVO useCoupon) {
        this.useCoupon = useCoupon;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public PromotionTarget getTarget() {
        return target;
    }

    public void setTarget(PromotionTarget target) {
        this.target = target;
    }

    public Double getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(Double reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

}
