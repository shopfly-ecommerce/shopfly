/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;

/**
 * UserTokenPrefix
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-19 下午3:08
 */
public enum UserTokenPrefix {

    /**
     * 访问令牌
     */
    ACCESS_TOKEN,
    /**
     * 刷新令牌
     */
    REFRESH_TOKEN;


    public String getPrefix() {
        return "{" + this.name() + "}_";
    }
}
