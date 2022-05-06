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
package cloud.shopfly.b2c.core.trade.order.model.enums;

/**
 * 订单元Key
 *
 * @author Snow create in 2018/7/5
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderMetaKeyEnum {

    /**
     * 使用的积分
     */
    POINT("使用的积分"),

    /**
     * 赠送的积分
     */
    GIFT_POINT("赠送的积分"),

    /**
     * 赠送的优惠券
     */
    COUPON("赠送的优惠券"),

    /**
     * 优惠券抵扣金额
     */
    COUPON_PRICE("优惠券抵扣金额"),

    /**
     * 满减金额
     */
    FULL_MINUS("满减金额"),

    /**
     * 赠品
     */
    GIFT("赠品"),

    CASH_BACK("返现");

    private String description;

    OrderMetaKeyEnum(String description) {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
