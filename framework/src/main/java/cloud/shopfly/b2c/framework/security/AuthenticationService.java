/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.security;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证业务类
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019/12/27
 */
public interface AuthenticationService {


    /**
     * 认证
     * @param req
     */
    void auth(HttpServletRequest req);

}
