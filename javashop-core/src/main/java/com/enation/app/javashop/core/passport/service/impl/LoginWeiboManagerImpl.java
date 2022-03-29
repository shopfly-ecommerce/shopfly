/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.passport.service.impl;


import cn.hutool.http.HttpUtil;
import com.enation.app.javashop.core.member.model.dto.LoginAppDTO;
import com.enation.app.javashop.core.member.model.dto.LoginUserDTO;
import com.enation.app.javashop.core.member.model.enums.ConnectTypeEnum;
import com.enation.app.javashop.core.member.service.ConnectManager;
import com.enation.app.javashop.core.passport.service.LoginManager;
import com.enation.app.javashop.core.passport.service.LoginWeiboManager;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.BeanUtil;
import com.enation.app.javashop.framework.util.HttpUtils;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 新浪微博登陆相关接口
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
