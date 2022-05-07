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
package cloud.shopfly.b2c.core.client.trade;


import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;

import java.util.List;

/**
 * Spell groupclient
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/18 In the morning11:39
 */

public interface PintuanClient {


    /**
     * Access to spell group
     *
     * @param id Spell the primary key
     * @return Pintuan  Spell group
     */
    Pintuan getModel(Integer id);


    /**
     * Stop an activity
     *
     * @param promotionId
     */
    void closePromotion(Integer promotionId);

    /**
     * Start an activity
     *
     * @param promotionId
     */
    void openPromotion(Integer promotionId);

    /**
     * Query group activities according to the status
     *
     * @param status Status
     * @return Group activity gathering
     */
    List<Pintuan> get(String status);
}
