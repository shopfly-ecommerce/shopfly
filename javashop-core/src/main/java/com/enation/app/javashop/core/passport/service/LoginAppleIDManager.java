/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.passport.service;



import com.enation.app.javashop.core.member.model.dto.AppleIDUserDTO;

import java.util.Map;

/**
 * AppleID IOS 登陆服务
 * @author snow
 * @since v1.0
 * @version 7.2.2
 * 2020-12-16
 */
public interface LoginAppleIDManager {

    /**
     * IOS-APP 登录
     * @param uuid
     * @param appleIDUserDTO
     * @return
     */
    Map appleIDLogin(String uuid, AppleIDUserDTO appleIDUserDTO);



}
