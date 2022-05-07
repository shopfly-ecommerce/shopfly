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
package cloud.shopfly.b2c.core.passport.service.impl;


import cloud.shopfly.b2c.core.member.model.dto.LoginAppDTO;
import cloud.shopfly.b2c.core.member.model.dto.LoginUserDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.passport.service.LoginManager;
import cloud.shopfly.b2c.core.passport.service.LoginWeiboManager;
import cn.hutool.http.HttpUtil;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Sina Weibo login interface
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Service
public class LoginWeiboManagerImpl implements LoginWeiboManager {

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private LoginManager loginManager;

    private static final Integer WB_TOKEN_VAILD_TIME_APP = 60*60*24*90;

    @Override
    public String getLoginUrl(String redirectUri) {
        Map<String, String> map = connectManager.initConnectSetting();
        String appId = map.get("weibo_pc_app_key");
        StringBuffer loginBuffer = new StringBuffer("https://open.weibo.cn/oauth2/authorize?");
        loginBuffer.append("client_id=").append(appId);
        loginBuffer.append("&redirect_uri=").append(redirectUri);
        loginBuffer.append("&display=mobile");
        loginBuffer.append("&state=weibo");
        return loginBuffer.toString();
    }

    @Override
    public Map wapLogin(String code,String uuid,String redirectUri){
        JSONObject jsonObject = getAccessToken(code,redirectUri);
        LoginUserDTO loginUserDTO = getUserInfo(jsonObject.getString("access_token"),jsonObject.getString("uid"));
        loginUserDTO.setUuid(uuid);
        loginUserDTO.setTokenOutTime(null);
        loginUserDTO.setRefreshTokenOutTime(null);
        return loginManager.loginByUnionId(loginUserDTO);
    }

    @Override
    public Map appLogin(LoginAppDTO loginAppDTO) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtil.copyProperties(loginAppDTO,loginUserDTO);
        loginUserDTO.setUnionType(ConnectTypeEnum.WEIBO);
        loginUserDTO.setTokenOutTime(WB_TOKEN_VAILD_TIME_APP);
        loginUserDTO.setRefreshTokenOutTime(WB_TOKEN_VAILD_TIME_APP);
        return loginManager.loginByUnionId(loginUserDTO);
    }


    private JSONObject getAccessToken(String code,String redirectUri){
        Map<String, String> map = connectManager.initConnectSetting();
        String appId = map.get("weibo_pc_app_key");
        String secret = map.get("weibo_pc_app_secret");
        String url = "https://api.weibo.com/oauth2/access_token";
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("client_id",appId);
        dataMap.put("client_secret",secret);
        dataMap.put("grant_type","authorization_code");
        dataMap.put("code",code);
        try {
            dataMap.put("redirect_uri", URLDecoder.decode(redirectUri,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String post = HttpUtil.post(url, dataMap);
        JSONObject jsonObject = JSONObject.fromObject(post);
        if (jsonObject.get("access_token")==null){
            throw new ServiceException("403","fail_to_get_access_token");
        }
        return jsonObject;
    }

    private LoginUserDTO getUserInfo(String accessToken,String uid){
        StringBuffer accessTokenBuffer = new StringBuffer("https://api.weibo.com/2/users/show.json?");
        accessTokenBuffer.append("access_token=").append(accessToken);
        accessTokenBuffer.append("&uid=").append(uid);
        String user_json = HttpUtils.doGet(accessTokenBuffer.toString(),"UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(user_json);
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUnionType(ConnectTypeEnum.WEIBO);
        loginUserDTO.setUnionid(uid);
        loginUserDTO.setHeadimgurl(jsonObject.getString("avatar_hd"));
        loginUserDTO.setNickName(jsonObject.getString("screen_name"));
        loginUserDTO.setSex("m".equals(jsonObject.getString("gender"))?1:0);
//        loginUserDTO.setProvince(jsonObject.getString("province"));
//        loginUserDTO.setCity(jsonObject.getString("city"));
        return loginUserDTO;
    }
}
