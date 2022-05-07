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
package cloud.shopfly.b2c.core.distribution.exception;

/**
 * Distribution error code
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 In the morning8:53
 */
public enum DistributionErrorCode {

    //STATISTICS ERROR CODE
    E1000("The distribution service is abnormal. Please try again later."),
    E1001("The user has not logged in. Please log in and try again"),
    E1002("The withdrawal application cannot be repeated."),
    E1003("The amount applied exceeds the amount available for withdrawal."),
    E1004("Incorrect withdrawal request."),
    E1005("Wrong audit status."),
    E1006("Incorrect application amount."),
    E1010("Default templates cannot be deleted!"),
    E1012("Template is not allowed to delete, distributor use!"),
    E1013("Default templates cannot be modified!"),
    E1011("Insufficient parameters!");

    private String describe;

    DistributionErrorCode(String des) {
        this.describe = des;
    }

    /**
     * Get the statistical exception code
     *
     * @return
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
