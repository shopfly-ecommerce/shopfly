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
package cloud.shopfly.b2c.core.passport.service.signaturer;

import cloud.shopfly.b2c.core.member.model.dos.ConnectDO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.passport.model.dto.WechatDTO;
import cloud.shopfly.b2c.core.payment.plugin.weixin.exception.WeixinSignatrueExceprion;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.WechatTypeEnmu;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.SignatureParams;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.WechatAccessToken;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.WechatJsapiTicket;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.logs.Debugger;

import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Wechat signature tool
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-21 In the morning11:04
 */
@Component
public class WechatSignaturer {


    @Autowired
    private DomainHelper domainHelper;
    @Autowired
    protected Debugger debugger;
    /**
     * Wechat access platform signaturekeyThe prefix
     */
    private static final String PLATEFORM_SIGNATURE = "singnature_plateform_";

    /**
     * WeChat accessbuyerThe signaturekeyThe prefix
     */
    private static final String BUYER_SIGNATURE = "singnature_buyer_";

    protected final Logger logger = LoggerFactory.getLogger(WechatSignaturer.class.getName());

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private Cache cache;

    /**
     * Obtain the rights of wechat signature front-end call sharing and other functions
     *
     * @param type @see  cloud.shopfly.b2c.service.passport.signaturer.WechatTypeEnmu
     *             WAP("WAP"),
     *             REACT("native"),
     *             NATIVE("NAAPP"),
     *             MINI("Small program");
     * @param url  You need signatures and so onurl
     * @return
     */
    public Map signature(String type, String url) {

        Map<String, String> map = new HashMap<>(4);

        String nonceStr = StringUtil.getRandStr(16);
        String timestamp = Long.toString(DateUtil.getDateline());
        try {
            Object object = cache.get(WechatSignaturer.PLATEFORM_SIGNATURE + type);
            if (object == null) {
                Map<String, String> config = getConnectConfig(type);

                SignatureParams signatureParams = new SignatureParams();
                // To obtain access
                signatureParams.setWechatAccessToken(this.getCgiAccessToken(config.get("app_id"), config.get("app_key")));
                signatureParams.setWechatJsapiTicket(getJsapiTicket(signatureParams.getWechatAccessToken().getAccessToken()));
                signatureParams.setAppId(config.get("app_id"));
                // This method call, in fact, the front end is mainly using ticket so were going to temporarily use the valid time of ticket, and were going to buffer it for 10 seconds.
                cache.put(PLATEFORM_SIGNATURE + type, signatureParams, signatureParams.getWechatJsapiTicket().getExpires() - 100);
                object = signatureParams;
            }
            StringBuffer stringBuffer = new StringBuffer("jsapi_ticket=");
            stringBuffer.append(((SignatureParams) object).getWechatJsapiTicket().getJsapiTicket() + "&");
            stringBuffer.append("noncestr=");
            stringBuffer.append(nonceStr + "&");
            stringBuffer.append("timestamp=");
            stringBuffer.append(timestamp + "&");
            stringBuffer.append("url=");
            stringBuffer.append(url.replaceAll("&amp;", "&"));
            if(logger.isDebugEnabled()){
                logger.debug("Signature parameters：" + stringBuffer.toString());
            }
            map.put("timestamp", timestamp);
            map.put("nonceStr", nonceStr);
            map.put("signature", SHA1.encode(stringBuffer.toString()));
            map.put("appid", ((SignatureParams) object).getAppId());
            if(logger.isDebugEnabled()){
                logger.debug("mapparameter：" + map);
            }
        } catch (WeixinSignatrueExceprion e) {
            if(logger.isDebugEnabled()){
                logger.debug("Signature configuration is not enabled2");
            }
            this.logger.error("The wechat signature is abnormal：", e);
            throw e;
        } catch (Exception e) {
            this.logger.error("The wechat signature is abnormal：", e);
            throw new WeixinSignatrueExceprion("The wechat signature is abnormal");
        }
        return map;
    }

