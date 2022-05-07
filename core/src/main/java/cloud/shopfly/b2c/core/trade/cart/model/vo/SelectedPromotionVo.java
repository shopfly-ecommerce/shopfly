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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kingapex on 2018/12/9.
 * User selected offers and coupons<br/>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/9
 */
public class SelectedPromotionVo implements Serializable {


    private static final long serialVersionUID = -5721652966259085432L;
    /**
     * Coupons selected by the user
     */
    private CouponVO coupon;

    /**
     * User selected single product promotional activities
     */
    private List<PromotionVO> singlePromotionList;

    /**
     * User-selected composite activity
     */
    private PromotionVO groupPromotion;


    /**
     * Constructor, initializationmap
     */
    public SelectedPromotionVo() {
    }


    /**
     * Set up a coupon
     *
     * @param coupon coupons
     */
    public void setCoupon(CouponVO coupon) {
        this.coupon = coupon;
    }


    /**
     * Set up a promotional campaign for a product
     *
     * @param usedPromotionVo someskuPromotional activities to be used
     */
    public void setPromotion(PromotionVO usedPromotionVo) {
        if (usedPromotionVo == null) {
            return;
        }


        // Only the full reduction is a combination of activities, the other are all single product activities
        if (usedPromotionVo.getPromotionType().equals(PromotionTypeEnum.FULL_DISCOUNT.name())) {
            this.groupPromotion = usedPromotionVo;
        } else {
            this.putSinglePromotion(usedPromotionVo);
        }

    }


    /**
     * Set up individual product promotion activities
     *
     * @param usedPromotionVo Single product activity to be used
     */
    private void putSinglePromotion(PromotionVO usedPromotionVo) {

        if (singlePromotionList == null) {
            singlePromotionList = new ArrayList<>();
        }

        // Check whether the current SKU has promotional activities, if so, delete first (single product activities cannot overlap)
        Iterator<PromotionVO> iterator = singlePromotionList.iterator();
        while (iterator.hasNext()) {
            PromotionVO promotionVO = iterator.next();
            if (promotionVO.getSkuId().intValue() == usedPromotionVo.getSkuId().intValue()) {
                iterator.remove();
            }
        }
        // Pass the reference, no need to press back
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
