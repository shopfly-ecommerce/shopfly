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

import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSettingVO;
import cloud.shopfly.b2c.core.trade.cart.model.dos.CartDO;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 购物车展示Vo
 *
 * @author Snow
 * @version v1.0
 * 2017年08月23日14:22:48
 * @since v6.4
 */

@ApiModel(description = "购物车展示Vo")
public class CartVO extends CartDO implements Serializable {


    private static final long serialVersionUID = 6382186311779188645L;
    /**
     * 把Cart.SkuList 数据 根据促销活动压入到此集合中。
     */
    @ApiModelProperty(value = "促销活动集合（包含商品")
    private List<GroupPromotionVO> promotionList;

    @ApiModelProperty(value = "已参与的的促销活动提示，直接展示给客户")
    private String promotionNotice;


    /**
     * 购物车与运费dto的map映射
     * key为skuid value 为模版
     */
    private Map<Integer, ShipTemplateSettingVO> shipTemplateChildMap;

    @ApiModelProperty(value = "购物车页展示时，店铺内的商品是否全选状态.1为店铺商品全选状态,0位非全选")
    private Integer checked;

    private List<PromotionRule> ruleList;

    /**
     * 购物车类型：购物车页面和或结算页
     */
    private CartType cartType;


    public CartVO() {

    }

    /**
     * 父类的构造器
     *
     * @param sellerId
     * @param sellerName
     */
    public CartVO(int sellerId, String sellerName, CartType cartType) {
        this.cartType = cartType;
        super.setWeight(0D);
        super.setSellerId(sellerId);
        super.setSellerName(sellerName);
        super.setPrice(new PriceDetailVO());
        super.setSkuList(new ArrayList<>());
        super.setCouponList(new ArrayList<>());
        super.setGiftList(new ArrayList<>());
        super.setGiftCouponList(new ArrayList<>());
        this.setChecked(1);
        this.setInvalid(0);
        ruleList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "promotionList=" + promotionList +
                ", promotionNotice='" + promotionNotice + '\'' +
                ", shipTemplateChildMap=" + shipTemplateChildMap +
                ", checked=" + checked +
                ", ruleList=" + ruleList +
                ", cartType=" + cartType +
                "} " + super.toString();
    }

    public List<PromotionRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<PromotionRule> ruleList) {
        this.ruleList = ruleList;

    }

    public List<GroupPromotionVO> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<GroupPromotionVO> promotionList) {
        this.promotionList = promotionList;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Map<Integer, ShipTemplateSettingVO> getShipTemplateChildMap() {
        return shipTemplateChildMap;
    }

    public String getPromotionNotice() {
        return promotionNotice;
    }

    public void setPromotionNotice(String promotionNotice) {
        this.promotionNotice = promotionNotice;
    }

    public void setShipTemplateChildMap(Map<Integer, ShipTemplateSettingVO> shipTemplateChildMap) {
        this.shipTemplateChildMap = shipTemplateChildMap;
    }

    public CartType getCartType() {
        return cartType;
    }

    public void setCartType(CartType cartType) {
        this.cartType = cartType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        CartVO cartVO = (CartVO) o;

        if (promotionList != null ? !promotionList.equals(cartVO.promotionList) : cartVO.promotionList != null) {
            return false;
        }
        if (promotionNotice != null ? !promotionNotice.equals(cartVO.promotionNotice) : cartVO.promotionNotice != null) {
            return false;
        }
        if (shipTemplateChildMap != null ? !shipTemplateChildMap.equals(cartVO.shipTemplateChildMap) : cartVO.shipTemplateChildMap != null) {
            return false;
        }
        return checked != null ? checked.equals(cartVO.checked) : cartVO.checked == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (promotionList != null ? promotionList.hashCode() : 0);
        result = 31 * result + (promotionNotice != null ? promotionNotice.hashCode() : 0);
        result = 31 * result + (shipTemplateChildMap != null ? shipTemplateChildMap.hashCode() : 0);
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        return result;
    }
}
