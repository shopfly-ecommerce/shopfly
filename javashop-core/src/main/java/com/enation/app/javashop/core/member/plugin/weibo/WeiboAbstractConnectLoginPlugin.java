/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.plugin.weibo;

import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.enums.ConnectTypeEnum;
import com.enation.app.javashop.core.member.model.enums.WeiboConnectConfigGroupEnum;
import com.enation.app.javashop.core.member.model.enums.WeiboConnectConfigItemEnum;
import com.enation.app.javashop.core.member.model.vo.Auth2Token;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingConfigItem;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingParametersVO;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingVO;
import com.enation.app.javashop.core.member.service.impl.AbstractConnectLoginPlugin;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.util.HttpUtils;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description 微博信任登录插件类
 * @ClassName WeiboAbstractConnectLoginPlugin
 * @since v7.0 上午11:37 2018/6/5
 */
@Component
public class WeiboAbstractConnectLoginPlugin extends AbstractConnectLoginPlugin {

    public WeiboAbstractConnectLoginPlugin() {
        super();
    }

    @Override
    public String getLoginUrl() {

        //获取参数
        Map map =  initConnectSetting();

        //将回调地址存入redis中
        String callBack = this.getCallBackUrl(ConnectTypeEnum.WEIBO.value());
        return "https://api.weibo.com/oauth2/authorize?" +
                "client_id=" + StringUtil.toString(map.get("weibo_pc_app_key")) +
                "&redirect_uri=" + callBack +
                "&scope=all";
    }

    @Override
    public Auth2Token loginCallback() {

        debugger.log("进入 WeiboAbstractConnectLoginPlugin 回调");

        //获取参数
        Map map =  initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        //获取code
        String code = request.getParameter("code");
        //通过code获取access_token及openid
        String callBack = this.getCallBackUrl(ConnectTypeEnum.WEIBO.value());
        String url = "https://api.weibo.com/oauth2/access_token";

        Map newMap = new HashMap<>(8);
        newMap.put("client_id", StringUtil.toString(map.get("weibo_pc_app_key")));
        newMap.put("client_secret", StringUtil.toString(map.get("weibo_pc_app_secret")));
        newMap.put("grant_type", "authorization_code");
        newMap.put("code", code);
        newMap.put("redirect_uri", callBack);

        debugger.log("向weibo发出请求，请求地址为：",url);

        String content = HttpUtils.doPost(url, newMap, "UTF-8", 1000, 1000);

        debugger.log("返回结果为:",content);

        //获取openid
        JSONObject json = JSONObject.fromObject(content);

        String openid = json.getString("uid");
        String accessToken = json.getString("access_token");

        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setUnionid(openid);
        auth2Token.setAccessToken(accessToken);

        return auth2Token;
    }

    @Override
    public Member fillInformation(Auth2Token auth2Token, Member member) {
        //获取参数
        Map map =  initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        String url = "https://api.weibo.com/2/users/show.json?" +
                "access_token=" + auth2Token.getAccessToken() +
                "&uid=" + auth2Token.getUnionid();
        //通过openid获取userinfo
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(content);
        member.setNickname(jsonObject.getString("screen_name"));
        member.setFace(jsonObject.getString("profile_image_url"));
        String sex = jsonObject.getString("gender");
        if ("m".equals(sex)) {
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
        for (WeiboConnectConfigGroupEnum weiboConnectConfigGroupEnum : WeiboConnectConfigGroupEnum.values()) {
            ConnectSettingParametersVO connectSettingParametersVO = new ConnectSettingParametersVO();
            List<ConnectSettingConfigItem> lists = new ArrayList<>();
            for (WeiboConnectConfigItemEnum weiboConnectConfigItemEnum : WeiboConnectConfigItemEnum.values()) {
                ConnectSettingConfigItem connectSettingConfigItem = new ConnectSettingConfigItem();
                connectSettingConfigItem.setKey("weibo_" + weiboConnectConfigGroupEnum.value() + "_" + weiboConnectConfigItemEnum.value());
                connectSettingConfigItem.setName(weiboConnectConfigItemEnum.getText());
                lists.add(connectSettingConfigItem);
            }
            connectSettingParametersVO.setConfigList(lists);
            connectSettingParametersVO.setName(weiboConnectConfigGroupEnum.getText());
            list.add(connectSettingParametersVO);
        }
        connectSetting.setName("微博参数配置");
        connectSetting.setType(ConnectTypeEnum.WEIBO.value());
        connectSetting.setConfig(JsonUtil.objectToJson(list));
        return connectSetting;
    }
}
