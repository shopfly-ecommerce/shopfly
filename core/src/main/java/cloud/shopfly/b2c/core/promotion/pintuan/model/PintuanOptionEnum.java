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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

/**
 *
 * 拼团操作枚举值
 * @author liushuai
 * @version v1.0
 * @since v7.0
 * 2019/2/26 上午10:40
 * @Description:
 *
 */

public enum PintuanOptionEnum {


    /**
     * 拼团操作枚举值
     */
    CAN_OPEN ("可以开启"),

    CAN_CLOSE("可以关闭"),

    NOTHING("没有什么可以操作的");

    private String name;

    PintuanOptionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
