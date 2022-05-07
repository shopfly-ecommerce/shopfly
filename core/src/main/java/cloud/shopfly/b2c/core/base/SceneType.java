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
package cloud.shopfly.b2c.core.base;

/**
 * Image verification code service type
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month19On the afternoon4:35:32
 */
public enum SceneType {

    /**
     * Verification code login
     */
    LOGIN("Verification code login"),
    /**
     * Mobile phone registered
     */
    REGISTER("Mobile phone registered"),
    /**
     * Retrieve password
     */
    FIND_PASSWORD("Retrieve password"),
    /**
     * Binding mobile phone
     */
    BIND_MOBILE("Binding mobile phone"),
    /**
     * Change the password
     */
    MODIFY_PASSWORD("Change the password"),
    /**
     * Add a clerk
     */
    ADD_CLERK("Add a clerk"),
    /**
     * Verify the mobile phone
     */
    VALIDATE_MOBILE("Verify the mobile phone");

    private String description;

    SceneType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
