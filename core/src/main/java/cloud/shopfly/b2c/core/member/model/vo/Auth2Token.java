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
package cloud.shopfly.b2c.core.member.model.vo;

import java.io.Serializable;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust login user information
 * @ClassName Auth2Token
 * @since v7.0 In the morning10:09 2018/6/6
 */
public class Auth2Token implements Serializable {


    private static final long serialVersionUID = 761784446368906457L;
    /**
     * Open the userID
     */
    private String unionid;

    /**
     * opneid
     */
    private String opneId;

    /**
     * OpenIdtype
     */
    private String type;

    /**
     * To obtainopenidThe token
     */
    private String accessToken;

    /**
     * WeChat refreshtoken If you need it later, get it from here
     */
    private String refreshToken;

    /**
     * Valid time is used for refreshingtoken
     */
    private Long expires;

    /**
     * storageappid To refreshtoken
     */
    private String appid;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpneId() {
        return opneId;
    }

    public void setOpneId(String opneId) {
        this.opneId = opneId;
    }

    @Override
    public String toString() {
        return "Auth2Token{" +
                "unionid='" + unionid + '\'' +
                ", opneId='" + opneId + '\'' +
                ", type='" + type + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
