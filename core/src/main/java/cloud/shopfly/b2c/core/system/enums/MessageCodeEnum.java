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
package cloud.shopfly.b2c.core.system.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description Message template number enumeration class
 * @ClassName MessageCodeEnum
 * @since v7.0 In the afternoon5:03 2018/7/5
 */
public enum MessageCodeEnum {
    /**
     * Store new order creation reminder
     */
    SHOPORDERSNEW("Store new order creation reminder"),
    /**
     * Store order payment reminder
     */
    SHOPORDERSPAY("Store order payment reminder"),
    /**
     * Store order receipt reminder
     */
    SHOPORDERSRECEIVE("Store order receipt reminder"),
    /**
     * Store order evaluation reminder
     */
    SHOPORDERSEVALUATE("Store order evaluation reminder"),
    /**
     * Store order cancellation reminder
     */
    SHOPORDERSCANCEL("Store order cancellation reminder"),
    /**
     * Store Refund Reminder
     */
    SHOPREFUND("Store Refund Reminder"),
    /**
     * Store Return Reminder
     */
    SHOPRETURN("Store Return Reminder"),
    /**
     * Reminder of goods being banned for violation（Goods from the shelves）
     */
    SHOPGOODSVIOLATION("Reminder of goods being banned for violation（Goods from the shelves）"),
    /**
     * Product audit failure reminder
     */
    SHOPGOODSVERIFY("Product audit failure reminder"),
    /**
     * Return to remind
     */
    MEMBERRETURNUPDATE("Return to remind"),
    /**
     * Refund to remind
     */
    MEMBERREFUNDUPDATE("Refund to remind"),
    /**
     * Order shipping Reminder
     */
    MEMBERORDERSSEND("Order shipping Reminder"),
    /**
     * Order receipt reminder
     */
    MEMBERORDERSRECEIVE("Order receipt reminder"),
    /**
     * Order Payment reminder
     */
    MEMBERORDERSPAY("Order Payment reminder"),
    /**
     * Order Cancellation Reminder
     */
    MEMBERORDERSCANCEL("Order Cancellation Reminder"),
    /**
     * The phone sends the verification code
     */
    MOBILECODESEND("The phone sends the verification code"),
    /**
     * Notification of merchandise removal
     */
    SHOPGOODSMARKETENABLE("Notification of merchandise removal"),
    /**
     * Member login successful reminder
     */
    MEMBERLOGINSUCCESS("Member login successful reminder"),
    /**
     * Member registration success reminder
     */
    MEMBERREGISTESUCCESS("Member registration success reminder");

    private String description;

    MessageCodeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
