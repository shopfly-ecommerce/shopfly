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
package cloud.shopfly.b2c.core.promotion.coupon.service;

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * Coupon business layer
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
public interface CouponManager {

    /**
     * Query the list of current merchant coupons
     *
     * @param page      The page number
     * @param pageSize  Number each page
     * @param startTime Starting time
     * @param endTime   By the time
     * @param keyword   Search keywords
     * @return Page
     */
    Page list(int page, int pageSize, Long startTime, Long endTime, String keyword);

    /**
     * Reading merchant coupons, in progress
     *
     * @return
     */
    List<CouponDO> getList();

    /**
     * Add coupons
     *
     * @param coupon coupons
     * @return Coupon coupons
     */
    CouponDO add(CouponDO coupon);

    /**
     * Modify coupons
     *
     * @param coupon coupons
     * @param id     Coupon primary key
     * @return Coupon coupons
     */
    CouponDO edit(CouponDO coupon, Integer id);

    /**
     * Delete coupons
     *
     * @param id Coupon primary key
     */
    void delete(Integer id);

    /**
     * Get coupons
     *
     * @param id Coupon primary key
     * @return Coupon  coupons
     */
    CouponDO getModel(Integer id);

    /**
     * Verifying operation rights<br/>
     * Throw a permission exception if there is a problem
     *
     * @param id
     */
    void verifyAuth(Integer id);

    /**
     * Increase the number of coupons used
     *
     * @param id
     */
    void addUsedNum(Integer id);


    /**
     * Increase the number of claims
     *
     * @param couponId
     */
    void addReceivedNum(Integer couponId);


    /**
     * Check the list of all merchant coupons
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page all(int page, int pageSize);


    /**
     * Get the coupon data set according to the expired state
     * @param status The failure state0：All,1：Effective,2：failure
     * @return
     */
    List<CouponDO> getByStatus(Integer status);
}
