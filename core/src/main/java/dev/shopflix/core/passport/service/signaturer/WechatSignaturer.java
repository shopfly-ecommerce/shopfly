/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.passport.service.signaturer;

import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.core.member.model.dos.ConnectDO;
import dev.shopflix.core.member.model.enums.ConnectTypeEnum;
import dev.shopflix.core.member.model.vo.Auth2Token;
import dev.shopflix.core.member.service.ConnectManager;
import dev.shopflix.core.passport.model.dto.WechatDTO;
import dev.shopflix.core.payment.plugin.weixin.exception.WeixinSignatrueExceprion;
import dev.shopflix.core.payment.plugin.weixin.signaturer.WechatTypeEnmu;
import dev.shopflix.core.payment.plugin.weixin.signaturer.model.SignatureParams;
import dev.shopflix.core.payment.plugin.weixin.signaturer.model.WechatAccessToken;
import dev.shopflix.core.payment.plugin.weixin.signaturer.model.WechatJsapiTicket;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.ThreadContextHolder;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.logs.Logger;
import dev.shopflix.framework.logs.LoggerFactory;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.HttpUtils;
import dev.shopflix.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信签名工具
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-21 上午11:04
 */
@Component
public class WechatSignaturer {


    @Autowired
    private DomainHelper domainHelper;
    @Autowired
    protected Debugger debugger;
    /**
     * 微信访问 平台签名key前缀
     */
    private static final String PLATEFORM_SIGNATURE = "singnature_plateform_";

    /**
     * 微信访问 buyer签名key前缀
     */
    private static final String BUYER_SIGNATURE = "singnature_buyer_";

    protected final Logger logger = LoggerFactory.getLogger(WechatSignaturer.class);

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private Cache cache;

    /**
     * 获取 微信签名 前端调用分享等功能权限
     *
     * @param type @see  com.enation.app.javashop.service.passport.signaturer.WechatTypeEnmu
     *             WAP("WAP"),
     *             REACT("原生"),
     *             NATIVE("NAAPP"),
     *             MINI("小程序");
     * @param url  需要签名等url
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
                //获取access
                signatureParams.setWechatAccessToken(this.getCgiAccessToken(config.get("app_id"), config.get("app_key")));
                signatureParams.setWechatJsapiTicket(getJsapiTicket(signatureParams.getWechatAccessToken().getAccessToken()));
                signatureParams.setAppId(config.get("app_id"));
                //这个方法调用，其实前端主要使用到的ticket 所以这里暂时用ticket的有效时间，并且做10秒缓冲。
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
            logger.debug("签名参数：" + stringBuffer.toString());
            map.put("timestamp", timestamp);
            map.put("nonceStr", nonceStr);
            map.put("signature", SHA1.encode(stringBuffer.toString()));
            map.put("appid", ((SignatureParams) object).getAppId());
            logger.debug("map参数：" + map);
        } catch (WeixinSignatrueExceprion e) {
            logger.debug("未开启签名配置2");
            this.logger.error("微信签名异常：", e);
            throw e;
        } catch (Exception e) {
            this.logger.error("微信签名异常：", e);
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
            logger.debug("未开启签名配置2");
            throw new WeixinSignatrueExceprion("未开启签名配置");
        }
        Map result = new HashMap();
        result.put("app_id", appId);
        result.put("app_key", appKey);

        return result;
    }


    /**
     * 获取买家accesstoken
     *
     * @param client
     * @return
     */
    public Auth2Token getCallbackAccessToken(String client) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        //获取code
        String code = request.getParameter("code");
        //回传uuid state
        String uuid = request.getParameter("state");

        //pc使用的是开放平台，微信端使用的是公众平台，参数是不一致
        String ua = request.getHeader("user-agent").toLowerCase();

