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
 * 前端订单页面TAB标签枚举
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderTagEnum {


    /**
     * 所有订单
     */
    ALL("所有订单"),

    /**
     * 待付款
     */
    WAIT_PAY("待付款"),

    /**
     * 待发货
     */
    WAIT_SHIP("待发货"),

    /**
     * 待收货
     */
    WAIT_ROG("待收货"),

    /**
     * 已取消
     */
    CANCELLED("已取消"),

    /**
     * 已完成
     */
    COMPLETE("已完成"),

    /**
     * 待评论
     */
    WAIT_COMMENT("待评论"),

    /**
     * 售后中
     */
    REFUND("售后中");

    private String description;


    OrderTagEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static OrderTagEnum defaultType() {
        return ALL;
    }

    public String value() {
        return this.name();
    }


}
