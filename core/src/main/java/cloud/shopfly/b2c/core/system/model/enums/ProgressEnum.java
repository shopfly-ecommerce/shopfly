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
package cloud.shopfly.b2c.core.system.model.enums;

/**
 * The progress of the enumeration
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2017years9month6On the afternoon8:44:42
 */
public enum ProgressEnum {


//  PROGRESS STATUS ENUM
    DOING("ongoing"), SUCCESS("successful"), EXCEPTION("abnormal");

    String status;

    ProgressEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
