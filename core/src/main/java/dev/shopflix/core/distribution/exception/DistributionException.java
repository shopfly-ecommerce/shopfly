/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.exception;

import dev.shopflix.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * 分销异常类
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-03-26 下午5:26
 */
public class DistributionException extends ServiceException {


    public DistributionException(String code, String message) {

        super(code, message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode = HttpStatus.BAD_REQUEST;

    }

}
