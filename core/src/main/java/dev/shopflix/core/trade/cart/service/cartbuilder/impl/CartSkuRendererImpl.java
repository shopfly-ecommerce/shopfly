/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.service.cartbuilder.impl;

import dev.shopflix.core.trade.cart.model.enums.CartType;
import dev.shopflix.core.trade.cart.model.vo.CartSkuOriginVo;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.service.CartOriginDataManager;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartSkuRenderer;
import dev.shopflix.core.trade.cart.util.CartUtil;
import dev.shopflix.framework.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车sku渲染实现<br>
 * 文档请参考：<br>
 * <a href="http://doc.javamall.com.cn/current/achitecture/jia-gou/ding-dan/cart-and-checkout.html#购物车显示" >购物车显示</a>
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/11
 */
@Service
@Primary
public class CartSkuRendererImpl implements CartSkuRenderer {


    @Autowired
    private CartOriginDataManager cartOriginDataManager;

    @Override
    public void renderSku(List<CartVO> cartList, CartType cartType) {

        //获取原始数据
        List<CartSkuOriginVo> originList = cartOriginDataManager.read();

        //用原始数据渲染购物车
        for (CartSkuOriginVo originVo : originList) {

            innerRunder(originVo, cartList, cartType);

        }

    }

    @Override
    public void renderSku(List<CartVO> cartList, CartSkuFilter cartSkuFilter, CartType cartType) {
        //获取原始数据
        List<CartSkuOriginVo> originList = cartOriginDataManager.read();

        //用原始数据渲染购物车
        for (CartSkuOriginVo originVo : originList) {

            //转换为购物车skuvo
            CartSkuVO skuVO = toSkuVo(originVo);

            //如果过滤成功才继续
            if (!cartSkuFilter.accept(skuVO)) {
                continue;
            }

            innerRunder(originVo, cartList, cartType);

        }
    }


    /**
     * 内部化用的渲染方法
     *
     * @param originVo
     * @param cartList
     * @param cartType
     */
    private void innerRunder(CartSkuOriginVo originVo, List<CartVO> cartList, CartType cartType) {
        Integer sellerId = originVo.getSellerId();
        String sellerName = originVo.getSellerName();
        //转换为购物车skuvo
        CartSkuVO skuVO = toSkuVo(originVo);
        CartVO cartVO = CartUtil.findCart(sellerId, cartList);
        if (cartVO == null) {
            cartVO = new CartVO(sellerId, sellerName, cartType);
            cartList.add(cartVO);
        }

        //压入到当前店铺的sku列表中
        cartVO.getSkuList().add(skuVO);
    }

    /**
     * 将一个 CartSkuOriginVo转为  CartSkuVO
     *
     * @param originVo
     * @return
     */
    @SuppressWarnings("Duplicates")
    private CartSkuVO toSkuVo(CartSkuOriginVo originVo) {

        // 生成一个购物项
        CartSkuVO skuVO = new CartSkuVO();
        skuVO.setSellerId(originVo.getSellerId());
        skuVO.setSellerName(originVo.getSellerName());
        skuVO.setGoodsId(originVo.getGoodsId());
        skuVO.setSkuId(originVo.getSkuId());
        skuVO.setCatId(originVo.getCategoryId());
        skuVO.setGoodsImage(originVo.getThumbnail());
        skuVO.setName(originVo.getGoodsName());
        skuVO.setSkuSn(originVo.getSn());
        skuVO.setPurchasePrice(originVo.getPrice());
        skuVO.setOriginalPrice(originVo.getPrice());
        skuVO.setSpecList(originVo.getSpecList());
        skuVO.setIsFreeFreight(originVo.getGoodsTransfeeCharge());
        skuVO.setGoodsWeight(originVo.getWeight());
        skuVO.setTemplateId(originVo.getTemplateId());
        skuVO.setEnableQuantity(originVo.getEnableQuantity());
        skuVO.setLastModify(originVo.getLastModify());
        skuVO.setNum(originVo.getNum());
        skuVO.setChecked(originVo.getChecked());
        skuVO.setGoodsType(originVo.getGoodsType());
        skuVO.setSellerId(originVo.getSellerId());
        skuVO.setSellerName(originVo.getSellerName());

        //设置可用的活动列表
        skuVO.setSingleList(originVo.getSingleList());
        skuVO.setGroupList(originVo.getGroupList());

        //计算小计
        double subTotal = CurrencyUtil.mul(skuVO.getNum(), skuVO.getOriginalPrice());
        skuVO.setSubtotal(subTotal);

        return skuVO;
    }

}