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
package cloud.shopfly.b2c.consumer.shop.sss;

import cloud.shopfly.b2c.consumer.core.event.GoodsChangeEvent;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.client.statistics.GoodsDataClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品数据收集消费者
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/6/20 下午2:19
 * @Description:
 *
 */
@Component
public class DataGoodsConsumer implements GoodsChangeEvent {

	protected final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private GoodsDataClient goodsDataClient;


	@Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg){

		try {
			if (goodsChangeMsg.getGoodsIds() == null || goodsChangeMsg.getGoodsIds().length == 0) {
                return;
            }
			// 添加商品
			if (goodsChangeMsg.getOperationType().equals(GoodsChangeMsg.ADD_OPERATION)) {
                this.goodsDataClient.addGoods(goodsChangeMsg.getGoodsIds());
            }
			// 修改商品
			if (goodsChangeMsg.getOperationType().equals(GoodsChangeMsg.MANUAL_UPDATE_OPERATION)
					||goodsChangeMsg.getOperationType().equals(GoodsChangeMsg.AUTO_UPDATE_OPERATION)) {
                this.goodsDataClient.updateGoods(goodsChangeMsg.getGoodsIds());
            }
		} catch (Exception e) {
			logger.error("商品消息监听异常：",e);
			e.printStackTrace();
		}
	}

}
