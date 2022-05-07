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

import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.payment.plugin.weixin.exception.WeixinSignatrueExceprion;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.SignatureParams;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.WechatAccessToken;
import cloud.shopfly.b2c.core.payment.plugin.weixin.signaturer.model.WechatJsapiTicket;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * WexinSignaturer
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-21 In the morning11:04
 */
@Component
public class WeixinSignaturer {


    /**
     * Wechat access signature content
     */
    public static final String SIGNATURE_PARAMS = "singnature_params_";

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private Cache<SignatureParams> cache;

    /**
     * Get wechat signature
     *
     * @param type 1:wap  /  2:mini   /  3:naapp   /  4 :Reactt app
     * @return
     */
    public Map signature(String type, String url) {


        Map<String, String> map = new HashMap<>(4);

        String nonceStr = StringUtil.getRandStr(16);
        String timestamp = Long.toString(DateUtil.getDateline());
        try {
            SignatureParams signatureParams = cache.get(WeixinSignaturer.SIGNATURE_PARAMS + type);
            if (signatureParams == null) {
                Map<String, String> config = getConfig(type);

                signatureParams = new SignatureParams();
                // To obtain access
                signatureParams.setWechatAccessToken(this.getAccessToken(config.get("app_id"), config.get("app_key")));
                signatureParams.setWechatJsapiTicket(getJsapiTicket(signatureParams.getWechatAccessToken().getAccessToken()));
                signatureParams.setAppId(config.get("app_id"));
                // This method call, in fact, the front end is mainly using ticket so were going to temporarily use the valid time of ticket, and were going to buffer it for 10 seconds.
                cache.put(SIGNATURE_PARAMS + type, signatureParams, signatureParams.getWechatJsapiTicket().getExpires() - 10);
            }
            StringBuffer stringBuffer = new StringBuffer("jsapi_ticket=");
            stringBuffer.append(signatureParams.getWechatJsapiTicket().getJsapiTicket() + "&");
            stringBuffer.append("noncestr=");
            stringBuffer.append(nonceStr + "&");
            stringBuffer.append("timestamp=");
            stringBuffer.append(timestamp + "&");
            stringBuffer.append("url=");
            stringBuffer.append(url.replaceAll("&amp;", "&"));
            if (logger.isDebugEnabled()) {
                logger.debug("Signature parameters：" + stringBuffer.toString());
            }
            map.put("timestamp", timestamp);
            map.put("nonceStr", nonceStr);
            map.put("signature", SHA1.encode(stringBuffer.toString()));
            map.put("appid", signatureParams.getAppId());
            if (logger.isDebugEnabled()) {
                logger.debug("mapparameter：" + map);
            }
        } catch (WeixinSignatrueExceprion e) {
            logger.debug("Signature configuration is not enabled2");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
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
    public Map<String, String> getConfig(String type) {

        Map<String, String> map = connectManager.initConnectSetting();

        String appId = "";
        String appKey = "";
        if (type.equals(WechatTypeEnmu.WAP.name())) {
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
            logger.debug("Signature configuration is not enabled2");
            throw new WeixinSignatrueExceprion("Signature configuration is not enabled");
        }
        Map result = new HashMap();
        result.put("app_id", appId);
        result.put("app_key", appKey);

        return result;
    }


    /**
     * generateaccess token
     *
     * @param appid
     * @param secret
     * @return
     */
    public WechatAccessToken getAccessToken(String appid, String secret) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret + "&code=CODE&grant_type=authorization_code");
            if (logger.isDebugEnabled()) {
                logger.debug("To obtainaccess_tokenThe response:" + content);
            }
            JSONObject object = JSONObject.fromObject(content);
            String accessToken = object.get("access_token").toString();
            String expires = object.get("expires_in").toString();

            WechatAccessToken wechatAccessToken = new WechatAccessToken();
            wechatAccessToken.setAccessToken(accessToken);
            wechatAccessToken.setExpires(Integer.valueOf(expires));
            return wechatAccessToken;

        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinSignatrueExceprion("WeChat signatureaccess_tokenIf no, check it");
        }
    }

    /**
     * generatejsapi ticket
     *
     * @param accessToken
     * @return
     */
    public WechatJsapiTicket getJsapiTicket(String accessToken) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            if (logger.isDebugEnabled()) {
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


//    public static void main(String[] args) {
//
//        String content = "{\"errcode\":0,\"errmsg\":\"ok\",\"ticket\":\"kgt8ON7yVITDhtdwci0qeQkH-fpczIYiHBqcGjN36Y8kPl1rUGUDAMOrAB9zmlxddTsQ6R-wABKWWTG_tEmWiA\",\"expires_in\":7200}";
//
//        JSONObject object = JSONObject.fromObject(content);
//        String ticket = object.get("ticket").toString();
//        String expires = object.get("expires_in").toString();
//        WechatJsapiTicket wechatJsapiTicket = new WechatJsapiTicket();
//        wechatJsapiTicket.setJsapiTicket(ticket);
//        wechatJsapiTicket.setExpires(Integer.parseInt(expires));
//
//
//        SignatureParams signatureParams = new SignatureParams();
//        //To obtainaccess
//        signatureParams.setWechatJsapiTicket(wechatJsapiTicket);
//        signatureParams.setAppId("wxdb95bb2d5b8621a3");
//
//        String noncestr = StringUtil.getRandStr(16);
//        String timestamp = Long.toString(DateUtil.getDateline());
//
//        StringBuffer stringBuffer = new StringBuffer("jsapi_ticket=");
//        stringBuffer.append(signatureParams.getWechatJsapiTicket().getJsapiTicket() + "&");
//        stringBuffer.append("noncestr=");
//        stringBuffer.append(noncestr + "&");
//        stringBuffer.append("timestamp=");
//        stringBuffer.append(timestamp + "&");
//        stringBuffer.append("url=");
//        stringBuffer.append(url);
//
//        map.put("timestamp", timestamp);
//        map.put("noncestr", noncestr);
//        String ss = DigestUtils.sha1(stringBuffer.toString()).toString();
//        map.put("signature", SHA1.encode(stringBuffer.toString()).toString());
//        map.put("appid", signatureParams.getAppId());
//
//    }

}
