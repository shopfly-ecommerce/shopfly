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
package cloud.shopfly.b2c.core.system.enums;

/**
 * Operation type enumeration
 *
 * @author zh
 * @version v7.0
 * @since v7.0 2017years10month17The morning of10:49:25
 */
public enum AfterOpenEnum {

    /**
     * Open the application
     */
    go_app,
    /**
     * Jump toURL
     */
    go_url,
    /**
     * Open specificactivity
     */
    go_activity,
    /**
     * User-defined content
     */
    go_custom


}
