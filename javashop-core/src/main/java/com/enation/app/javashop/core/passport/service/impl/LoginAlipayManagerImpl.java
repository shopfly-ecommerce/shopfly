/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.passport.service.impl;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.enation.app.javashop.core.member.model.dto.LoginUserDTO;
import com.enation.app.javashop.core.member.model.enums.ConnectTypeEnum;
import com.enation.app.javashop.core.member.service.ConnectManager;
import com.enation.app.javashop.core.passport.service.LoginAlipayManager;
import com.enation.app.javashop.core.passport.service.LoginManager;
import com.enation.app.javashop.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 支付宝登陆相关接口
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
        loginUserDTO.setProvince(alipayUserInfoShareResponse.getProvince());
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
            //处理异常
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
