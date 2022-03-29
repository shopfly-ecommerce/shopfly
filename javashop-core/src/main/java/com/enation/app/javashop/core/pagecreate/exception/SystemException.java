/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagecreate.exception;

import com.enation.app.javashop.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * 系统异常
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-10 上午7:11
 */

public class SystemException extends ServiceException {



    public SystemException(String code, String message) {

        super(code, message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode = HttpStatus.BAD_REQUEST;

    }

}
