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
package cloud.shopfly.b2c.core.promotion;

/**
 * Transaction exception code
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum PromotionErrorCode {

    /**
     * Parameter error
     */
    E400("Parameter error"),

    /**
     * Activity goods conflict
     */
    E401("Activity goods conflict"),

    /**
     * Conflict of activities
     */
    E402("Conflict of activities"),

    /**
     * The stock of active goods is insufficient
     */
    E403("The stock of active goods is insufficient"),

    /**
     * Exceed the group purchase limit
     */
    E404("Exceed the group purchase limit"),

    /**
     * The limit is greater than the circulation
     */
    E405("The limit exceeds the circulation"),

    /**
     * The quantity limit is not in compliance
     */
    E406("The quantity limit is not in compliance"),

    /**
     * Integral classification repetition
     */
    E407("Integral classification repetition"),
    /**
     * Errors related to group buying activities
     */
    E408("Errors related to group buying activities"),

    /**
     * Promotion amount error
     */
    E409("Promotion amount error")
    ;

    private String describe;

    PromotionErrorCode(String des) {
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
     * The exception message
     *
     * @return
     */
    public String des() {
        return this.describe;
    }
}
