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
package cloud.shopfly.b2c.core.statistics;

/**
 * Statistical error code
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-03-26 In the afternoon11:56
 */
public enum StatisticsErrorCode {

    // Statistical error codes
    E801("Incorrect request parameters"),
    E810("Service processing exception");

    private String describe;

    StatisticsErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get exception code
     *
     * @return Abnormal code
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * Get statistics error messages
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
