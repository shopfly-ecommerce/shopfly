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
package cloud.shopfly.b2c.core.aftersale;

/**
 * After sale exception code
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum AftersaleErrorCode {

    /**
     * An exception
     */
    E600("The refund amount cannot be greater than the amount paid"),
    E601("Operation not allowed"),
    E602("Goods dont exist"),
    E603("Refund slip does not exist"),
    E604("Order does not exist"),
    E605("Refund method Mandatory"),
    E606("Storage failure"),
    E607("The quantity of goods applied for after sale should not be greater than the purchased quantity"),
    E608("Failed to export data"),
    E609("The refundable amount is0, no need to apply for a refund/Return, please contact the platform to solve");

    private String describe;

    AftersaleErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get exception code
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    /**
     * Obtaining exception Description
     *
     * @return
     */
    public String describe() {
        return this.describe;
    }


}
