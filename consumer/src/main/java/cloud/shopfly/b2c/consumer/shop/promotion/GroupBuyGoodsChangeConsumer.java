/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.promotion;

import cloud.shopfly.b2c.consumer.core.event.GoodsChangeEvent;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author liuyulei
 * @version 1.0
 * @Description:  修改团购商品信息
 * @date 2019/5/15 9:50
 * @since v7.0
 */
@Component
public class GroupBuyGoodsChangeConsumer implements GoodsChangeEvent {

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
        if (GoodsChangeMsg.MANUAL_UPDATE_OPERATION == goodsChangeMsg.getOperationType()) {
            this.groupbuyGoodsManager.updateGoodsInfo(goodsChangeMsg.getGoodsIds());
        }
    }
}
