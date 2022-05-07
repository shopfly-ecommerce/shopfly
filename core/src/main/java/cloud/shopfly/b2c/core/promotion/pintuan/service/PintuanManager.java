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
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Group business layer
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-21 15:17:57
 */
public interface PintuanManager {

    /**
     * Query the group list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param name     The name
     * @return Page
     */
    Page list(int page, int pageSize, String name);

    /**
     * Query activities based on the current state
     *
     * @param status Status
     * @return Group activity gathering
     */
    List<Pintuan> get(String status);

    /**
     * Add spell group
     *
     * @param pintuan Spell group
     * @return Pintuan Spell group
     */
    Pintuan add(Pintuan pintuan);

    /**
     * Modify the spell group
     *
     * @param pintuan Spell group
     * @param id      Spell the primary key
     * @return Pintuan Spell group
     */
    Pintuan edit(Pintuan pintuan, Integer id);

    /**
     * Delete the spell group
     *
     * @param id Spell the primary key
     */
    void delete(Integer id);

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
     * Stop an activity
     *
     * @param promotionId
     */
    void manualClosePromotion(Integer promotionId);

    /**
     * Start an activity
     *
     * @param promotionId
     */
    void manualOpenPromotion(Integer promotionId);

}
