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
package cloud.shopfly.b2c.core.aftersale.model.enums;

/**
 * A refund(cargo)Operation enumeration class
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the afternoon4:54 2018/5/2
 */
public enum RefundOperateEnum {

    // Apply for a refund
    APPLY("Apply for refund(paragraph)cargo"),
    // Administrator audit
    ADMIN_APPROVAL("Administrator audit"),
    // Return of the Treasury
    STOCK_IN("Return of the Treasury"),
    // cancel
    CANCEL("cancel"),
    // Administrator refund
    ADMIN_REFUND("Administrator refund");

    private String description;

    RefundOperateEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
