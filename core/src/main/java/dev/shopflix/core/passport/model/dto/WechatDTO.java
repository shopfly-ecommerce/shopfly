/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.passport.model.dto;


import dev.shopflix.core.member.model.vo.Auth2Token;

import java.io.Serializable;

/**
 * WechatDTO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-06-18 11:28 AM
 */
public class WechatDTO implements Serializable {

    /**
     * 需要跳转地址
     */
    private String redirectUrl;


    /**
     * 是否需要重定向，前端跳转重新获取accesstoken（包含openid）
     */
    private boolean needRedirect;


    /**
     * 存储用户信息与微信之间沟通的token
     */
    private Auth2Token auth2Token;


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isNeedRedirect() {
        return needRedirect;
    }

    public void setNeedRedirect(boolean needRedirect) {
        this.needRedirect = needRedirect;
    }

    public Auth2Token getAuth2Token() {
        return auth2Token;
    }

    public void setAuth2Token(Auth2Token auth2Token) {
        this.auth2Token = auth2Token;
    }
}
