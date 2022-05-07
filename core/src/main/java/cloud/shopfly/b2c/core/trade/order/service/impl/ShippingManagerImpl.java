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
 * Freight calculation business layer implementation class
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
     * Get shopping cart price
     *
     * @param cartVO The shopping cart
     * @return
     */
    @Override
    public Double getShipPrice(CartVO cartVO) {
        // The final freight
        double finalShip = 0;
        List<CartSkuVO> cartSkuVOS = cartVO.getSkuList();
        // Freight template map. Template ID: indicates the template object
        Map<Integer, ShipTemplateSettingDTO> templateMap = new HashMap<>();
        // List of goods data corresponding to the freight template. Template id: skulist
        Map<Integer, List<CartSkuVO>> templateSkuMap = new HashMap<>();

        for (CartSkuVO cartSkuVO : cartSkuVOS) {
            // Cart items that are not selected are not processed
            if (cartSkuVO.getChecked() == 0) {
                continue;
            }
            // Gets the shopping cart freight template map
            Map<Integer, ShipTemplateSettingVO> map = cartVO.getShipTemplateChildMap();
            // Gets the shipping template for the current item
            ShipTemplateSettingVO settingVO = map.get(cartSkuVO.getSkuId());
            if (settingVO == null || settingVO.getItems().size() == 0) {
                continue;
            }
            // Create freight template data
            ShipTemplateSettingDTO shipTemplateSettingDTO = new ShipTemplateSettingDTO();
            List<ShipTemplateSettingDO> items = settingVO.getItems();
            // Filter out price range Settings
            List<ShipTemplateSettingDO> priceSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.PRICE.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setPriceSettings(priceSettings);
            // Filter weight range Settings
            List<ShipTemplateSettingDO> weightSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.WEIGHT.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setWeightSettings(weightSettings);
            // Filter out price range Settings
            List<ShipTemplateSettingDO> itemsSettings = items.stream().filter(settingDO -> settingDO.getConditionsType().equalsIgnoreCase(ConditionsTypeEnums.ITEMS.name())).sorted(Comparator.comparing(ShipTemplateSettingDO::getSort)).collect(Collectors.toList());
            shipTemplateSettingDTO.setItemsSettings(itemsSettings);
            templateMap.put(settingVO.getTemplateId(), shipTemplateSettingDTO);
            // Create freight template corresponding to commodity data
            List<CartSkuVO> skuList = templateSkuMap.get(settingVO.getTemplateId());
            if(skuList == null){
                skuList = new ArrayList<>();
            }
            skuList.add(cartSkuVO);

            templateSkuMap.put(settingVO.getTemplateId(),skuList);
        }

        // To calculate
        for (Integer key : templateSkuMap.keySet()) {

            List<CartSkuVO> skuList = templateSkuMap.get(key);

            ShipTemplateSettingDTO template = templateMap.get(key);

            // The freight amount
            Double shipPrice = 0D;
            Double goodsPrice = 0D;
            for (CartSkuVO cartSkuVO : skuList) {
                Double originalPrice = CurrencyUtil.mul(cartSkuVO.getOriginalPrice(), cartSkuVO.getNum());
                goodsPrice = CurrencyUtil.add(goodsPrice, originalPrice);
            }
            // The price of goods is calculated as freight
            List<ShipTemplateSettingDO> priceSettings = template.getPriceSettings();
            if (priceSettings!=null&&priceSettings.size()>0){
                ShipTemplateSettingDO setting = screen(priceSettings,goodsPrice);
                if (setting!=null){
                    shipPrice = addShipPirce(shipPrice, goodsPrice, setting);
                }

            }

            // Freight by weight
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

            // Piece rate
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
            logger.debug("The final freight amount is calculated：" + finalShip);

        }
        return finalShip;
    }

    /**
     * Filter Settings
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
     * Increased freight price
     * @param shipPrice
     * @param goodsPrice
     * @param setting
     * @return
     */
    private Double addShipPirce(Double shipPrice, Double goodsPrice, ShipTemplateSettingDO setting) {
        if (AmtTypeEnums.ABSOLUTE.name().equalsIgnoreCase(setting.getAmtType())) {
            // If its absolute value, it just adds up
            shipPrice = CurrencyUtil.add(shipPrice, setting.getAmt());
        } else if (AmtTypeEnums.PERCENTAGE.name().equalsIgnoreCase(setting.getAmtType())) {
            // If its a percentage, its the original price * percentage
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

        // Detect goods outside the distribution area
        this.checkArea(cartList, address.getCountryCode(),address.getStateCode());

        for (CartVO cartVo : cartList) {

            List<PromotionRule> ruleList = cartVo.getRuleList();
            // No postage will be calculated if free postage is added
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

            // Get cart item shipping totals
            double finalShip = this.getShipPrice(cartVo);
            cartVo.getPrice().setFreightPrice(finalShip);
            if (finalShip > 0) {
                cartVo.getPrice().setIsFreeFreight(0);
            }
            cartVo.setShippingTypeName("freight");
        }


    }

    /**
     * Check area
     *
     * @param cartList The shopping cart
     * @param countryCode   countriescode
     * @param stateCode      chaucode
     * @return
     */
    @Override
    public List<CacheGoods> checkArea(List<CartVO> cartList, String countryCode,String stateCode) {
        List<CacheGoods> errorGoods = new ArrayList<>();
        for (CartVO cartVo : cartList) {
            // Freight template mapping
            Map<Integer, ShipTemplateSettingVO> shipMap = new HashMap<>(16);
            List<CartSkuVO> cartSkuVOS = cartVo.getSkuList();
            for (CartSkuVO skuVO : cartSkuVOS) {
                // If it is not selected, it is not processed
                if (skuVO.getChecked() == 0) {
                    continue;
                }
                // Not the freight
                if (skuVO.getIsFreeFreight() != 1) {
                    skuVO.setIsShip(1);
                    // If there is no freight template, it is forbidden to record the wrong goods
                    ShipTemplateVO temp = this.shipTemplateClient.get(skuVO.getTemplateId());
                    // If template is empty
                    if (temp == null) {
                        errorGoods.add(goodsClient.getFromCache(skuVO.getGoodsId()));
                        skuVO.setIsShip(0);
                    } else {

                        for (ShipTemplateSettingVO settingVO : temp.getItems()) {
                            if (settingVO.getAreaId() != null) {
                                /** Check area*/
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
                        // If there is no match, pass
                        if (!shipMap.containsKey(skuVO.getSkuId())) {
                            errorGoods.add(goodsClient.getFromCache(skuVO.getGoodsId()));
                            skuVO.setIsShip(0);
                        }
                    }
                } else {
                    // If no freight template is set, the default region is available
                    skuVO.setIsShip(1);
                }
            }
            cartVo.setShipTemplateChildMap(shipMap);
        }
        return errorGoods;
    }


}
