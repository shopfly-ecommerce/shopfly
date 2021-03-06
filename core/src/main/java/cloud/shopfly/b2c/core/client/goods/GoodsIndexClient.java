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
package cloud.shopfly.b2c.core.client.goods;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Commodity index
 * @date 2018/8/14 14:11
 * @since v7.0.0
 */
public interface GoodsIndexClient {


    /**
     * Adds an item to an index<br>
     * @param goods
     */
    void addIndex(Map goods);

    /**
     * Update an index for an item
     * @param goods
     */
    void updateIndex(Map goods);


    /**
     * update
     * @param goods
     */
    void deleteIndex(Map goods);

    /**
     * Initializing indexes
     * @param list
     * @param index
     * @return  Is the generation successful?
     */
    boolean addAll(List<Map<String, Object>> list, int index);

}
