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
package cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model;

import java.io.Serializable;

/**
 * 签名参数
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-21 下午2:48
 */
public class SignatureParams  implements Serializable {


    /**
     * appId
     */
    private String appId;


    /**
     * ticket
     */
    private WechatJsapiTicket wechatJsapiTicket;

    /**
     * token
     */
    private WechatAccessToken wechatAccessToken;


    public WechatJsapiTicket getWechatJsapiTicket() {
        return wechatJsapiTicket;
    }

    public void setWechatJsapiTicket(WechatJsapiTicket wechatJsapiTicket) {
        this.wechatJsapiTicket = wechatJsapiTicket;
    }

    public WechatAccessToken getWechatAccessToken() {
        return wechatAccessToken;
    }

    public void setWechatAccessToken(WechatAccessToken wechatAccessToken) {
        this.wechatAccessToken = wechatAccessToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
