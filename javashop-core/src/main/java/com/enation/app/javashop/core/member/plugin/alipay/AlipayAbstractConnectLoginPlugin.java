/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.plugin.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.enation.app.javashop.core.member.MemberErrorCode;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.member.model.enums.AlipayConnectConfigGroupEnum;
import com.enation.app.javashop.core.member.model.enums.AlipayConnectConfigItemEnum;
import com.enation.app.javashop.core.member.model.enums.ConnectTypeEnum;
import com.enation.app.javashop.core.member.model.vo.Auth2Token;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingConfigItem;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingParametersVO;
import com.enation.app.javashop.core.member.model.vo.ConnectSettingVO;
import com.enation.app.javashop.core.member.service.impl.AbstractConnectLoginPlugin;
import com.enation.app.javashop.framework.context.ThreadContextHolder;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zjp
 * @version v7.0
 * @Description 支付宝信任登录插件类
 * @ClassName AlipayAbstractConnectLoginPlugin
 * @since v7.0 下午3:53 2018/6/12
 */
@Component
public class AlipayAbstractConnectLoginPlugin extends AbstractConnectLoginPlugin {

    /**
     * 日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlipayAbstractConnectLoginPlugin(){
        super();
    }

    @Override
    public String getLoginUrl() {
        Map map =  initConnectSetting();

        String uuid = UUID.randomUUID().toString();
        debugger.log("生成uuid" , uuid);

        String callBack = this.getCallBackUrl(ConnectTypeEnum.ALIPAY.value());
        return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?" +
                "app_id=" + map.get("alipay_pc_app_id").toString() +
                "&redirect_uri=" + callBack+
                "&scope=auth_user"+
                "&state="+uuid;
    }

    @Override
    public Auth2Token loginCallback() {

        debugger.log("进入  AlipayAbstractConnectLoginPlugin  回调");

        Map map =  initConnectSetting();
        HttpServletRequest req = ThreadContextHolder.getHttpRequest();

        //获取code
        String code = req.getParameter("auth_code");

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                map.get("alipay_pc_app_id").toString(),  map.get("alipay_pc_private_key").toString(), "json", "utf-8",
                map.get("alipay_pc_public_key").toString(), "RSA2");

        debugger.log("向支付宝发出请求");

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            String accessToken = oauthTokenResponse.getAccessToken();
            String openid = oauthTokenResponse.getUserId();

            debugger.log("返回的accessToken及openid为：",accessToken,openid);


            Auth2Token auth2Token = new Auth2Token();
            auth2Token.setUnionid(openid);
            auth2Token.setAccessToken(accessToken);
            return auth2Token;
        } catch (AlipayApiException e) {
            //处理异常
            this.logger.error(e.getMessage(), e);
            throw  new ServiceException(MemberErrorCode.E131.name(),"联合登录失败");
        }
    }

    @Override
    public Member fillInformation(Auth2Token auth2Token, Member member) {
        Map map =  initConnectSetting();
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                map.get("alipay_pc_app_id").toString(), map.get("alipay_pc_private_key").toString(),  "json", "utf-8",
                map.get("alipay_pc_public_key").toString());
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = null;
        try {
            response = alipayClient.execute(request, auth2Token.getAccessToken());
        } catch (AlipayApiException e) {
            this.logger.error(e.getMessage(), e);
            throw  new ServiceException(MemberErrorCode.E131.name(),"联合登录失败");
        }

        if (response.isSuccess()) {
            member.setFace(response.getAvatar());
            member.setNickname(response.getNickName());
            String gender = response.getGender();
            if("M".equals(gender)){
                member.setSex(1);
            }else {
                member.setSex(0);
            }
            return member;
        } else {
            this.logger.error(response.getSubMsg(), response.getSubCode());
            throw  new ServiceException(MemberErrorCode.E131.name(),"联合登录失败");
        }
    }

    @Override
    public ConnectSettingVO assembleConfig() {
        ConnectSettingVO connectSetting = new ConnectSettingVO();
        List<ConnectSettingParametersVO> list = new ArrayList<>();
        for(AlipayConnectConfigGroupEnum alipayConnectConfigGroupEnum: AlipayConnectConfigGroupEnum.values()){
            ConnectSettingParametersVO connectSettingParametersVO = new ConnectSettingParametersVO();
            List<ConnectSettingConfigItem> lists = new ArrayList<>();
            for(AlipayConnectConfigItemEnum alipayConnectConfigItemEnum: AlipayConnectConfigItemEnum.values()){
                ConnectSettingConfigItem connectSettingConfigItem = new ConnectSettingConfigItem();
                connectSettingConfigItem.setKey("alipay_"+alipayConnectConfigGroupEnum.value()+"_"+alipayConnectConfigItemEnum.value());
                connectSettingConfigItem.setName(alipayConnectConfigItemEnum.getText());
                lists.add(connectSettingConfigItem);
            }
            connectSettingParametersVO.setConfigList(lists);
            connectSettingParametersVO.setName(alipayConnectConfigGroupEnum.getText());
            list.add(connectSettingParametersVO);
        }
        connectSetting.setName("支付宝参数配置");
        connectSetting.setType(ConnectTypeEnum.ALIPAY.value());
        connectSetting.setConfig(JsonUtil.objectToJson(list));
        return connectSetting;
    }

}
