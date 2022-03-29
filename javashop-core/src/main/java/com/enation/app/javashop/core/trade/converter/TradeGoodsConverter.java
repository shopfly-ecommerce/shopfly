/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.converter;

import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.goods.model.vo.GoodsSkuVO;
import com.enation.app.javashop.core.trade.cart.model.vo.TradeConvertGoodsSkuVO;
import com.enation.app.javashop.core.trade.cart.model.vo.TradeConvertGoodsVO;
import com.enation.app.javashop.framework.util.BeanUtil;


/**
 * 转换goods包下，此包使用到的model及字段
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
public class TradeGoodsConverter {

    /**
     * 转换goodsVO对象
     *
     * @return
     */
    public static TradeConvertGoodsVO goodsVOConverter(CacheGoods goods) {
        TradeConvertGoodsVO goodsVO = new TradeConvertGoodsVO();
        goodsVO.setTemplateId(goods.getTemplateId());
        goodsVO.setLastModify(goods.getLastModify());
        return goodsVO;
    }

    /**
     * 转换goodsSkuVO对象
     *
     * @return
     */
    public static TradeConvertGoodsSkuVO goodsSkuVOConverter(GoodsSkuVO goodsSkuVO) {
        TradeConvertGoodsSkuVO skuVO = new TradeConvertGoodsSkuVO();
        BeanUtil.copyProperties(goodsSkuVO, skuVO);
        return skuVO;
    }
}
