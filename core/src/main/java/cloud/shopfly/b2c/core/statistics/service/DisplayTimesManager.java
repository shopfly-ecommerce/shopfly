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
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.dos.GoodsPageView;

import java.util.List;

/**
 * 访问次数manager
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/7 上午8:21
 */

public interface DisplayTimesManager {


    /**
     * 访问某地址
     *
     * @param url  访问的地址
     * @param uuid 客户唯一id
     */
    void view(String url, String uuid);

    /**
     * 将统计好的商品数据 写入数据库
     *
     * @param list
     */
    void countGoods(List<GoodsPageView> list);


    /**
     * 立即整理现有的数据
     */
    void countNow();


}
