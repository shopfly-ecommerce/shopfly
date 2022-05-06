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
package cloud.shopfly.b2c.framework.elasticsearch;

/**
 *
 * es索引设置
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-03-21
 */

public class EsSettings {

    /**
     * 商品索引后缀
     */
    public static final String GOODS_INDEX_NAME="goods";

    /**
     * 商品type名字
     */
    public static final String GOODS_TYPE_NAME="goods";

    /**
     * 拼团索引后缀
     */
    public static final String PINTUAN_INDEX_NAME="pt";


    /**
     * 拼团类型名字
     */
    public static final String PINTUAN_TYPE_NAME="pintuan_goods";


}
