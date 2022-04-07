/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.promotion;

import dev.shopflix.consumer.core.event.GoodsChangeEvent;
import dev.shopflix.core.base.message.GoodsChangeMsg;
import dev.shopflix.core.client.trade.PromotionGoodsClient;
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