/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.exception;

import org.springframework.http.HttpStatus;

/**
 * 服务异常类,各业务异常需要继承此异常
 * 业务类如有不能处理异常也要抛出此异常
 *
 * @author kingapex
 * @version v1.0.0
 * @since v7.0.0
 * 2017年3月7日 上午11:09:29
 */
public class ServiceException extends RuntimeException {

    protected HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    private String code;
    /**
     * 要返回前端的数据
     */
    private Object data;


    public ServiceException(String code, String message) {
        super(message);
        this.code = code;

    }

    public ServiceException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
