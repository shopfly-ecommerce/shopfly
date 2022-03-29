/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.passport.service;


/**
 * 账号管理
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-04-8 11:33:56
 */
public interface PassportManager {
    /**
     * 发送注册短信验证码
     *
     * @param mobile 手机号码
     */
    void sendRegisterSmsCode(String mobile);

    /**
     * 发送找回密码短信验证码
     *
     * @param mobile
     */
    void sendFindPasswordCode(String mobile);

    /**
     * 发送登录短信验证码
     *
     * @param mobile
     */
    void sendLoginSmsCode(String mobile);


    /**
     * 通过refreshToken 获取 accessToken
     *
     * @param refreshToken
     * @returna ccessToken
     */
    String exchangeToken(String refreshToken);

    /**
     * 清除标记缓存
     *
     * @param mobile 手机号码
     * @param scene  业务场景
     */
    void clearSign(String mobile, String scene);


}