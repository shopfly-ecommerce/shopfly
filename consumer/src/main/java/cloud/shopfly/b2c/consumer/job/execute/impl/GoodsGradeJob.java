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
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品评分定时任务
 *
 * @author fk
 * @version v1.0
 * @since v7.0
 * 2018-07-05 下午2:11
 */
@Component
public class GoodsGradeJob implements EveryDayExecute {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 每晚23:30执行
     */
    @Override
    public void everyDay() {

        try{

            logger.debug(" goods job start");
            Thread.sleep(5000);

            this.goodsClient.updateGoodsGrade();
            logger.debug(" goods job end");
        }catch (Exception e) {
            logger.error("计算商品评分出错", e);
        }

    }

}
