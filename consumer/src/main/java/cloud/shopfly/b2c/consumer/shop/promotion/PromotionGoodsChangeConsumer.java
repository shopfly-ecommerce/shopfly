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
package cloud.shopfly.b2c.consumer.shop.promotion;

import cloud.shopfly.b2c.consumer.core.event.GoodsChangeEvent;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.client.trade.PromotionGoodsClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 删除促销活动商品
 *
 * @author liuyulei
 * @version v1.0
 * @since v7.1.3 2019-07-30
 */
@Component
public class PromotionGoodsChangeConsumer implements GoodsChangeEvent {

    @Autowired
    private PromotionGoodsClient promotionGoodsClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {

        //修改商品,则删除此商品参与的所有促销活动
        if(goodsChangeMsg.getOperationType().equals(GoodsChangeMsg.MANUAL_UPDATE_OPERATION)){
            if (logger.isDebugEnabled()) {
                logger.debug("删除促销活动商品");
            }
            this.promotionGoodsClient.delPromotionGoods(goodsChangeMsg.getGoodsIds()[0]);
        }

    }
}
