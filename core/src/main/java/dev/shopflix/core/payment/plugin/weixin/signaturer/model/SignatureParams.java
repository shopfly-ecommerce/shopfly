/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.plugin.weixin.signaturer.model;

import java.io.Serializable;

/**
 * 签名参数
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-21 下午2:48
 */
public class SignatureParams  implements Serializable {


    /**
     * appId
     */
    private String appId;


    /**
     * ticket
     */
    private WechatJsapiTicket wechatJsapiTicket;

    /**
     * token
     */
    private WechatAccessToken wechatAccessToken;


    public WechatJsapiTicket getWechatJsapiTicket() {
        return wechatJsapiTicket;
    }

    public void setWechatJsapiTicket(WechatJsapiTicket wechatJsapiTicket) {
        this.wechatJsapiTicket = wechatJsapiTicket;
    }

    public WechatAccessToken getWechatAccessToken() {
        return wechatAccessToken;
    }

    public void setWechatAccessToken(WechatAccessToken wechatAccessToken) {
        this.wechatAccessToken = wechatAccessToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
