/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.cart.model.vo;

import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.enation.app.javashop.core.promotion.tool.model.vo.PromotionVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kingapex on 2018/12/9.
 * 用户选择的优惠活动和优惠券<br/>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/9
 */
public class SelectedPromotionVo implements Serializable {


    private static final long serialVersionUID = -5721652966259085432L;
    /**
     * 用户选择的优惠券
     */
    private CouponVO coupon;

    /**
     * 用户选择的单品优惠活动
     */
    private List<PromotionVO> singlePromotionList;

    /**
     * 用户选择的组合活动
     */
    private PromotionVO groupPromotion;


    /**
     * 构造器，初始化map
     */
    public SelectedPromotionVo() {
    }


    /**
     * 设置一个优惠券
     *
     * @param coupon 优惠券
     */
    public void setCoupon(CouponVO coupon) {
        this.coupon = coupon;
    }


    /**
     * 设置一个商品的促销活动
     *
     * @param usedPromotionVo 某sku的要使用的促销活动
     */
    public void setPromotion(PromotionVO usedPromotionVo) {
        if (usedPromotionVo == null) {
            return;
        }


        //只有满减是组合活动，其它全是单品活动
        if (usedPromotionVo.getPromotionType().equals(PromotionTypeEnum.FULL_DISCOUNT.name())) {
            this.groupPromotion = usedPromotionVo;
        } else {
            this.putSinglePromotion(usedPromotionVo);
        }

    }


    /**
     * 设置单品促销活动
     *
     * @param usedPromotionVo 要使用的单品活动
     */
    private void putSinglePromotion(PromotionVO usedPromotionVo) {

        if (singlePromotionList == null) {
            singlePromotionList = new ArrayList<>();
        }

        //查找当前sku是否有促销活动，如果有，先删掉（单品活动不能重叠）
        Iterator<PromotionVO> iterator = singlePromotionList.iterator();
        while (iterator.hasNext()) {
            PromotionVO promotionVO = iterator.next();
            if (promotionVO.getSkuId().intValue() == usedPromotionVo.getSkuId().intValue()) {
                iterator.remove();
            }
        }
        //传引用，不用重新压回了
        singlePromotionList.add(usedPromotionVo);
    }


    @Override
    public String toString() {
        return "SelectedPromotionVo{" +
                "couponMap=" + coupon +
                ", singlePromotionMap=" + singlePromotionList +
                ", groupPromotionMap=" + groupPromotion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectedPromotionVo that = (SelectedPromotionVo) o;

        return new EqualsBuilder()
                .append(coupon, that.coupon)
                .append(singlePromotionList, that.singlePromotionList)
                .append(groupPromotion, that.groupPromotion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(coupon)
                .append(singlePromotionList)
                .append(groupPromotion)
                .toHashCode();
    }

    public CouponVO getCoupon() {
        return coupon;
    }


    public List<PromotionVO> getSinglePromotionList() {
        return singlePromotionList;
    }

    public void setSinglePromotionList(List<PromotionVO> singlePromotionList) {
        this.singlePromotionList = singlePromotionList;
    }

    public PromotionVO getGroupPromotion() {
        return groupPromotion;
    }

    public void setGroupPromotion(PromotionVO groupPromotion) {
        this.groupPromotion = groupPromotion;
    }
}
