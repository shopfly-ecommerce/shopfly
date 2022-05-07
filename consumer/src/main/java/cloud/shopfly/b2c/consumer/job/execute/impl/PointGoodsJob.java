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
package cloud.shopfly.b2c.consumer.job.execute.impl;

import cloud.shopfly.b2c.consumer.job.execute.EveryDayExecute;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: Point goods timed task
 * @date 2018/11/7 9:32
 * @since v7.0.0
 */
@Component
public class PointGoodsJob implements EveryDayExecute {

    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public void everyDay() {
        // The default validity period of the bonus product added now is one year, so check every day whether the bonus product has reached the expiration date, and increase the time of the bonus product by one year after the expiration date

        String sql = "select * from es_promotion_goods where promotion_type = ? and (end_time - ?)/86400<1  ";

        List<PromotionGoodsDO> list = this.daoSupport.queryForList(sql, PromotionGoodsDO.class, PromotionTypeEnum.EXCHANGE.name(), DateUtil.getDateline());
        if(list !=null){
            for(PromotionGoodsDO promotionGoodsDO : list){

                promotionGoodsDO.setEndTime(promotionGoodsDO.getEndTime()+60*60*24*365);
                this.daoSupport.update(promotionGoodsDO,promotionGoodsDO.getPgId());

            }
        }

    }
}
