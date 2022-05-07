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
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.distribution.model.dos.ShortUrlDO;

/**
 * Short linkManagerinterface
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 In the morning8:37
 */
public interface ShortUrlManager {

    /**
     * Generate a short link
     *
     * @param memberId
     * @param goodsId
     * @return
     */
    ShortUrlDO createShortUrl(Integer memberId, Integer goodsId);

    /**
     * Get long links based on short links
     *
     * @param shortUrl Short link（Can be prefixed namely：http:xxx/）
     * @return The corresponding long link
     */
    ShortUrlDO getLongUrl(String shortUrl);

}
