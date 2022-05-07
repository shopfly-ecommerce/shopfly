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
package cloud.shopfly.b2c.consumer.shop.goodssearch;

import cloud.shopfly.b2c.consumer.core.event.GoodsIndexInitEvent;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.goods.GoodsIndexClient;
import cloud.shopfly.b2c.core.system.model.TaskProgressConstant;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.logs.Debugger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: The commodity index is initialized
 * @date 2018/6/25 11:38
 * @since v7.0.0
 */
@Service
public class GoodsIndexInitConsumer implements GoodsIndexInitEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ProgressManager progressManager;
    @Autowired
    private GoodsIndexClient goodsIndexClient;
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Debugger debugger;

    @Override
    public void createGoodsIndex() {

        if (logger.isDebugEnabled()) {
            logger.debug("Start building indexes");
        }
        debugger.log("Start building indexes");

        String key = TaskProgressConstant.GOODS_INDEX;
        try {
            /** Quantity of goods acquired*/
            int goodsCount = this.goodsClient.queryGoodsCount();

            /** Generating task Progress*/
            progressManager.taskBegin(key, goodsCount);

            // Generate a common commodity index
            boolean goodsResult = createOrdinaryGoods(goodsCount);

            // End of the task
            progressManager.taskEnd(key, "Index generation complete");

            if (logger.isDebugEnabled()) {
                logger.debug("Index generation complete");
            }
            debugger.log("Index generation complete");

            if (goodsResult) {
                debugger.log("An error occurred in index generation");
            }

        } catch (Exception e) {
            debugger.log("Index generation exception");
            progressManager.taskError(key, "Abnormal index generation. Contact O&M personnel");
            this.logger.error("Generate index exception：", e);

        }

    }

    /**
     * Generate an index of common goods
     *
     * @param goodsCount Number of goods
     */
    private boolean createOrdinaryGoods(Integer goodsCount) {

        // Used to indicate whether there is an error
        boolean hasError = false;

        int pageSize = 100;
        int pageCount;
        pageCount = goodsCount / pageSize;
        pageCount = goodsCount % pageSize > 0 ? pageCount + 1 : pageCount;
        for (int i = 1; i <= pageCount; i++) {
            List<Map> goodsList = this.goodsClient.queryGoodsByRange(i, pageSize);
            Integer[] goodsIds = new Integer[goodsList.size()];
            int j = 0;
            for (Map map : goodsList) {
                goodsIds[j] = Integer.valueOf(map.get("goods_id").toString());
                j++;
            }
            List<Map<String, Object>> list = goodsClient.getGoodsAndParams(goodsIds);
            boolean result = goodsIndexClient.addAll(list, i);

            // There are mistakes, there are mistakes
            hasError = result && hasError;
        }

        return hasError;

    }

}
