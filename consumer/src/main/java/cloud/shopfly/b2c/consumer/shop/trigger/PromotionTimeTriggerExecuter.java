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
package cloud.shopfly.b2c.consumer.shop.trigger;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.goods.GoodsIndexClient;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionPriceDTO;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTriggerExecuter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Evaluation group activity delay actuator
 *
 * @author zh
 * @version v1.0
 * @since v7.0
 * 2019-02-13 In the afternoon5:34
 */
@Component("promotionTimeTriggerExecuter")
public class PromotionTimeTriggerExecuter implements TimeTriggerExecuter {

    @Autowired
    private GoodsIndexClient goodsIndexClient;

    @Autowired
    private GoodsClient goodsClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * Perform promotional activities to add goods to re-change index goods prices
     *
     * @param object The task parameters
     */
    @Override
    public void execute(Object object) {
        try {
            // Get commodity ID
            PromotionPriceDTO promotionPriceDTO = (PromotionPriceDTO) object;
            // Obtaining commodity information
            Integer[] goodsIds = {promotionPriceDTO.getGoodsId()};
            List<Map<String, Object>> list = goodsClient.getGoodsAndParams(goodsIds);
            list.get(0).put("discount_price", promotionPriceDTO.getPrice());
            // Rebuild the index
            goodsIndexClient.updateIndex(list.get(0));
        } catch (Exception e) {
            logger.error("Failed to generate index for new item in promotion campaign");
        }
    }
}
