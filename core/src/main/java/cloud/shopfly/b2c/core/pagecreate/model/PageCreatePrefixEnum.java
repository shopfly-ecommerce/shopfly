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
package cloud.shopfly.b2c.core.pagecreate.model;

/**
 * PageCreatePrefixEnum
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-17 In the afternoon2:15
 */
public enum PageCreatePrefixEnum {

    /**
     * Home
     */
    INDEX("/"),
    /**
     * Commodity page
     */
    GOODS("/goods/{goods_id}"),
    /**
     * Help page
     */
    HELP("/help/{article_id}");

    String prefix;

    PageCreatePrefixEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHandlerGoods(Integer goodsId) {
        return this.prefix.replace("{goods_id}", goodsId.toString());
    }

    public String getHandlerHelp(Integer articleId) {
        return this.prefix.replace("{article_id}", articleId.toString());

    }
}
