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
 * 优惠券业务层
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
public interface CouponManager {

    /**
     * 查询当前商家优惠券列表
     *
     * @param page      页码
     * @param pageSize  每页数量
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param keyword   搜索关键字
     * @return Page
     */
    Page list(int page, int pageSize, Long startTime, Long endTime, String keyword);

    /**
     * 读取商家优惠券，正在进行中的
     *
     * @return
     */
    List<CouponDO> getList();

    /**
     * 添加优惠券
     *
     * @param coupon 优惠券
     * @return Coupon 优惠券
     */
    CouponDO add(CouponDO coupon);

    /**
     * 修改优惠券
     *
     * @param coupon 优惠券
     * @param id     优惠券主键
     * @return Coupon 优惠券
     */
    CouponDO edit(CouponDO coupon, Integer id);

    /**
     * 删除优惠券
     *
     * @param id 优惠券主键
     */
    void delete(Integer id);

    /**
     * 获取优惠券
     *
     * @param id 优惠券主键
     * @return Coupon  优惠券
     */
    CouponDO getModel(Integer id);

    /**
     * 验证操作权限<br/>
     * 如有问题直接抛出权限异常
     *
     * @param id
     */
    void verifyAuth(Integer id);

    /**
     * 增加优惠券使用数量
     *
     * @param id
     */
    void addUsedNum(Integer id);


    /**
     * 增加被领取数量
     *
     * @param couponId
     */
    void addReceivedNum(Integer couponId);


    /**
     * 查询所有商家优惠券列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page all(int page, int pageSize);


    /**
     * 根据失效状态获取优惠券数据集合
     * @param status 失效状态 0：全部，1：有效，2：失效
     * @return
     */
    List<CouponDO> getByStatus(Integer status);
}