    /**
     * Get wechat configuration parameters
     *
     * @param type See enumeration for detailsWechatTypeEnmu
     * @return
     */
    public Map<String, String> getConnectConfig(String type) {

        Map<String, String> map = connectManager.initConnectSetting();

        String appId = "";
        String appKey = "";
        if (type.equals(WechatTypeEnmu.PC.name())) {
            appId = map.get("wechat_pc_app_id");
            appKey = map.get("wechat_pc_app_key");
        } else if (type.equals(WechatTypeEnmu.WAP.name())) {
            appId = map.get("wechat_wechat_app_id");
            appKey = map.get("wechat_wechat_app_key");
        } else if (type.equals(WechatTypeEnmu.MINI.name())) {
            appId = map.get("wechat_miniprogram_app_id");
            appKey = map.get("wechat_miniprogram_app_key");
        } else if (type.equals(WechatTypeEnmu.NATIVE.name())) {
            appId = map.get("wechat_app_app_id");
            appKey = map.get("wechat_app_app_key");

        } else if (type.equals(WechatTypeEnmu.REACT.name())) {
            appId = map.get("wechat_rn_app_id");
            appKey = map.get("wechat_rn_app_key");
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("Signature configuration is not enabled2");
            }
            throw new WeixinSignatrueExceprion("Signature configuration is not enabled");
        }
        Map result = new HashMap();
        result.put("app_id", appId);
        result.put("app_key", appKey);

