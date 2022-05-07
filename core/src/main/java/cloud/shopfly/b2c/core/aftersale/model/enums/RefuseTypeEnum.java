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
 * A refund(cargo)type
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 In the morning9:41 2018/5/3
 */
public enum RefuseTypeEnum {

    // A refund
    RETURN_MONEY("A refund"),
    // Return of the goods
    RETURN_GOODS("Return of the goods");

    private String description;

    RefuseTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
