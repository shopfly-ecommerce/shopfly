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
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.logs.Debugger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust the login plug-in base class
 * @ClassName AbstractConnectLoginPlugin
 * @since v7.0 In the morning10:46 2018/6/5
 */

@Component
public abstract class AbstractConnectLoginPlugin {

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    protected Cache cache;

    @Autowired
    private DomainHelper domainHelper;


    @Autowired
    protected Debugger debugger;


    /**
     * Initialization information Login parameters
     */
    protected Map initConnectSetting() {

        return connectManager.initConnectSetting();
    }

    /**
     * Obtained authorized loginurl
     *
     * @return
     */
    public abstract String getLoginUrl();

    /**
     * Callback method after successful login
     *
     * @return
     */
    public abstract Auth2Token loginCallback();

    /**
     * Fill in membership information
     *
     * @param auth2Token
     * @param member
     * @return
     */
    public abstract Member fillInformation(Auth2Token auth2Token, Member member);

    /**
     * Store intermediate page information and concatenate callback address
     *
     * @param type Login type
     * @return
     */
    protected String getCallBackUrl(String type) {
        // Concatenate the callback domain name
        String domain = domainHelper.getCallback();
        String calback;
        // If the member center is bound, the callback address is the callback address of the member center
        if (UserContext.getBuyer() != null ) {

            if(!"QQ".equals(type)){
                calback = domain + "/passport/account-binder/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
            }else{
                if (isWap()) {
                    calback = domain + "/passport/connect/wap/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
                } else {
                    calback = domain + "/passport/connect/pc/" + type + "/callback?uid=" + UserContext.getBuyer().getUid();
                }
            }
        } else {
            if (isWap()) {
                calback = domain + "/passport/connect/wap/" + type + "/callback";
            } else {
                calback = domain + "/passport/connect/pc/" + type + "/callback";
            }
        }
        debugger.log("generatecallback：", calback);

        return calback;

    }

    /**
     * Assemble configuration parameters
     *
     * @return
     */
    public abstract ConnectSettingVO assembleConfig();

    /**
     * Determine whether or notwapaccess
     *
     * @return Whether it iswap
     */
    private boolean isWap() {
        boolean flag = false;

        String header = ThreadContextHolder.getHttpRequest().getHeader("User-Agent");
        if (header == null) {
            return flag;
        }

        String[] keywords = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser", "Mobile"};
        for (String s : keywords) {
            if (header.contains(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
