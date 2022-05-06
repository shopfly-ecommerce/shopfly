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
package cloud.shopfly.b2c.core.distribution.model.enums;

/**
 * 模版切换状态枚举
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/25 上午11:39
 */
public enum UpgradeTypeEnum {
    //提现状态
    MANUAL("手动"), AUTOMATIC("自动");

    private String name;

    UpgradeTypeEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
