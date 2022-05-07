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
package cloud.shopfly.b2c.core.base.service;

/**
 * Image captcha business layer
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018years3month19The morning of9:55:04
 */
public interface CaptchaManager {
    /**
     * Image authentication
     *
     * @param uuid  uid
     * @param code  captcha
     * @param scene Business types
     * @return
     */
    boolean valid(String uuid, String code, String scene);

    /**
     * Clear the image verification code
     *
     * @param uuid
     * @param code
     * @param scene
     */
    void deleteCode(String uuid, String code, String scene);

    /**
     * Images generated
     *
     * @param uuid  uid
     * @param scene Business types
     */
    void writeCode(String uuid, String scene);

}
