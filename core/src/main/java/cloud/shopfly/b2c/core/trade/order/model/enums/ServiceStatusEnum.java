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
 * Status of application after sale
 *
 * @author Snow
 * @version v2.0
 * @since v6.4
 * 2017years9month6On the afternoon4:16:53
 */
public enum ServiceStatusEnum {

    /**
     * Did not apply for
     */
    NOT_APPLY("Did not apply for"),

    /**
     * Has applied for
     */
    APPLY("Has applied for"),

    /**
     * approved
     */
    PASS("approved"),

    /**
     * The audit is not approved.
     */
    REFUSE("The audit is not approved."),

    /**
     * Expired or not allowed to apply for after sales
     */
    EXPIRED("It is not allowed to apply for after-sales service");


    private String description;

    ServiceStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
