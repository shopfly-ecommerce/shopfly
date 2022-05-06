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
 * 订单的操作方式
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017年5月19日下午10:43:06
 */
public enum OrderOperateEnum {


    /**
     * 确认
     */
    CONFIRM("确认"),

    /**
     * 支付
     */
    PAY("支付"),

    /**
     * 发货
     */
    SHIP("发货"),

    /**
     * 确认收货
     */
    ROG("确认收货"),

    /**
     * 取消
     */
    CANCEL("取消"),

    /**
     * 评论
     */
    COMMENT("评论"),

    /**
     * 完成
     */
    COMPLETE("完成");

    private String description;

    OrderOperateEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

}
