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
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 支付宝信任登录参数组枚举类
 * @ClassName AlipayConnectConfigGroupEnum
 * @since v7.0 下午8:05 2018/6/28
 */
public enum AlipayConnectConfigGroupEnum {
    /**
     * 网页端参数 （PC，WAP）参数
     */
    pc("网页端参数 （PC，WAP）参数"),
    /**
     * app端参数
     */
    app("app端参数");

    private String text;

    AlipayConnectConfigGroupEnum(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String value() {
        return this.name();
    }
}
