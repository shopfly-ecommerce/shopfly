/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.validation.impl;

import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.validation.annotation.Operation;
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
    private ShopflyConfig shopflyConfig;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Before("@annotation(operation)")
    public void doAfter(Operation operation) throws Exception {
        if (shopflyConfig.getIsDemoSite()) {
            if (logger.isDebugEnabled()) {
                logger.debug("演示站点禁止此操作");
            }
            throw new ServiceException(SystemErrorCodeV1.NO_PERMISSION, "演示站点禁止此操作");
        }
    }

}
