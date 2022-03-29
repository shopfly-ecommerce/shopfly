/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.exception;

import org.springframework.http.HttpStatus;

/**
 * 无权限异常，比如试图更新一个别人的账号的密码
 *
 * @author yanlin
 * @version v1.0
 * @since v7.0.0
 * 2017年8月15日 下午1:07:30
 */
public class NoPermissionException extends ServiceException {

    private static final long serialVersionUID = 8207742972948289957L;

    public NoPermissionException(String message) {
        super(SystemErrorCodeV1.NO_PERMISSION, message);
        this.statusCode = HttpStatus.UNAUTHORIZED;
    }


}
