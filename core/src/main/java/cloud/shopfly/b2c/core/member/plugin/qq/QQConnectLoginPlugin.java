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
package cloud.shopfly.b2c.core.member.plugin.qq;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.enums.QqConnectConfigGroupEnum;
import cloud.shopfly.b2c.core.member.model.enums.QqConnectConfigItemEnum;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingConfigItem;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingParametersVO;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.member.service.impl.AbstractConnectLoginPlugin;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zjp
 * @version v7.0
 * @Description qqTrust the login plug-in class
 * @ClassName QqAbstractConnectLoginPlugin
 * @since v7.0 In the morning11:17 2018/6/5
 */
@Component
public class QQConnectLoginPlugin extends AbstractConnectLoginPlugin {

    private static Pattern unionidPattern = Pattern.compile("\"openid\":\"(.+)\"");

    private static Pattern accessTokenPattern = Pattern.compile("access_token=(.+)&expires_in");

    @Autowired
    private Debugger debugger;

    @Override
    public String getLoginUrl() {

        Map map = initConnectSetting();


        String uuid = UUID.randomUUID().toString();
        debugger.log("generateuuid", uuid);

        String callBack = this.getCallBackUrl(ConnectTypeEnum.QQ.value());

        String url = "https://graph.qq.com/oauth2.0/authorize?" +
                "response_type=code" +
                "&client_id=" + map.get("qq_pc_app_id") +
                "&redirect_uri=" + callBack +
                "&state=" + uuid;


        return url;

    }


    @Override
    public Auth2Token loginCallback() {

        debugger.log("Enter theQQConnectLoginPluginThe callback");

        Map map = initConnectSetting();

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        // Obtain Access tokens using Authorization Code
        String code = request.getParameter("code");
        String redirectUri = this.getCallBackUrl(ConnectTypeEnum.QQ.value());

        String url = "https://graph.qq.com/oauth2.0/token?" +
                "grant_type=authorization_code" +
                "&client_id=" + map.get("qq_pc_app_id") +
                "&client_secret=" + map.get("qq_pc_app_key") +
                "&code=" + code +
                "&redirect_uri=" + redirectUri;


        debugger.log("toqqMake a request, request address is：", url);

        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);

        debugger.log("The return result is:", content);


        String accessToken = "";
        Matcher matcher = accessTokenPattern.matcher(content);
        while (matcher.find()) {
            accessToken = matcher.group(1);
        }

        debugger.log("generateaccessToken", accessToken);

        url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;


        debugger.log("Send a request to Alipay, the request address is：", url);


        content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);

        debugger.log("The return result is", content);

        String unionId = "";
        Matcher unionIdMatcher = unionidPattern.matcher(content);
        while (unionIdMatcher.find()) {
            unionId = unionIdMatcher.group(1);
        }

        debugger.log("getunionId:", unionId);

        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setUnionid(unionId);
        auth2Token.setAccessToken(accessToken);
        return auth2Token;
    }


    @Override
    public Member fillInformation(Auth2Token auth2Token, Member member) {

        Map map = initConnectSetting();

        // Get membership information
        String url = "https://graph.qq.com/user/get_user_info?" +
                "access_token=" + auth2Token.getAccessToken() +
                "&oauth_consumer_key=" + map.get("qq_pc_app_id").toString() +
                "&openid=" + auth2Token.getUnionid();
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject json = JSONObject.fromObject(content);
        String gender = json.getString("gender");
        // Improve member information
        member.setNickname(json.getString("nickname"));
        if ("male".equals(gender)) {
            member.setSex(1);
        } else {
            member.setSex(0);
        }
        member.setFace(json.getString("figureurl"));
        return member;
    }

    @Override
    public ConnectSettingVO assembleConfig() {
        ConnectSettingVO connectSetting = new ConnectSettingVO();
        List<ConnectSettingParametersVO> list = new ArrayList<>();
        for (QqConnectConfigGroupEnum qqConnectConfigGroup : QqConnectConfigGroupEnum.values()) {
            ConnectSettingParametersVO connectSettingParametersVO = new ConnectSettingParametersVO();
            List<ConnectSettingConfigItem> lists = new ArrayList<>();
            for (QqConnectConfigItemEnum qqConnectConfigItem : QqConnectConfigItemEnum.values()) {
                ConnectSettingConfigItem connectSettingConfigItem = new ConnectSettingConfigItem();
                connectSettingConfigItem.setKey("qq_" + qqConnectConfigGroup.value() + "_" + qqConnectConfigItem.value());
                connectSettingConfigItem.setName(qqConnectConfigItem.getText());
                lists.add(connectSettingConfigItem);
            }
            connectSettingParametersVO.setConfigList(lists);
            connectSettingParametersVO.setName(qqConnectConfigGroup.getText());
            list.add(connectSettingParametersVO);
        }
        connectSetting.setName("QQParameter configuration");
        connectSetting.setType(ConnectTypeEnum.QQ.value());
        connectSetting.setConfig(JsonUtil.objectToJson(list));
        return connectSetting;
    }


}
