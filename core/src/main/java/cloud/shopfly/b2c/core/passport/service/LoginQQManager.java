/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.passport.service;



import cloud.shopfly.b2c.core.member.model.dto.QQUserDTO;

import java.util.Map;

/**
 * QQ统一登陆服务
 * @author cs
 * @since v1.0
 * @version 7.2.2
 * 2020/09/24
 */
public interface LoginQQManager {





    /**
     * 获取unionid
     * @param accessToken QQh5授权返回的code
     * @return
     */
    Map qqWapLogin(String accessToken, String uuid);

    /**
     * QQ app登陆
     * @param qqUserDTO
     * @return
     */
    Map qqAppLogin(String uuid, QQUserDTO qqUserDTO);

    /**
     * 获取wap端appid
     * @return
     */
    String getAppid();

}
