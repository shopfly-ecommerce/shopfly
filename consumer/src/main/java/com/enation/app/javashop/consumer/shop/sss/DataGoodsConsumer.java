/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.sss;

import com.enation.app.javashop.consumer.core.event.GoodsChangeEvent;
import com.enation.app.javashop.core.base.message.GoodsChangeMsg;
import com.enation.app.javashop.core.client.statistics.GoodsDataClient;
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
