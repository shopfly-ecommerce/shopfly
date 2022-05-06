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

/**
 * @author fk
 * @version v2.0
 * @Description: 商品分词client
 * @date 2018/8/21 11:04
 * @since v7.0.0
 */
public interface GoodsWordsClient {

    /**
     * 删除某个分词
     * @param words
     */
    void delete(String words);

    /**
     * 添加一组分词，存在累加数量，不存在新增
     * @param words
     */
    void addWords(String words);

    /**
     * 删除
     */
    void delete();

}
