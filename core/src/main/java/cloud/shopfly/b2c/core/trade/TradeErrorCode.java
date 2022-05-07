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
package cloud.shopfly.b2c.core.trade;

/**
 * Transaction exception code range：451~499
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum TradeErrorCode {

    /**
     * The item added to the shopping cart is abnormal
     */
    E451("Abnormal goods"),

    /**
     * Check anomalies before placing orders
     */
    E452("Check anomalies before placing orders"),

    /**
     * Read the abnormal：If the order does not exist
     */
    E453("Read the abnormal"),

    /**
     * Abnormal payment：If the payment amount is inconsistent with the actual payment amount of the order
     */
    E454("Abnormal payment"),

    /**
     * Parameters of the abnormal
     */
    E455("Parameters of the abnormal"),

    /**
     * Order creation exception
     */
    E456("Order creation exception"),

    /**
     * exportExcelabnormal
     */
    E457("exportExcelabnormal"),

    /**
     * The deal doesnt exist.
     */
    E458("The deal doesnt exist."),

    /**
     * Order does not exist
     */
    E459("Order does not exist"),

    /**
     * No permission to operate an order
     */
    E460("No permission to operate an order"),

    /**
     * Goods are not in the distribution area
     */
    E461("Goods are not in the distribution area"),

    /**
     * There was an error using the promotion
     */
    E462("There was an error using the promotion"),

    /*
     * The order amount is incorrect
     */
    E471("The order amount is incorrect");


    private String describe;

    TradeErrorCode(String des) {
        this.describe = des;
    }

    public String code() {
        return this.name().replaceAll("E", "");
    }


}
