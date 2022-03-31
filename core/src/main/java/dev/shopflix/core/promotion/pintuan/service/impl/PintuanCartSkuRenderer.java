/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.pintuan.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.promotion.pintuan.exception.PintuanErrorCode;
import dev.shopflix.core.promotion.pintuan.model.PintuanGoodsDO;
import dev.shopflix.core.promotion.pintuan.service.PintuanManager;
import dev.shopflix.core.trade.cart.model.enums.CartType;
import dev.shopflix.core.trade.cart.model.vo.CartSkuOriginVo;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.service.cartbuilder.CartSkuRenderer;
import dev.shopflix.core.trade.cart.service.cartbuilder.impl.CartSkuFilter;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingapex on 2019-01-23.
 * 拼团的购物车渲染器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-23
 */
@Service
public class PintuanCartSkuRenderer implements CartSkuRenderer {

    @Autowired
    
    private DaoSupport tradeDaoSupport;


    @Autowired
    private PintuanManager pintuanManager;
    @Autowired
    private Cache cache;

    @SuppressWarnings("Duplicates")
    @Override
    public void renderSku(List<CartVO> cartList, CartType cartType) {

        String originKey = this.getOriginKey();
        CartSkuOriginVo goodsSkuVO = (CartSkuOriginVo) cache.get(originKey);

        String sql = "select * from es_pintuan_goods pg inner join es_pintuan p on p.promotion_id = pg.pintuan_id where sku_id=? and start_time < ? and end_time > ?";
        PintuanGoodsDO pintuanGoodsDO = tradeDaoSupport.queryForObject(sql, PintuanGoodsDO.class, goodsSkuVO.getSkuId(), DateUtil.getDateline(), DateUtil.getDateline());

        if (pintuanGoodsDO == null) {
            throw new ResourceNotFoundException("此拼团活动已经取消，不能发起拼团");
        }
        CartSkuVO skuVO = new CartSkuVO();

        skuVO.setSellerId(pintuanGoodsDO.getSellerId());
        skuVO.setSellerName(pintuanGoodsDO.getSellerName());
        skuVO.setGoodsId(pintuanGoodsDO.getGoodsId());
        skuVO.setSkuId(pintuanGoodsDO.getSkuId());
        skuVO.setCatId(goodsSkuVO.getCategoryId());
        skuVO.setGoodsImage(goodsSkuVO.getThumbnail());
        skuVO.setName(pintuanGoodsDO.getGoodsName());
        skuVO.setSkuSn(pintuanGoodsDO.getSn());


        //拼团成交价
        skuVO.setPurchasePrice(pintuanGoodsDO.getSalesPrice());

        //拼团商品原始价格
        skuVO.setOriginalPrice(pintuanGoodsDO.getPrice());

        skuVO.setSpecList(goodsSkuVO.getSpecList());
        skuVO.setIsFreeFreight(goodsSkuVO.getGoodsTransfeeCharge());
        skuVO.setGoodsWeight(goodsSkuVO.getWeight());
        skuVO.setTemplateId(goodsSkuVO.getTemplateId());
        skuVO.setEnableQuantity(goodsSkuVO.getEnableQuantity());
        skuVO.setLastModify(goodsSkuVO.getLastModify());
        skuVO.setNum(goodsSkuVO.getNum());
        skuVO.setChecked(1);
        skuVO.setGoodsType(goodsSkuVO.getGoodsType());

        //计算小计
        double subTotal = CurrencyUtil.mul(skuVO.getNum(), skuVO.getPurchasePrice());
        skuVO.setSubtotal(subTotal);

        List<CartSkuVO> skuList = new ArrayList<>();
        skuList.add(skuVO);

        Integer sellerId = goodsSkuVO.getSellerId();
        String sellerName = goodsSkuVO.getSellerName();

        CartVO cartVO = new CartVO(sellerId, sellerName, cartType);
        cartVO.setSkuList(skuList);

        //如果超出限购数量 如果限购数量为空，则不验证限购数量
        Integer pintuan = pintuanManager.getModel(pintuanGoodsDO.getPintuanId()).getLimitNum();
        if (pintuan != null && pintuan < skuVO.getNum()) {
            throw new ServiceException(PintuanErrorCode.E5018.code(), PintuanErrorCode.E5018.describe());
        }

        cartList.add(cartVO);

    }

    @Override
    public void renderSku(List<CartVO> cartList, CartSkuFilter cartFilter, CartType cartType) {

        //创建一个临时的list
        List<CartVO> tempList = new ArrayList<>();

        //将临时的list渲染好
        renderSku(tempList, cartType);

        //进行过滤
        tempList.forEach(cartVO -> {

            cartVO.getSkuList().forEach(cartSkuVO -> {
                //如果过滤成功才继续
                if (!cartFilter.accept(cartSkuVO)) {
                    cartList.add(cartVO);
                }
            });

        });
    }


    /**
     * 读取当前会员购物车原始数据key
     *
     * @return
     */
    @SuppressWarnings("Duplicates")
    protected String getOriginKey() {

        String cacheKey = "";
        //如果会员登陆了，则要以会员id为key
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            cacheKey = CachePrefix.CART_SKU_PREFIX.getPrefix() + buyer.getUid();
        }

        return cacheKey;
    }
}
