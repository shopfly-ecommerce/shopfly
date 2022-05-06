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
 * 退款方式
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午9:47 2018/5/3
 */
public enum RefundWayEnum {

    //原路退回
    ORIGINAL("原路退回"),
    //线下支付
    OFFLINE("线下支付");

    private String description;

    RefundWayEnum(String des) {
        this.description = des;

    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
