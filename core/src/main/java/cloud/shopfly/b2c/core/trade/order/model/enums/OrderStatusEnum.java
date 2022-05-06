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
 * 订单状态
 *
 * @author Snow
 * @version 1.0
 * @since v7.0.0
 * 2017年3月31日下午2:44:54
 */
public enum OrderStatusEnum {

    /**
     * 新订单
     */
    NEW("新订单"),

    /**
     * 出库失败
     */
    INTODB_ERROR("出库失败"),

    /**
     * 已确认
     */
    CONFIRM("已确认"),

    /**
     * 已付款
     */
    PAID_OFF("已付款"),

    /**
     * 已成团
     */
    FORMED("已经成团"),

    /**
     * 已发货
     */
    SHIPPED("已发货"),

    /**
     * 已收货
     */
    ROG("已收货"),

    /**
     * 已完成
     */
    COMPLETE("已完成"),

    /**
     * 已取消
     */
    CANCELLED("已取消"),

    /**
     * 售后中
     */
    AFTER_SERVICE("售后中");


    private String description;

    OrderStatusEnum(String description) {
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
