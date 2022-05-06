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
 * 微信jsapi 访问票据
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-22 上午11:55
 */
public class WechatJsapiTicket implements Serializable {


    /**
     * jsapiticket
     */
    private String jsapiTicket;
    /**
     * expires_in 有效时间
     */
    private Integer expires;

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }
}
