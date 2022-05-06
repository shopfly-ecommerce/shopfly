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
 * 拼团业务层
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-21 15:17:57
 */
public interface PintuanManager {

    /**
     * 查询拼团列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param name     名字
     * @return Page
     */
    Page list(int page, int pageSize, String name);

    /**
     * 根据当前状态查询活动
     *
     * @param status 状态
     * @return 拼团活动集合
     */
    List<Pintuan> get(String status);

    /**
     * 添加拼团
     *
     * @param pintuan 拼团
     * @return Pintuan 拼团
     */
    Pintuan add(Pintuan pintuan);

    /**
     * 修改拼团
     *
     * @param pintuan 拼团
     * @param id      拼团主键
     * @return Pintuan 拼团
     */
    Pintuan edit(Pintuan pintuan, Integer id);

    /**
     * 删除拼团
     *
     * @param id 拼团主键
     */
    void delete(Integer id);

    /**
     * 获取拼团
     *
     * @param id 拼团主键
     * @return Pintuan  拼团
     */
    Pintuan getModel(Integer id);


    /**
     * 停止一个活动
     *
     * @param promotionId
     */
    void closePromotion(Integer promotionId);

    /**
     * 开始一个活动
     *
     * @param promotionId
     */
    void openPromotion(Integer promotionId);

    /**
     * 停止一个活动
     *
     * @param promotionId
     */
    void manualClosePromotion(Integer promotionId);

    /**
     * 开始一个活动
     *
     * @param promotionId
     */
    void manualOpenPromotion(Integer promotionId);

}