        return result;
    }


    /**
     * Access to the buyeraccesstoken
     *
     * @param client
     * @return
     */
    public Auth2Token getCallbackAccessToken(String client) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        // Access code
        String code = request.getParameter("code");
        // Back uuid state
        String uuid = request.getParameter("state");

        // The PC uses an open platform, while the wechat terminal uses a public platform, and the parameters are inconsistent
        String ua = request.getHeader("user-agent").toLowerCase();

        String appId = null;
        String key = null;
        Map map;
        WechatTypeEnmu wechatEnum = WechatTypeEnmu.NATIVE;
        // If it is wechat browser, get the parameters of wechat web page
        if (ua.indexOf("micromessenger") > 0) {
            debugger.log("Its wechat browser");
            wechatEnum = WechatTypeEnmu.WAP;
        } else if (client != null) {
            wechatEnum = WechatTypeEnmu.valueOf(client.toUpperCase());
        }

        map = this.getConnectConfig(wechatEnum.name());

        appId = StringUtil.toString(map.get("app_id"));
        key = StringUtil.toString(map.get("app_key"));
        // See the document
        // Obtain the access_token and OpenID by code
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appId +
                "&secret=" + key +
                "&code=" + code +
                "&grant_type=authorization_code";

        debugger.log("For gettingaccess_token url: ");
        debugger.log(url);
        debugger.log("Send a request to wechat");
        String content = HttpUtils.doGet(url, "UTF-8", 100, 1000);
        debugger.log("Wechat returns the content as：");
        debugger.log(content);
        // To obtain the openid
        JSONObject json = JSONObject.fromObject(content);
        String accessToken = json.getString("access_token");
        String unionId = json.getString("unionid");
        String refreshToken = json.getString("refresh_token");
        Integer expires = json.getInt("expires_in");


        // Encapsulate information into objects
        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setUnionid(unionId);
        if(!WechatTypeEnmu.PC.equals(wechatEnum)){
            String openid = json.getString("openid");
            auth2Token.setOpneId(openid);
        }
        auth2Token.setAccessToken(accessToken);
        auth2Token.setRefreshToken(refreshToken);
        auth2Token.setType(ConnectTypeEnum.WECHAT.value());
        auth2Token.setExpires(expires + DateUtil.getDateline());
        auth2Token.setAppid(appId);
        // Storage token The storage policy is as follows:
        // The valid time of refreshing the token is one month, but the buyer cannot quit the wechat browser for a month. Therefore, in order to occupy the cache, the token storage time is set to 24 hours
        this.cache.put(WechatSignaturer.BUYER_SIGNATURE + uuid, auth2Token, expires * 12);

        debugger.log("generateaccessToken:");
        debugger.log(accessToken);

        return auth2Token;
    }


    /**
     * Assign wechat user information
     *
     * @param accessToken token
     * @param openId      opneid
     * @return
     */
    public JSONObject getWechatInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openId;
        // Obtain userInfo from openID
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(content);
        return jsonObject;
    }


    /**
     * generateCGI interfaceaccess token ，服务器与微信的interfacetoken
     *
     * @param wechatTypeEnmu
     * @return
     */
    public String getCgiAccessToken(WechatTypeEnmu wechatTypeEnmu) {
        Map<String, String> map = this.getConnectConfig(wechatTypeEnmu.name());
        WechatAccessToken wechatAccessToken = this.getCgiAccessToken(map.get("app_id"), map.get("app_key"));
        if (wechatAccessToken == null) {
            return null;
        }
        return wechatAccessToken.getAccessToken();
    }

    /**
     * generateCGI interfaceaccess token ，服务器与微信的interfacetoken
     *
     * @param appid
     * @param secret
     * @return
     */
    public WechatAccessToken getCgiAccessToken(String appid, String secret) {

        if(StringUtil.isEmpty(appid)|| StringUtil.isEmpty(secret)){
            throw new WeixinSignatrueExceprion("Wechat web page parameters are not configured, please go to the trusted login to configure correctly");
        }
        String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret + "&code=CODE&grant_type=authorization_code");
        if(logger.isDebugEnabled()){
            logger.debug("To obtainaccess_tokenThe response:" + content);
        }
        JSONObject object = JSONObject.fromObject(content);
        if(object.get("access_token")==null){
            throw new WeixinSignatrueExceprion("If no correct authentication information is obtained, contact the administrator to view logs");
        }
        String accessToken = object.get("access_token").toString();
        String expires = object.get("expires_in").toString();

        WechatAccessToken wechatAccessToken = new WechatAccessToken();
        wechatAccessToken.setAccessToken(accessToken);
        wechatAccessToken.setExpires(Integer.valueOf(expires));
        return wechatAccessToken;
    }

    /**
     * generatejsapi ticket Used for front-end sharing interface, call wechatapiFor functions like wechat sharing
     *
     * @param accessToken
     * @return
     */
    public WechatJsapiTicket getJsapiTicket(String accessToken) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            if(logger.isDebugEnabled()){
                logger.debug("To obtainticketThe response:" + content);
            }
            JSONObject object = JSONObject.fromObject(content);
            String ticket = object.get("ticket").toString();
            String expires = object.get("expires_in").toString();
            WechatJsapiTicket wechatJsapiTicket = new WechatJsapiTicket();
            wechatJsapiTicket.setJsapiTicket(ticket);
            wechatJsapiTicket.setExpires(Integer.parseInt(expires));
            return wechatJsapiTicket;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinSignatrueExceprion("WeChat signature：jsapi ticketFor failure");
        }
    }


    /**
     * Get the cachebuyer token
     *
     * @param uuid
     * @return
     */
    public Auth2Token getSnsAccessToken(String uuid) {
        Object auth2Token = this.cache.get(BUYER_SIGNATURE + uuid);
        if (auth2Token != null) {
            Auth2Token token = (Auth2Token) auth2Token;
            // If the token is valid, return it
            if (token.getExpires() > DateUtil.getDateline()) {
                return token;
            } else {
                String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + token.getAppid() +
                        "&grant_type=refresh_token&refresh_token=" + token.getRefreshToken();
                debugger.log("The refresh to getaccess_token url: ");
                debugger.log(url);
                debugger.log("Send a request to wechat");
                String content = HttpUtils.doGet(url, "UTF-8", 100, 1000);
                debugger.log("Wechat returns the content as：");
                debugger.log(content);
                // To obtain the openid
                JSONObject json = JSONObject.fromObject(content);
                String openid = json.getString("openid");
                String accessToken = json.getString("access_token");
                String unionId = json.getString("unionid");
                String refreshToken = json.getString("refresh_token");
                Integer expires = json.getInt("expires_in");
                // Encapsulate information into objects
                token.setUnionid(unionId);
                token.setOpneId(openid);
                token.setAccessToken(accessToken);
                token.setRefreshToken(refreshToken);
                token.setType(ConnectTypeEnum.WECHAT.value());
                token.setExpires(expires + DateUtil.getDateline());

                // Storage token The storage policy is as follows:
                // The valid time of refreshing the token is one month, but the buyer cannot quit the wechat browser for a month. Therefore, in order to occupy the cache, the token storage time is set to 24 hours
                this.cache.put(WechatSignaturer.BUYER_SIGNATURE + uuid, token, expires * 12);
                debugger.log("generateaccessToken:");
                debugger.log(accessToken);
                return token;
            }
        }
        return null;
    }

    /**
     * Try to get the cachetokenOf,dtoUsed by the caller
     *
     * @param uuid buyersuuid
     * @return
     */
    public WechatDTO getAccesstoken(String uuid) {

        Auth2Token auth2Token = this.getSnsAccessToken(uuid);
        // If it is empty, it gets it again and returns the address of the jump
        if (auth2Token == null) {
            WechatDTO wechatDTO = new WechatDTO();
            wechatDTO.setNeedRedirect(true);
            wechatDTO.setRedirectUrl(this.getAuthorizeUrl(null));
            return wechatDTO;
        } else {
            // Return the corresponding token, including openID and other parameters
            WechatDTO wechatDTO = new WechatDTO();
            wechatDTO.setAuth2Token(auth2Token);
            wechatDTO.setNeedRedirect(false);
            return wechatDTO;
        }


    }


    /**
     * Access to the buyeropenid Here is the result of a query through the database
     *
     * @param memberId
     * @return openid
     */
    public String getMemberOpenid(Integer memberId) {
        ConnectDO connectDO = connectManager.getConnect(memberId, ConnectTypeEnum.WECHAT_OPENID.value());
        if (connectDO == null) {
            return null;
        }
        return connectDO.getUnionId();
    }

    /**
     * Obtain wechat authorizationurl
     *
     * @param callbackUrl When non-wechat browser hops, you need to customize the callback address.
     *                    note：If the caller makes the call in a non-wechat browser environment and passes the callback address, the callback address will prevail
     * @return
     */
    public String getAuthorizeUrl(String callbackUrl) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        // Get the buyerS UUID
        String uuid = request.getParameter("uuid");
        String ua = request.getHeader("user-agent").toLowerCase();

        // If the callback address is empty, the callback address is specified internally; otherwise, the callback is based on the calling method address
        String callBack = StringUtil.isEmpty(callbackUrl) ?
                domainHelper.getCallback() + "/passport/connect/wechat/auth/back" : callbackUrl;


        // Wechat browser
        if (ua.indexOf("micromessenger") > -1) {
            /**
             See the documenthttps://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842 Here is mainly to obtain buyer authorization platformcode
             Should be usedhttpsLink to ensure authorizationcodeThe safety of
             state This parameter, is the wechat callback will be returned to the parameter, here select carry buyeruuid
             */
            Map map = this.getConnectConfig(WechatTypeEnmu.WAP.name());
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                    "?appid=" + map.get("app_id").toString() +
                    "&redirect_uri=" + callBack +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=" + uuid
                    + "#wechat_redirect";

            debugger.log("Make sure its wechat browser,The forward address is generated：");
            debugger.log(url);
            return url;

        } //Non-wechat browser
        else {
            Map map = this.getConnectConfig(WechatTypeEnmu.PC.name());
            try {
                String url = "https://open.weixin.qq.com/connect/qrconnect?" +
                        "appid=" + map.get("app_id").toString() +
                        "&redirect_uri=" + URLEncoder.encode(callBack, "GBK") +
                        "&response_type=code" +
                        "&scope=snsapi_login" +
                        "&state=" + uuid +
                        "#wechat_redirect";
                debugger.log("Non-wechat browser,The forward address is generated：");
                debugger.log(url);
                return url;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                debugger.log("Non-wechat browser,The forward address generation is abnormal. Procedure");
            }
            return null;
        }
    }

}
