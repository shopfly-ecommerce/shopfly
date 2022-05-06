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
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.ShipTemplateClient;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.system.service.ShipTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/25 16:24
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class ShipTemplateClientDefaultImpl implements ShipTemplateClient {

    @Autowired
    private ShipTemplateManager shipTemplateManager;

    @Override
    public ShipTemplateVO get(Integer id) {
        return shipTemplateManager.getFromCache(id);
    }
}
