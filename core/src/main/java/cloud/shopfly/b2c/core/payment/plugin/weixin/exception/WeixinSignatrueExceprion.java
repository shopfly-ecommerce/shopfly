/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.plugin.weixin.exception;

import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class WeixinSignatrueExceprion extends ServiceException {

    public WeixinSignatrueExceprion(String message){

        super(StatisticsErrorCode.E801.code(),message);
        this.statusCode= HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode=HttpStatus.BAD_REQUEST;

    }
}
