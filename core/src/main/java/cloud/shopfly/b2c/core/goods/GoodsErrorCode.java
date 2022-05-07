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
package cloud.shopfly.b2c.core.goods;

/**
 * Commodity exception codeCreated by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0 2018/3/13
 */
public enum GoodsErrorCode {

    /**
     * Classification correlation anomaly
     */
    E300("Classification correlation anomaly"),
    /**
     * Commodity related anomaly
     */
    E301("Commodity related anomaly"),
    /**
     * Brand related exception
     */
    E302("Brand related exception"),
    /**
     * Parameter dependent anomaly
     */
    E303("Parameter dependent anomaly"),
    /**
     * Parameter groups are abnormal
     */
    E304("Parameter groups are abnormal"),
    /**
     * Specification related exception
     */
    E305("Specification related exception"),
    /**
     * The specification value is abnormal
     */
    E306("The specification value is abnormal"),
    /**
     * Exception related to commodity inventory
     */
    E307("Exception related to commodity inventory"),
    /**
     * Draft item related exception
     */
    E308("Draft item related exception"),
    /**
     * Label related exception
     */
    E309("Label related exception");

    private String describe;

    GoodsErrorCode(String des) {
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

}