        String appId = null;
        String key = null;
        Map map;
        WechatTypeEnmu wechatEnum = WechatTypeEnmu.NATIVE;
        //如果是微信浏览器则获取 微信网页端参数
        if (ua.indexOf("micromessenger") > 0) {
            debugger.log("是微信浏览器");
            wechatEnum = WechatTypeEnmu.WAP;
        } else if (client != null) {
            wechatEnum = WechatTypeEnmu.valueOf(client.toUpperCase());
        }

        map = this.getConnectConfig(wechatEnum.name());

        appId = StringUtil.toString(map.get("app_id"));
        key = StringUtil.toString(map.get("app_key"));
        //文档见
        //通过code获取access_token及openid
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appId +
                "&secret=" + key +
                "&code=" + code +
                "&grant_type=authorization_code";

        debugger.log("生成获取access_token url: ");
        debugger.log(url);
        debugger.log("向微信发起请求");
        String content = HttpUtils.doGet(url, "UTF-8", 100, 1000);
        debugger.log("微信返回内容为：");
        debugger.log(content);
        //获取openid
        JSONObject json = JSONObject.fromObject(content);
        String accessToken = json.getString("access_token");
        String unionId = json.getString("unionid");
        String refreshToken = json.getString("refresh_token");
        Integer expires = json.getInt("expires_in");


        //将信息封装到对象当中
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
        // 存储token 这里说明一下存储策略：
        // 因为刷新token有效时间是1个月，但是买家不可能一个月不退出微信浏览器，所以这里为了缓存的占用问题，将token存储时间设置为24小时
        this.cache.put(WechatSignaturer.BUYER_SIGNATURE + uuid, auth2Token, expires * 12);

        debugger.log("生成accessToken:");
        debugger.log(accessToken);

