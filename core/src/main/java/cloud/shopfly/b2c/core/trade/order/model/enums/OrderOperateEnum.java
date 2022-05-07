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
 * The operation mode of the order
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017years5month19On the afternoon10:43:06
 */
public enum OrderOperateEnum {


    /**
     * confirm
     */
    CONFIRM("confirm"),

    /**
     * pay
     */
    PAY("pay"),

    /**
     * The delivery
     */
    SHIP("The delivery"),

    /**
     * Confirm the goods
     */
    ROG("Confirm the goods"),

    /**
     * cancel
     */
    CANCEL("cancel"),

    /**
     * comments
     */
    COMMENT("comments"),

    /**
     * complete
     */
    COMPLETE("complete");

    private String description;

    OrderOperateEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

}
