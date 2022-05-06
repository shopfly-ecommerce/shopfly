/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CheckDataRebderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据正确性校验实现
 *
 * @author zh
 * @version v7.0
 * @date 18/12/27 上午10:05
 * @since v7.0
 */

@Service
public class CheckDataRendererImpl implements CheckDataRebderer {


    @Autowired
    private GoodsClient goodsClient;


    @Override
    public void checkData(List<CartVO> cartList) {
        for (CartVO cartVO : cartList) {
            for (CartSkuVO cartSkuVO : cartVO.getSkuList()) {
                GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(cartSkuVO.getSkuId());
                if (goodsSkuVO == null || goodsSkuVO.getLastModify() > cartSkuVO.getLastModify()) {
                    //设置购物车未选中
                    cartSkuVO.setChecked(0);
                    //设置购物车此sku商品已失效
                    cartSkuVO.setInvalid(1);
                    //设置失效消息
                    cartSkuVO.setErrorMessage("商品信息发生变化,已失效");
                }
            }
        }
    }


}
