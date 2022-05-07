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
 * @Description Trusted login type enumeration class
 * @ClassName ConnectUserGenderEnum
 * @since v7.0 In the morning10:35 2018/6/6
 */
public enum ConnectTypeEnum {
    // QQ joint login
    QQ("QQ"),
    // QQ H5 login openID
    QQ_OPENID("QQ H5 landingopenid"),
    // QQ APP login openID
    QQ_APP("QQ APP landingopenid"),
    // Weibo joint login
    WEIBO("Weibo joint login"),
    // Wechat joint login
    WECHAT("Wechat joint login"),
    // Wechat mini program joint login
    WECHAT_MINI("Wechat mini program joint login"),
    // Wechat H5 Log in to OpenID
    WECHAT_OPENID("WeChatH5Sign inopenid"),
    // Log in to OpenID using wechat APP
    WECHAT_APP("WeChatAPPSign inopenid"),
    // Alipay login
    ALIPAY("Alipay login"),
    // Alipay login
    APPLEID("IOSApple login Login");

    private String description;

    ConnectTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
