/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.PromotionGoodsClient;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 促销商品客户端实现
 *
 * @author zh
 * @version v7.0
 * @date 19/3/28 上午11:30
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class PromotionGoodsClientDefaultImpl implements PromotionGoodsClient {

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private SeckillGoodsManager seckillGoodsManager;


    @Override
    public void delPromotionGoods(Integer goodsId) {
        //删除促销商品
        promotionGoodsManager.delete(goodsId);
        //删除限时抢购商品
        seckillGoodsManager.deleteSeckillGoods(goodsId);

    }
}
