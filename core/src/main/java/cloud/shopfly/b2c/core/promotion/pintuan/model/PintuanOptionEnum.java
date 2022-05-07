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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

/**
 *
 * Enumeration value for group operation
 * @author liushuai
 * @version v1.0
 * @since v7.0
 * 2019/2/26 In the morning10:40
 * @Description:
 *
 */

public enum PintuanOptionEnum {


    /**
     * Enumeration value for group operation
     */
    CAN_OPEN ("You can open"),

    CAN_CLOSE("You can turn off"),

    NOTHING("Nothing to manipulate");

    private String name;

    PintuanOptionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
