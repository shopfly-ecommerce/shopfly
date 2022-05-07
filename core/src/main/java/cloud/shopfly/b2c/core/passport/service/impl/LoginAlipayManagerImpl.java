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


import cloud.shopfly.b2c.core.member.model.dto.LoginUserDTO;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.service.ConnectManager;
import cloud.shopfly.b2c.core.passport.service.LoginManager;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import cloud.shopfly.b2c.core.passport.service.LoginAlipayManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Pay treasure to land relevant interface
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
@Service
public class LoginAlipayManagerImpl  implements LoginAlipayManager {

    @Autowired
    private ConnectManager connectManager;

    @Autowired
    private LoginManager loginManager;

    @Override
    public String getLoginUrl(String redirectUri) {
        Map<String, String> map = connectManager.initConnectSetting();
        String appId = map.get("alipay_wap_app_id");
        StringBuffer loginBuffer = new StringBuffer("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?");
        loginBuffer.append("app_id=").append(appId);
        loginBuffer.append("&scope=auth_user");
        loginBuffer.append("&redirect_uri=").append(redirectUri);
        loginBuffer.append("&state=init");
        StringBuffer openBuffer = new StringBuffer("alipays://platformapi/startapp?appId=20000067&url=");
        try {
            openBuffer.append(URLEncoder.encode(loginBuffer.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openBuffer.toString();
    }

    @Override
    public Map wapLogin(String code,String uuid){
        AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = getAccessToken(code);
        AlipayUserInfoShareResponse alipayUserInfoShareResponse = getUserInfo(alipaySystemOauthTokenResponse.getAccessToken());
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUuid(uuid);
        loginUserDTO.setTokenOutTime(null);
        loginUserDTO.setRefreshTokenOutTime(null);
        loginUserDTO.setUnionType(ConnectTypeEnum.ALIPAY);
        loginUserDTO.setUnionid(alipayUserInfoShareResponse.getUserId());
        loginUserDTO.setHeadimgurl(alipayUserInfoShareResponse.getAvatar());
        loginUserDTO.setNickName(alipayUserInfoShareResponse.getNickName());
        loginUserDTO.setSex("F".equals(alipayUserInfoShareResponse.getGender())?0:1);
        loginUserDTO.setCountry(alipayUserInfoShareResponse.getCountryCode());
        loginUserDTO.setCity(alipayUserInfoShareResponse.getCity());
        return loginManager.loginByUnionId(loginUserDTO);
    }



    private AlipaySystemOauthTokenResponse getAccessToken(String code){
        Map<String, String> map = connectManager.initConnectSetting();
        String appId = map.get("alipay_wap_app_id");
        String privateKey = map.get("alipay_wap_private_key");
        String publicKey = map.get("alipay_wap_public_key");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "utf-8", publicKey, "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse oauthTokenResponse = null;
        try {
            oauthTokenResponse = alipayClient.execute(request);
            if (!oauthTokenResponse.isSuccess()){
                throw new ServiceException("403",oauthTokenResponse.getSubMsg());
            }
            System.out.println(oauthTokenResponse.getAccessToken());
            System.out.println(oauthTokenResponse.getUserId());
        } catch (AlipayApiException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return oauthTokenResponse;
    }

    private AlipayUserInfoShareResponse getUserInfo(String accessToken){
        Map<String, String> map = connectManager.initConnectSetting();
        String appId = map.get("alipay_wap_app_id");
        String privateKey = map.get("alipay_wap_private_key");
        String publicKey = map.get("alipay_wap_public_key");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,privateKey,"json","UTF-8",publicKey,"RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = null;
        try {
            response = alipayClient.execute(request,accessToken);
            if(!response.isSuccess()){
                throw new ServiceException("403",response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }
}
