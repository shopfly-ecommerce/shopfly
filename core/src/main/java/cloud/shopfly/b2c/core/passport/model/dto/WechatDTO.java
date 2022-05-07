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
package cloud.shopfly.b2c.core.passport.model.dto;


import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;

import java.io.Serializable;

/**
 * WechatDTO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-06-18 11:28 AM
 */
public class WechatDTO implements Serializable {

    /**
     * Redirect address required
     */
    private String redirectUrl;


    /**
     * If redirection is required, the front-end redirect is used to obtain the data againaccesstoken（containsopenid）
     */
    private boolean needRedirect;


    /**
     * Store user information and communication between wechattoken
     */
    private Auth2Token auth2Token;


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isNeedRedirect() {
        return needRedirect;
    }

    public void setNeedRedirect(boolean needRedirect) {
        this.needRedirect = needRedirect;
    }

    public Auth2Token getAuth2Token() {
        return auth2Token;
    }

    public void setAuth2Token(Auth2Token auth2Token) {
        this.auth2Token = auth2Token;
    }
}
