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
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.PintuanClient;
import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PintuanClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-18 In the morning11:44
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class PintuanClientDefaultImpl implements PintuanClient {
    @Autowired
    private PintuanManager pintuanManager;

    /**
     * Access to spell group
     *
     * @param id Spell the primary key
     * @return Pintuan  Spell group
     */
    @Override
    public Pintuan getModel(Integer id) {
        return pintuanManager.getModel(id);
    }

    /**
     * Stop an activity
     *
     * @param promotionId
     */
    @Override
    public void closePromotion(Integer promotionId) {
        pintuanManager.closePromotion(promotionId);
    }

    /**
     * Start an activity
     *
     * @param promotionId
     */
    @Override
    public void openPromotion(Integer promotionId) {
        pintuanManager.openPromotion(promotionId);
    }

    @Override
    public List<Pintuan> get(String status) {
        return pintuanManager.get(status);
    }
}
