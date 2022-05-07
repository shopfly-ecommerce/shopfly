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
package cloud.shopfly.b2c.core;

import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexInitManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.context.UserHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * shopfly Project startup Configuration
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */

@Component
@Order(value = 1)
public class ShopflyRunner implements ApplicationRunner {


    /**
     * The user informationholderTo obtain authentication information
     */
    @Autowired
    private UserHolder userHolder;


    @Autowired
    private ShopflyConfig shopflyConfig;

    @Autowired
    GoodsIndexInitManager goodsIndexInitManager;

   protected final Logger logger = LoggerFactory.getLogger(ShopflyRunner.class);


    /**
     * Specify the authentication information getter when the project loads
     * The default is aspring Get in a security context
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (shopflyConfig.getTokenSecret() == null) {
            String errorMsg = "Abnormal configuration:Not configuredtokenSecret key, please go toconfigThe configuration center check is as follows：\n";
            errorMsg += "===========================\n";
            errorMsg += "   shopfly.token-secret\n";
            errorMsg += "===========================";

            throw new Exception(errorMsg);
        }

        UserContext.setHolder(userHolder);

        goodsIndexInitManager.initIndex();
        logger.debug("shopfly started");

    }
}
