/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.trade.impl;

import dev.shopflix.core.client.trade.ExchangeGoodsClient;
import dev.shopflix.core.goods.model.dto.ExchangeClientDTO;
import dev.shopflix.core.promotion.exchange.model.dos.ExchangeDO;
import dev.shopflix.core.promotion.exchange.service.ExchangeGoodsManager;
import dev.shopflix.core.promotion.tool.model.dto.PromotionGoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/21 16:14
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class ExchangeGoodsClientDefaultImpl implements ExchangeGoodsClient {

    @Autowired
    private ExchangeGoodsManager exchangeGoodsManager;

    @Override
    public ExchangeDO add(ExchangeClientDTO dto) {
        ExchangeDO exchange = dto.getExchangeSetting();
        PromotionGoodsDTO goodsDTO = dto.getGoodsDTO();

        return exchangeGoodsManager.add(exchange, goodsDTO);
    }

    @Override
    public ExchangeDO edit(ExchangeClientDTO dto) {
        ExchangeDO exchange = dto.getExchangeSetting();
        PromotionGoodsDTO goodsDTO = dto.getGoodsDTO();

        return exchangeGoodsManager.edit(exchange, goodsDTO);
    }

    @Override
    public ExchangeDO getModelByGoods(Integer goodsId) {

        return exchangeGoodsManager.getModelByGoods(goodsId);
    }

    /**
     * 删除某个商品的积分兑换信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public void del(Integer goodsId) {
        exchangeGoodsManager.deleteByGoods(goodsId);
    }
}
