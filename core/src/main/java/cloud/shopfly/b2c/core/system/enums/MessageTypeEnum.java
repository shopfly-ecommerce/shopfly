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
 * @author zjp
 * @version v7.0
 * @Description 消息类型枚举类
 * @ClassName MessageTypeEnum
 * @since v7.0 下午4:49 2018/7/5
 */
public enum MessageTypeEnum {
    //店铺
    SHOP("店铺"),
    //会员
    MEMBER("会员"),
    //其他
    OTHER("其他");

    private String description;

    MessageTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
