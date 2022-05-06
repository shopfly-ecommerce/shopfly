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
package cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer;

/**
 * 微信签名参数枚举
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/21 下午3:53
 */

public enum WechatTypeEnmu {

    /**
     * PC
     */
    PC("PC"),
    /**
     * WAP
     */
    WAP("WAP"),
    /**
     * 原生
     */
    REACT("原生"),
    /**
     * NAAPP
     */
    NATIVE("NAAPP"),
    /**
     * 小程序
     */
    MINI("小程序");
    private String text;

    WechatTypeEnmu(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
