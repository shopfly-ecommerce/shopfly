/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 2019-02-21 上午11:04
 */
@Component
public class WeixinSignaturer {


    /**
     * 微信访问 签名内容
     */
    public static final String SIGNATURE_PARAMS = "singnature_params_";

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private Cache<SignatureParams> cache;

    /**
     * 获取微信签名
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
                //获取access
                signatureParams.setWechatAccessToken(this.getAccessToken(config.get("app_id"), config.get("app_key")));
                signatureParams.setWechatJsapiTicket(getJsapiTicket(signatureParams.getWechatAccessToken().getAccessToken()));
                signatureParams.setAppId(config.get("app_id"));
                //这个方法调用，其实前端主要使用到的ticket 所以这里暂时用ticket的有效时间，并且做10秒缓冲。
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
                logger.debug("签名参数：" + stringBuffer.toString());
            }
            map.put("timestamp", timestamp);
            map.put("nonceStr", nonceStr);
            map.put("signature", SHA1.encode(stringBuffer.toString()));
            map.put("appid", signatureParams.getAppId());
            if (logger.isDebugEnabled()) {
                logger.debug("map参数：" + map);
            }
        } catch (WeixinSignatrueExceprion e) {
            logger.debug("未开启签名配置2");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeixinSignatrueExceprion("微信签名异常错误");
        }
        return map;
    }

    /**
     * 获取微信配置参数
     *
     * @param type 枚举之详情见  WechatTypeEnmu
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
            logger.debug("未开启签名配置2");
            throw new WeixinSignatrueExceprion("未开启签名配置");
        }
        Map result = new HashMap();
        result.put("app_id", appId);
        result.put("app_key", appKey);

        return result;
    }


    /**
     * 生成access token
     *
     * @param appid
     * @param secret
     * @return
     */
    public WechatAccessToken getAccessToken(String appid, String secret) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret + "&code=CODE&grant_type=authorization_code");
            if (logger.isDebugEnabled()) {
                logger.debug("获取access_token响应:" + content);
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
            throw new WeixinSignatrueExceprion("微信签名access_token异常，请检查");
        }
    }

    /**
     * 生成jsapi ticket
     *
     * @param accessToken
     * @return
     */
    public WechatJsapiTicket getJsapiTicket(String accessToken) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            if (logger.isDebugEnabled()) {
                logger.debug("获取ticket响应:" + content);
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
            throw new WeixinSignatrueExceprion("微信签名：jsapi ticket获取失败");
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
//        //获取access
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