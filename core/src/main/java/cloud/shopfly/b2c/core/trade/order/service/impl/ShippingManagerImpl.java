/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.service.CheckoutParamManager;
import cloud.shopfly.b2c.core.trade.order.service.ShippingManager;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberAddressClient;
import cloud.shopfly.b2c.core.client.member.ShipTemplateClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import cloud.shopfly.b2c.core.system.model.dto.ShipTemplateSettingDTO;
import cloud.shopfly.b2c.core.system.model.enums.AmtTypeEnums;
import cloud.shopfly.b2c.core.system.model.enums.ConditionsTypeEnums;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSettingVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
     * @return
     */
    @Override
    public Double getShipPrice(CartVO cartVO) {
        //最终运费
        double finalShip = 0;
        List<CartSkuVO> cartSkuVOS = cartVO.getSkuList();
        //运费模板map。模板id：模板对象
        Map<Integer, ShipTemplateSettingDTO> templateMap = new HashMap<>();
        //运费模板对应的商品数据列表。模板id:skulist
        Map<Integer, List<CartSkuVO>> templateSkuMap = new HashMap<>();

        for (CartSkuVO cartSkuVO : cartSkuVOS) {
            //购物车商品没有被选中的话，则不进行处理
            if (cartSkuVO.getChecked() == 0) {
                continue;
            }
            //获取购物车 运费模版 映射
            Map<Integer, ShipTemplateSettingVO> map = cartVO.getShipTemplateChildMap();
            //获取当前商品的运费模版
            ShipTemplateSettingVO settingVO = map.get(cartSkuVO.getSkuId());
            if (settingVO == null || settingVO.getItems().size() == 0) {
                continue;
            }
            //创建运费模板数据
            ShipTemplateSettingDTO shipTemplateSettingDTO = new ShipTemplateSettingDTO();
            List<ShipTemplateSettingDO> items = settingVO.getItems();
            //筛选出价格区间设置
            List<ShipTemplateSettingDO> priceSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.PRICE.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setPriceSettings(priceSettings);
            //筛选出重量区间设置
            List<ShipTemplateSettingDO> weightSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.WEIGHT.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setWeightSettings(weightSettings);
            //筛选出价格区间设置
            List<ShipTemplateSettingDO> itemsSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.ITEMS.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setItemsSettings(itemsSettings);
            templateMap.put(settingVO.getTemplateId(), shipTemplateSettingDTO);
            //创建运费模板对应商品数据
            List<CartSkuVO> skuList = templateSkuMap.get(settingVO.getTemplateId());
            if(skuList == null){
                skuList = new ArrayList<>();
            }
            skuList.add(cartSkuVO);

            templateSkuMap.put(settingVO.getTemplateId(),skuList);
        }

        //计算
        for (Integer key : templateSkuMap.keySet()) {

            List<CartSkuVO> skuList = templateSkuMap.get(key);

            ShipTemplateSettingDTO template = templateMap.get(key);

            //运费金额
            Double shipPrice = 0D;
            Double goodsPrice = 0D;
            for (CartSkuVO cartSkuVO : skuList) {
                Double originalPrice = CurrencyUtil.mul(cartSkuVO.getOriginalPrice(), cartSkuVO.getNum());
                goodsPrice = CurrencyUtil.add(goodsPrice, originalPrice);
            }
            //商品价格算运费
            List<ShipTemplateSettingDO> priceSettings = template.getPriceSettings();
            if (priceSettings!=null&&priceSettings.size()>0){
                ShipTemplateSettingDO setting = screen(priceSettings,goodsPrice);
                if (setting!=null){
                    shipPrice = addShipPirce(shipPrice, goodsPrice, setting);
                }

            }

            //重量算运费
            List<ShipTemplateSettingDO> weightSettings = template.getWeightSettings();
            if (weightSettings!=null&&weightSettings.size()>0){

                Double weight = 0D;
                for (CartSkuVO cartSkuVO : skuList) {
                    weight += CurrencyUtil.mul(cartSkuVO.getGoodsWeight(), cartSkuVO.getNum());
                }
                ShipTemplateSettingDO setting = screen(weightSettings,weight);
                if (setting!=null){
                    shipPrice = addShipPirce(shipPrice, goodsPrice, setting);
                }

            }

            //计件算运费
            List<ShipTemplateSettingDO> itemsSettings = template.getItemsSettings();
            if (itemsSettings!=null&&itemsSettings.size()>0){
                Integer num = 0;
                for (CartSkuVO cartSkuVO : skuList) {
                    num += cartSkuVO.getNum();
                }
                ShipTemplateSettingDO setting = screen(itemsSettings,Double.valueOf(num));
                if (setting!=null){
                    shipPrice = addShipPirce(shipPrice, goodsPrice, setting);
                }
            }
            finalShip = CurrencyUtil.add(finalShip, shipPrice);

        }
        if (logger.isDebugEnabled()) {
            logger.debug("最终运费金额计算：" + finalShip);

        }
        return finalShip;
    }

    /**
     * 筛选设置
     * @param weightSettings
     * @param unit
     * @return
     */
    private ShipTemplateSettingDO  screen(List<ShipTemplateSettingDO> weightSettings,Double unit){
        for (ShipTemplateSettingDO settingDO:weightSettings) {
            if (settingDO.getRegionStart() <= unit && (settingDO.getRegionEnd() >= unit || settingDO.getRegionEnd() == null)){
                return settingDO;
            }
        }
        return null;
    }

    /**
     * 增加运费价格
     * @param shipPrice
     * @param goodsPrice
     * @param setting
     * @return
     */
    private Double addShipPirce(Double shipPrice, Double goodsPrice, ShipTemplateSettingDO setting) {
        if (AmtTypeEnums.ABSOLUTE.name().equalsIgnoreCase(setting.getAmtType())) {
            //如果是绝对值就是直接相加
            shipPrice = CurrencyUtil.add(shipPrice, setting.getAmt());
        } else if (AmtTypeEnums.PERCENTAGE.name().equalsIgnoreCase(setting.getAmtType())) {
            //如果是百分比就是商品原价*百分比
            Double pAmt = CurrencyUtil.mul(goodsPrice, CurrencyUtil.div(setting.getAmt(),100D));
            shipPrice = CurrencyUtil.add(shipPrice, pAmt);
        }
        return shipPrice;
    }


    @Override
    public void setShippingPrice(List<CartVO> cartList) {
        MemberAddress address = memberAddressClient.getModel(checkoutParamManager.getParam().getAddressId());
        Buyer buyer = UserContext.getBuyer();
        if (address == null || !address.getMemberId().equals(buyer.getUid())) {
            return;
        }

        // 检测不在配送区域的货品
        this.checkArea(cartList, address.getCountryCode(),address.getStateCode());

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
            double finalShip = this.getShipPrice(cartVo);
            cartVo.getPrice().setFreightPrice(finalShip);
            if (finalShip > 0) {
                cartVo.getPrice().setIsFreeFreight(0);
            }
            cartVo.setShippingTypeName("运费");
        }


    }

    /**
     * 校验地区
     *
     * @param cartList 购物车
     * @param countryCode   国家code
     * @param stateCode      洲code
     * @return
     */
    @Override
    public List<CacheGoods> checkArea(List<CartVO> cartList, String countryCode,String stateCode) {
        List<CacheGoods> errorGoods = new ArrayList<>();
        for (CartVO cartVo : cartList) {
            //运费模版映射
            Map<Integer, ShipTemplateSettingVO> shipMap = new HashMap<>(16);
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

                        for (ShipTemplateSettingVO settingVO : temp.getItems()) {
                            if (settingVO.getAreaId() != null) {
                                /** 校验地区 */
                                if (!StringUtil.isEmpty(stateCode)){
                                    if (settingVO.getAreaId().indexOf("," + stateCode + ",") >= 0) {
                                        shipMap.put(skuVO.getSkuId(), settingVO);
                                    }
                                }
                                if (!StringUtil.isEmpty(countryCode)&&StringUtil.isEmpty(stateCode)){
                                    if (settingVO.getAreaId().indexOf("," + countryCode + ",") >= 0) {
                                        shipMap.put(skuVO.getSkuId(), settingVO);
                                    }
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
