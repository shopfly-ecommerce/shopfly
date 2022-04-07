/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.passport.service;



import dev.shopflix.core.member.model.dto.LoginUserDTO;

import java.util.Map;

public interface LoginManager {

    /**
     * 根据UnionId登陆
     * @param loginUserDTO
     * @return
     */
    Map loginByUnionId(LoginUserDTO loginUserDTO);
}