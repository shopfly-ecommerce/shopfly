/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.passport.service;

import cloud.shopfly.b2c.core.member.model.dto.LoginAppDTO;

import java.util.Map;

/**
 * 新浪微博登陆相关接口
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-10-30
 */
public interface LoginWeiboManager {


    /**
     * 获取wap登陆跳转地址
     * @param redirectUri
     * @return
     */
    String getLoginUrl(String redirectUri);


    /**
     * wap登陆
     * @param code
     * @param uuid
     * @param redirectUri
     * @return
     */
    Map wapLogin(String code, String uuid, String redirectUri);

    /**
     * app登陆
     * @param loginAppDTO
     * @return
     */
    Map appLogin(LoginAppDTO loginAppDTO);
}
