/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.client.member.MemberAddressClient;
import dev.shopflix.core.client.member.ShipTemplateClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.member.model.dos.MemberAddress;
import dev.shopflix.core.system.model.dto.ShipTemplateChildDTO;
import dev.shopflix.core.system.model.vo.ShipTemplateChildBuyerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateVO;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartVO;
import dev.shopflix.core.trade.cart.model.vo.PromotionRule;
import dev.shopflix.core.trade.order.service.CheckoutParamManager;
import dev.shopflix.core.trade.order.service.ShippingManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 运费计算业务层实现类
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class ShippingManagerImpl implements ShippingManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ShipTemplateClient shipTemplateClient;

    @Autowired
    private MemberAddressClient memberAddressClient;

    @Autowired
    private CheckoutParamManager checkoutParamManager;


    /**
     * 获取购物车价格
     *
     * @param cartVO 购物车
     * @param areaId 地区id
     * @return
     */
    @Override
    public Double getShipPrice(CartVO cartVO, Integer areaId) {
        //最终运费
        double finalShip = 0;
        List<CartSkuVO> cartSkuVOS = cartVO.getSkuList();
        //运费模板map。模板id：模板对象
        Map<Integer, ShipTemplateChildDTO> templateMap = new HashMap<>();
        //运费模板对应的商品数据列表。模板id:skulist
        Map<Integer, List<CartSkuVO>> templateSkuMap = new HashMap<>();

        for (CartSkuVO cartSkuVO : cartSkuVOS) {
            //购物车商品没有被选中的话，则不进行处理
            if (cartSkuVO.getChecked() == 0) {
                continue;
            }
            //获取购物车 运费模版 映射
            Map<Integer, ShipTemplateChildDTO> map = cartVO.getShipTemplateChildMap();
            //获取当前商品的运费模版
            ShipTemplateChildDTO temp = map.get(cartSkuVO.getSkuId());
            if (temp == null) {
                continue;
            }
            //创建运费模板数据
            if (templateMap.get(temp.getTemplateId()) == null) {
                templateMap.put(temp.getTemplateId(), temp);
            }
            //创建运费模板对应商品数据
            List<CartSkuVO> skuList = templateSkuMap.get(temp.getTemplateId());
            if(skuList == null){
                skuList = new ArrayList<>();
            }
            skuList.add(cartSkuVO);

            templateSkuMap.put(temp.getTemplateId(),skuList);
        }

        //计算
        for (Integer key : templateSkuMap.keySet()) {

            List<CartSkuVO> skuList = templateSkuMap.get(key);

            ShipTemplateChildDTO template = templateMap.get(key);

            //运费金额
            double shipPrice = template.getFirstPrice();
            if (logger.isDebugEnabled()) {
                logger.debug("shipPrice：" + shipPrice);

            }

            Double purchase = 0.0;
            // 1 重量算运费
            if (template.getType() == 1) {
                for (CartSkuVO cartSkuVO : skuList) {
                    purchase += CurrencyUtil.mul(cartSkuVO.getGoodsWeight(), cartSkuVO.getNum());
                }
            }
            // 2 计件算运费
            else {
                for (CartSkuVO cartSkuVO : skuList) {
                    purchase += cartSkuVO.getNum();
                }
            }
            //是否需要计算 续重/续件
            if (template.getFirstCompany() < purchase) {
                //重量 / 续重=续重金额的倍数
                double count = (purchase - template.getFirstCompany()) / template.getContinuedCompany();
                //向上取整计算为运费续重倍数
                count = Math.ceil(count);
                // 运费 = 首重价格+续重倍数*续重费用
                shipPrice = CurrencyUtil.add(shipPrice,
                        CurrencyUtil.mul(count, template.getContinuedPrice()));
                if (logger.isDebugEnabled()) {
                    logger.debug("续重费用：" + shipPrice);
                }

            }
            finalShip = CurrencyUtil.add(finalShip, shipPrice);


        }
        if (logger.isDebugEnabled()) {
            logger.debug("最终运费金额计算：" + finalShip);

        }
        return finalShip;
    }


    @Override
    public void setShippingPrice(List<CartVO> cartList) {
        MemberAddress address = memberAddressClient.getModel(checkoutParamManager.getParam().getAddressId());
        Buyer buyer = UserContext.getBuyer();
        if (address == null || !address.getMemberId().equals(buyer.getUid())) {
            return;
        }
//        Integer areaId = address.actualAddress();

        // 检测不在配送区域的货品
//        this.checkArea(cartList, areaId);

        for (CartVO cartVo : cartList) {

            List<PromotionRule> ruleList = cartVo.getRuleList();
            //如果满减慢增免邮则不计算邮费
            if (StringUtil.isNotEmpty(ruleList)) {
                boolean flag = false;
                for (PromotionRule rule : ruleList) {
                    if (rule != null && rule.getFreeShipping()) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    return;
                }
            }

            // 获取购物车商品运费总计
//            double finalShip = this.getShipPrice(cartVo, areaId);
//            cartVo.getPrice().setFreightPrice(finalShip);
//            if (finalShip > 0) {
//                cartVo.getPrice().setIsFreeFreight(0);
//            }
            cartVo.setShippingTypeName("运费");
        }


    }

    /**
     * 校验地区
     *
     * @param cartList 购物车
     * @param areaId   地区
     * @return
     */
    @Override
    public List<CacheGoods> checkArea(List<CartVO> cartList, Integer areaId) {
        List<CacheGoods> errorGoods = new ArrayList<>();
        for (CartVO cartVo : cartList) {
            //运费模版映射
            Map<Integer, ShipTemplateChildDTO> shipMap = new HashMap<>(16);
            List<CartSkuVO> cartSkuVOS = cartVo.getSkuList();
            for (CartSkuVO skuVO : cartSkuVOS) {
                // 未选中则先不处理
                if (skuVO.getChecked() == 0) {
                    continue;
                }
                // 不免运费
                if (skuVO.getIsFreeFreight() != 1) {
                    skuVO.setIsShip(1);
                    // 获取运费模板信息 没有运费模版的话 记录错误的商品，禁止下单
                    ShipTemplateVO temp = this.shipTemplateClient.get(skuVO.getTemplateId());
                    //如果模版空
                    if (temp == null) {
                        errorGoods.add(goodsClient.getFromCache(skuVO.getGoodsId()));
                        skuVO.setIsShip(0);
                    } else {

                        for (ShipTemplateChildBuyerVO child : temp.getItems()) {
                            if (child.getAreaId() != null) {
                                /** 校验地区 */
                                if (child.getAreaId().indexOf("," + areaId + ",") >= 0) {
                                    ShipTemplateChildDTO dto = new ShipTemplateChildDTO(child);
                                    dto.setTemplateId(temp.getId());
                                    dto.setType(temp.getType());
                                    shipMap.put(skuVO.getSkuId(), dto);
                                }
                            }
                        }
                        // 如果没有匹配 则当
                        if (!shipMap.containsKey(skuVO.getSkuId())) {
                            errorGoods.add(goodsClient.getFromCache(skuVO.getGoodsId()));
                            skuVO.setIsShip(0);
                        }
                    }
                } else {
                    //如果没有设置运费模版 则默认地区有货
                    skuVO.setIsShip(1);
                }
            }
            cartVo.setShipTemplateChildMap(shipMap);
        }
        return errorGoods;
    }


}
