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
 * @Description 微信信任登录参数组枚举类
 * @ClassName 微信ConnectConfigGroupEnum
 * @since v7.0 下午8:05 2018/6/28
 */
public enum WechatConnectConfigGroupEnum {
    /**
     * PC网页端参数
     */
    pc("PC网页端参数"),
    /**
     * 微信网页端参数
     */
    wechat("微信网页端参数"),
    /**
     * 原生-APP参数
     */
    app("原生-APP参数"),
    /**
     * RN-APP参数"
     */
    rn("RN-APP参数"),
    /**
     * 小程序参数
     */
    miniprogram("小程序参数");


    private String text;

    WechatConnectConfigGroupEnum(String text) {
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
