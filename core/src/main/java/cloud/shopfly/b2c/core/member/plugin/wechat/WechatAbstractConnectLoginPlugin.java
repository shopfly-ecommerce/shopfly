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
package cloud.shopfly.b2c.core.member.plugin.wechat;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.enums.WechatConnectConfigGroupEnum;
import cloud.shopfly.b2c.core.member.model.enums.WechatConnectConfigItemEnm;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingConfigItem;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingParametersVO;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.member.service.impl.AbstractConnectLoginPlugin;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zjp
 * @version v7.0
 * @Description 微信信任登录插件类
 * @ClassName WechatAbstractConnectLoginPlugin
 * @since v7.0 上午11:18 2018/6/5
 */
@Component
public class WechatAbstractConnectLoginPlugin extends AbstractConnectLoginPlugin {

    @Autowired
    private DomainHelper domainHelper;

    public WechatAbstractConnectLoginPlugin() {
        super();
    }


    @Override
    public String getLoginUrl() {

        //获取参数
        Map map = initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();


        String uuid = UUID.randomUUID().toString();
        String ua = request.getHeader("user-agent").toLowerCase();

        //微信浏览器
        if (ua.indexOf("micromessenger") > -1) {
            String callBack = domainHelper.getCallback() + "/passport/connect/wechat/auth/back";

            String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                    "?appid=" + map.get("wechat_wechat_app_id").toString() +
                    "&redirect_uri=" + callBack +
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=123#wechat_redirect";

            debugger.log("确定是微信浏览器,生成跳转地址为：");
            debugger.log(url);

            return url;
            //非微信浏览器
        } else {

            String callBack = this.getCallBackUrl(ConnectTypeEnum.WECHAT.value());

            String url = "https://open.weixin.qq.com/connect/qrconnect?" +
                    "appid=" + map.get("wechat_pc_app_id").toString() +
                    "&redirect_uri=" + callBack +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=" + uuid +
                    "&connect_redirect=1#wechat_redirect";

            debugger.log("非微信浏览器,生成跳转地址为：");
            debugger.log(url);

            return url;
        }
    }

    @Override
    public Auth2Token loginCallback() {
        Map map = initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        //获取code
        String code = request.getParameter("code");

        //pc使用的是开放平台，微信端使用的是公众平台，参数是不一致
        String appId = StringUtil.toString(map.get("wechat_pc_app_id"));
        String key = StringUtil.toString(map.get("wechat_pc_app_key"));
        String ua = request.getHeader("user-agent").toLowerCase();

        //如果是微信浏览器则获取 微信网页端参数
        if (ua.indexOf("micromessenger") > 0) {
            debugger.log("是微信浏览器");
            appId = StringUtil.toString(map.get("wechat_wechat_app_id"));
            key = StringUtil.toString(map.get("wechat_wechat_app_key"));
        }

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
        String openid = json.getString("openid");
        String accessToken = json.getString("access_token");
        String unionId = json.getString("unionid");

        //将信息封装到对象当中
        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setUnionid(unionId);
        auth2Token.setOpneId(openid);
        auth2Token.setAccessToken(accessToken);
        auth2Token.setType(ConnectTypeEnum.WECHAT.value());

        debugger.log("生成accessToken:");
        debugger.log(accessToken);

        return auth2Token;

    }

    @Override
    public Member fillInformation(Auth2Token auth2Token, Member member) {

        Map map = initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        JSONObject jsonObject = this.getWechatInfo(auth2Token.getAccessToken(), auth2Token.getOpneId());
        member.setNickname(jsonObject.getString("nickname"));
        member.setFace(jsonObject.getString("headimgurl"));
        String sex = jsonObject.getString("sex");
        if ("1".equals(sex)) {
            member.setSex(1);
        } else {
            member.setSex(0);
        }
        return member;
    }

    @Override
    public ConnectSettingVO assembleConfig() {
        ConnectSettingVO connectSetting = new ConnectSettingVO();
        List<ConnectSettingParametersVO> list = new ArrayList<>();
        for (WechatConnectConfigGroupEnum wechatConnectConfigGroupEnum : WechatConnectConfigGroupEnum.values()) {
            ConnectSettingParametersVO connectSettingParametersVO = new ConnectSettingParametersVO();
            List<ConnectSettingConfigItem> lists = new ArrayList<>();
            for (WechatConnectConfigItemEnm wechatConnectConfigItem : WechatConnectConfigItemEnm.values()) {
                ConnectSettingConfigItem connectSettingConfigItem = new ConnectSettingConfigItem();
                connectSettingConfigItem.setKey("wechat_" + wechatConnectConfigGroupEnum.value() + "_" + wechatConnectConfigItem.value());
                connectSettingConfigItem.setName(wechatConnectConfigItem.getText());
                lists.add(connectSettingConfigItem);
            }
            connectSettingParametersVO.setConfigList(lists);
            connectSettingParametersVO.setName(wechatConnectConfigGroupEnum.getText());
            list.add(connectSettingParametersVO);
        }
        connectSetting.setName("微信参数配置");
        connectSetting.setType(ConnectTypeEnum.WECHAT.value());
        connectSetting.setConfig(JsonUtil.objectToJson(list));
        return connectSetting;
    }

    /**
     * 获取微信用户信息
     *
     * @param accessToken token
     * @param openId      opneid
     * @return
     */
    private JSONObject getWechatInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openId;
        //通过openid获取userinfo
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(content);
        return jsonObject;
    }

    /**
     * 小程序自动登录
     *
     * @return
     */
    public String miniProgramAutoLogin(String code) {

        Map map = initConnectSetting();

        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + map.get("wechat_miniprogram_app_id") + "&" +
                "secret=" + map.get("wechat_miniprogram_app_key") + "&" +
                "js_code=" + code + "&" +
                "grant_type=authorization_code";
        String content = HttpUtils.doGet(url, "UTF-8", 100, 1000);

        return content;
    }

    /**
     * 获取accesstocken
     *
     * @return
     */
    public String getWXAccessTocken() {

        Map map = initConnectSetting();

        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&" +
                "appid=" + map.get("wechat_miniprogram_app_id") + "&" +
                "secret=" + map.get("wechat_miniprogram_app_key");

        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);

        //{"access_token":"16_OXa8nmH-APkaE3KhJUQz_rjSgOoFO0fF5e4GZiFesrPmLamCaIqGf_F1lCGX_gwKnwya0wkxjpNeQwtKtX9PGQ-FfKrlkEAIWwelHXB1S5zuGfFzgsLWHgpFxRA3gmAG_SJmVw7E14BSwAx5MOBfACAFHU","expires_in":7200}

        JSONObject object = JSONObject.fromObject(content);

        return object.get("access_token").toString();
    }


}
