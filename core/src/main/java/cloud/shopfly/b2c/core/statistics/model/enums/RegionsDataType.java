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
package cloud.shopfly.b2c.core.statistics.model.enums;

/**
 * Local data type
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/9 11:59
 */
public enum RegionsDataType {

    // Number of order members
    ORDER_MEMBER_NUM("Number of single members"),
    // Place the order amount
    ORDER_PRICE("Place the order amount"),
    // Order quantity
    ORDER_NUM("Order quantity");

    private String description;

    RegionsDataType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public String value() {
        return this.name();
    }

}
