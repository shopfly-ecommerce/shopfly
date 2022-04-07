/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.validation.impl;

import dev.shopflix.framework.ShopflixConfig;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.exception.SystemErrorCodeV1;
import dev.shopflix.framework.validation.annotation.Operation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 演示站点是否可以被操作切面类
 *
 * @author zh
 * @version v7.0
 * @date 19/2/25 上午8:58
 * @since v7.0
 */
@Component
@Aspect
public class OperationAspect {

    @Autowired
    private ShopflixConfig shopflixConfig;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Before("@annotation(operation)")
    public void doAfter(Operation operation) throws Exception {
        if (shopflixConfig.getIsDemoSite()) {
            if (logger.isDebugEnabled()) {
                logger.debug("演示站点禁止此操作");
            }
            throw new ServiceException(SystemErrorCodeV1.NO_PERMISSION, "演示站点禁止此操作");
        }
    }

}