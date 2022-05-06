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
 * @Description: 积分商品定时任务
 * @date 2018/11/7 9:32
 * @since v7.0.0
 */
@Component
public class PointGoodsJob implements EveryDayExecute {

    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public void everyDay() {
        //现在添加的积分商品默认是一年的有效期，所以每天检查积分商品是否到了有效期，过了有效期后，将积分商品的时间增加一年

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
