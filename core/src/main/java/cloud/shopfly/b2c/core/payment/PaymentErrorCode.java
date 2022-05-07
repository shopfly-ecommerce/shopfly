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
package cloud.shopfly.b2c.core.payment;

/**
 * Payment exception code
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum PaymentErrorCode {

    /**
     * A deal that doesnt exist
     */
    E500("A deal that doesnt exist"),
    /**
     * Payment method that does not exist
     */
    E501("Payment method that does not exist"),
    /**
     * Open payment method
     */
    E502("Open payment method"),
    /**
     * Payment callback validation failed
     */
    E503("Payment callback validation failed"),
    /**
     * Paying bills does not exist
     */
    E504("Paying bills does not exist"),
    /**
     * The payment mode parameters are incorrect
     */
    E505("The payment mode parameters are incorrect"),
    /**
     * The order status is incorrect and cannot be paid
     */
    E506("The order status is incorrect and cannot be paid"),

    /**
     * No suitable callback was found
     */
    E507("No suitable callback was found"),

    E509("openidCant be empty");

    private String describe;

    PaymentErrorCode(String des) {
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

    public String getDescribe() {
        return this.describe;
    }
}
