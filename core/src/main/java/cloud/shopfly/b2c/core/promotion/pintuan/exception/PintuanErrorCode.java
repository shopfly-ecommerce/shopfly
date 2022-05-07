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
package cloud.shopfly.b2c.core.promotion.pintuan.exception;

/**
 * PromotionErrorCode
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-19 In the afternoon1:31
 */
public enum PintuanErrorCode {

    /**
     * PintuanErrorCode
     */
    E5011("During the activity time, you cannot edit the activity, please go to the merchant side to stop"),
    E5012("Inactive time, cannot be opened/Close the activity"),
    E5013("Illegal operation"),

    E5014("Bonus items cannot be entered"),
    E5015("Event merchandise cannot participate"),
    E5016("Group goods cannot participate"),
    E5017("The activity cannot be performed because it is in progress. Contact your administrator if you want to perform the operation"),
    E5018("Exceed the purchase limit");

    private String describe;

    PintuanErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Gets the exception code for the item
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    /**
     * Get error message
     *
     * @return
     */
    public String describe() {
        return this.describe;
    }

}
