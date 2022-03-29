/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.auth;

/**
 * Token
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-21
 */

public class Token {


    /**
     * token令牌
     */
    private String accessToken;


    /**
     * 刷新token
     */
    private String refreshToken;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
