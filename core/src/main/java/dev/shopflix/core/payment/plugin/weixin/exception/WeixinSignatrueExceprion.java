/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.plugin.weixin.exception;

import dev.shopflix.core.statistics.StatisticsErrorCode;
import dev.shopflix.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class WeixinSignatrueExceprion extends ServiceException {

    public WeixinSignatrueExceprion(String message){

        super(StatisticsErrorCode.E801.code(),message);
        this.statusCode= HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode=HttpStatus.BAD_REQUEST;

    }
}