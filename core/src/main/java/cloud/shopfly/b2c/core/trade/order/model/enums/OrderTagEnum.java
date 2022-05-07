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
 * Front-end order pageTABLabel the enumeration
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderTagEnum {


    /**
     * All orders
     */
    ALL("All orders"),

    /**
     * For the payment
     */
    WAIT_PAY("For the payment"),

    /**
     * To send the goods
     */
    WAIT_SHIP("To send the goods"),

    /**
     * For the goods
     */
    WAIT_ROG("For the goods"),

    /**
     * Has been cancelled
     */
    CANCELLED("Has been cancelled"),

    /**
     * Has been completed
     */
    COMPLETE("Has been completed"),

    /**
     * To comment on
     */
    WAIT_COMMENT("To comment on"),

    /**
     * In the after-sale
     */
    REFUND("In the after-sale");

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
