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
package cloud.shopfly.b2c.core.distribution.service.impl;


import cloud.shopfly.b2c.core.distribution.model.dos.ShortUrlDO;
import cloud.shopfly.b2c.core.distribution.service.ShortUrlManager;
import cloud.shopfly.b2c.core.distribution.util.ShortUrlGenerator;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Short link implementation class
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 In the morning7:13
 */

@Component
public class ShortUrlManagerImpl implements ShortUrlManager {

    @Autowired

    private DaoSupport daoSupport;


    @Override
    public ShortUrlDO createShortUrl(Integer memberId, Integer goodsId) {
        ShortUrlDO shortUrlDO = null;

        if (goodsId == null) {
            goodsId = 0;
        }
        String url;
        if (goodsId == 0) {
            url = "/index.html?member_id=" + memberId;
        } else {
            url = "/goods/" + goodsId + "?member_id=" + memberId;
        }

        ShortUrlDO result = daoSupport.queryForObject("select * from es_short_url where url = ?", ShortUrlDO.class, url);
        if (result != null) {
            return result;
        }
        String[] shortUrls = ShortUrlGenerator.getShortUrl(url);

        // Check for presence
        for (String tempUrl : shortUrls) {
            String sql = "SELECT count(0) FROM es_short_url WHERE su = ?";
            int num = this.daoSupport.queryForInt(sql, tempUrl);

            if (num == 0) {
                shortUrlDO = new ShortUrlDO();
                shortUrlDO.setSu(tempUrl);
                shortUrlDO.setUrl(url);

                this.daoSupport.insert("es_short_url", shortUrlDO);
                break;
            }
        }

        return shortUrlDO;
    }

    @Override
    public ShortUrlDO getLongUrl(String shortUrl) {
        return this.daoSupport.queryForObject("select * from es_short_url where su = ?", ShortUrlDO.class, shortUrl);
    }

}
