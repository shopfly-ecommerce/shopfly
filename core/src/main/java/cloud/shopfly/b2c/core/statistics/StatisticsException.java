/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics;

import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * 统计异常类
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-03-26 下午5:26
 */
public class StatisticsException extends ServiceException {



    public StatisticsException(String message){

        super(StatisticsErrorCode.E801.code(),message);
        this.statusCode= HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode=HttpStatus.BAD_REQUEST;

    }


    public StatisticsException(String code, String message){

        super(code,message);
        this.statusCode= HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode=HttpStatus.BAD_REQUEST;

    }

}
