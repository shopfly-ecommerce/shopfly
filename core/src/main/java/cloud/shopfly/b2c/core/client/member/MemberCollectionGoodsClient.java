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
package cloud.shopfly.b2c.core.client.member;

/**
 * 会员上产品收藏
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午4:42
 * @since v7.0
 */

public interface MemberCollectionGoodsClient {


    /**
     * 某商品收藏数量
     *
     * @param goodsId 商品id
     * @return 收藏数量
     */
    Integer getGoodsCollectCount(Integer goodsId);


}
