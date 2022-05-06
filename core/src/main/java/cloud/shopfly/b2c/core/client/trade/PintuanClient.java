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
 * 拼团client
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/18 上午11:39
 */

public interface PintuanClient {


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
     * 根据状态查询拼团活动
     *
     * @param status 状态
     * @return 拼团活动集合
     */
    List<Pintuan> get(String status);
}
