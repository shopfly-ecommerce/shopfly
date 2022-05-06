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
 * @Description 支付宝信任登录参数项枚举类
 * @ClassName AlipayConnectConfigItemEnum
 * @since v7.0 下午6:13 2018/6/28
 */
public enum AlipayConnectConfigItemEnum {
    /**
     * appId
     */
    app_id("app_id"),
    /**
     * 支付宝私钥
     */
    private_key("支付宝私钥"),
    /**
     * 支付宝公钥
     */
    public_key("支付宝公钥"),
    /**
     * 支付宝公钥
     */
    pid("app的pid");

    private String text;

    AlipayConnectConfigItemEnum(String text) {
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
