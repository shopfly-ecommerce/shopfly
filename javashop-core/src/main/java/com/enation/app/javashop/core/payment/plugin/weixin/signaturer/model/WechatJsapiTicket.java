/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.plugin.weixin.signaturer.model;

import java.io.Serializable;

/**
 * 微信jsapi 访问票据
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-22 上午11:55
 */
public class WechatJsapiTicket implements Serializable {


    /**
     * jsapiticket
     */
    private String jsapiTicket;
    /**
     * expires_in 有效时间
     */
    private Integer expires;

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }
}