        return auth2Token;
    }


    /**
     * 赋值微信用户信息
     *
     * @param accessToken token
     * @param openId      opneid
     * @return
     */
    public JSONObject getWechatInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openId;
        //通过openid获取userinfo
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(content);
        return jsonObject;
    }


    /**
     * 生成 CGI 接口 access token ，服务器与微信的接口token
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
     * 生成 CGI 接口 access token ，服务器与微信的接口token
     *
     * @param appid
     * @param secret
     * @return
     */
    public WechatAccessToken getCgiAccessToken(String appid, String secret) {

        if(StringUtil.isEmpty(appid)|| StringUtil.isEmpty(secret)){
            throw new WeixinSignatrueExceprion("微信网页端参数没有配置，请前往信任登录正确配置");
        }
        String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret + "&code=CODE&grant_type=authorization_code");
        logger.debug("获取access_token响应:" + content);
        JSONObject object = JSONObject.fromObject(content);
        if(object.get("access_token")==null){
            throw new WeixinSignatrueExceprion("未获取到正确的认证信息，请联系管理员查看日志解决");
        }
        String accessToken = object.get("access_token").toString();
        String expires = object.get("expires_in").toString();

        WechatAccessToken wechatAccessToken = new WechatAccessToken();
        wechatAccessToken.setAccessToken(accessToken);
        wechatAccessToken.setExpires(Integer.valueOf(expires));
        return wechatAccessToken;
    }

    /**
     * 生成jsapi ticket 用于前端分享接口，调用微信api的微信分享之类功能所需
     *
     * @param accessToken
     * @return
     */
    public WechatJsapiTicket getJsapiTicket(String accessToken) {

        try {
            String content = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi");
            logger.debug("获取ticket响应:" + content);
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


    /**
     * 获取缓存中的 buyer token
     *
     * @param uuid
     * @return
     */
    public Auth2Token getSnsAccessToken(String uuid) {
        Object auth2Token = this.cache.get(BUYER_SIGNATURE + uuid);
        if (auth2Token != null) {
            Auth2Token token = (Auth2Token) auth2Token;
            //如果token在有效期，直接返回
            if (token.getExpires() > DateUtil.getDateline()) {
                return token;
            } else {
                String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + token.getAppid() +
                        "&grant_type=refresh_token&refresh_token=" + token.getRefreshToken();
                debugger.log("刷新获取access_token url: ");
                debugger.log(url);
                debugger.log("向微信发起请求");
                String content = HttpUtils.doGet(url, "UTF-8", 100, 1000);
                debugger.log("微信返回内容为：");
                debugger.log(content);
                //获取openid
                JSONObject json = JSONObject.fromObject(content);
                String openid = json.getString("openid");
                String accessToken = json.getString("access_token");
                String unionId = json.getString("unionid");
                String refreshToken = json.getString("refresh_token");
                Integer expires = json.getInt("expires_in");
                //将信息封装到对象当中
                token.setUnionid(unionId);
                token.setOpneId(openid);
                token.setAccessToken(accessToken);
                token.setRefreshToken(refreshToken);
                token.setType(ConnectTypeEnum.WECHAT.value());
                token.setExpires(expires + DateUtil.getDateline());

                // 存储token 这里说明一下存储策略：
                // 因为刷新token有效时间是1个月，但是买家不可能一个月不退出微信浏览器，所以这里为了缓存的占用问题，将token存储时间设置为24小时
                this.cache.put(WechatSignaturer.BUYER_SIGNATURE + uuid, token, expires * 12);
                debugger.log("生成accessToken:");
                debugger.log(accessToken);
                return token;
            }
        }
        return null;
    }

    /**
     * 尝试获取缓存中的token，组成dto为调用者使用
     *
     * @param uuid 买家uuid
     * @return
     */
    public WechatDTO getAccesstoken(String uuid) {

        Auth2Token auth2Token = this.getSnsAccessToken(uuid);
        //如果为空，则重新获取，返回跳转的地址
        if (auth2Token == null) {
            WechatDTO wechatDTO = new WechatDTO();
            wechatDTO.setNeedRedirect(true);
            wechatDTO.setRedirectUrl(this.getAuthorizeUrl(null));
            return wechatDTO;
        } else {
            //返回对应的token，包含openid 等参数
            WechatDTO wechatDTO = new WechatDTO();
            wechatDTO.setAuth2Token(auth2Token);
            wechatDTO.setNeedRedirect(false);
            return wechatDTO;
        }


    }


    /**
     * 获取买家 openid 这里是通过数据库中查询的结果
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
     * 获取微信授权url
     *
     * @param callbackUrl 非 微信浏览器跳转时，需要自定义回调地址。
     *                    注：如果调用方在非微信浏览器环境中进行调用，并且传递回调地址，以调用房回调地址为准
     * @return
     */
    public String getAuthorizeUrl(String callbackUrl) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        //获取买家 uuid
        String uuid = request.getParameter("uuid");
        String ua = request.getHeader("user-agent").toLowerCase();

        //回调地址 如果为空，则方法内部制定回调地址，否则 根据调用方法地址进行回调
        String callBack = StringUtil.isEmpty(callbackUrl) ?
                domainHelper.getCallback() + "/passport/connect/wechat/auth/back" : callbackUrl;


        //微信浏览器
        if (ua.indexOf("micromessenger") > -1) {
            /**
             文档见 https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842 这里主要获取买家授权平台code
             应当使用https链接来确保授权code的安全性
             state 这个参数，是微信回调会回传的参数，这里选择携带 买家uuid
             */
            Map map = this.getConnectConfig(WechatTypeEnmu.WAP.name());
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                    "?appid=" + map.get("app_id").toString() +
                    "&redirect_uri=" + callBack +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=" + uuid
                    + "#wechat_redirect";

            debugger.log("确定是微信浏览器,生成跳转地址为：");
            debugger.log(url);
            return url;

        } //非微信浏览器
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
                debugger.log("非微信浏览器,生成跳转地址为：");
                debugger.log(url);
                return url;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                debugger.log("非微信浏览器,生成跳转地址异常");
            }
            return null;
        }
    }

}
