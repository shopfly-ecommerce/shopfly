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
package cloud.shopfly.b2c.core.promotion.tool.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Promotion tools enumeration
 * <p>Class annotatedCart Refers to thecom.enation.app.shop.trade.model.vo.CartDO
 * @author Snow
 * @since V6.4
 * @version v1.0
 * 2017years08month18day17:55:35
 */
public enum PromotionTypeEnum {

	/**
	 * Not participating in activities（Not participating in any single product activity）
	 */
	NO("no","Not participating in activities"),

	/**
	 * Integral goods
	 */
	POINT("point","Integral activities"),

	/**
	 * Single product reduction activity
	 * When calculating the price:
	 * 1、CartDO.SkuList.Sku.purchase_price It needs to be modified.<br>
	 * 2、CartDO.price.discount_price You have to add them up.<br>
	 * 3、CartDO.SkuList.Sku.subtotal Need to compute<br>
	 */
	MINUS("minusPlugin","Item set"),

	/**
	 * Group-buying activities
	 * When calculating the price:
	 * 1、CartDO.SkuList.Sku.purchase_price It needs to be modified.<br>
	 * 2、CartDO.price.discount_price You have to add them up.<br>
	 * 3、CartDO.SkuList.Sku.subtotal Need to compute<br>
	 */
	GROUPBUY("groupBuyGoodsPlugin","A bulk"),

	/**
	 *Points for purchase
	 * When calculating the price:
	 * 1、CartDO.SkuList.Sku.point It needs to be modified.<br>
	 * 2、CartDO.price.exchange_point You have to add them up.<br>
	 * 3、CartDO.price.discount_price You have to add them up.<br>
	 * 4、CartDO.SkuList.Sku.subtotal Need to compute<br>
	 */
	EXCHANGE("exchangePlugin","Integral for"),

	/**
	 * The second half price event
	 * When calculating the price:
	 * 1、CartDO.price.discount_price You have to add them up.<br>
	 * 2、CartDO.SkuList.Sku.subtotal Need to compute<br>
	 */
	HALF_PRICE("halfPricePlugin","The second one is half price"),

	/**
	 *Full discount
	 * When calculating the price:
	 * 1、CartDO.price.discount_price You have to add them up.<br>
	 */
	FULL_DISCOUNT("fullDiscountPlugin","With preferential"),

	/**
	 * flash
	 * If the item participates in the display purchase activity, it is not allowed to participate in other activities.
	 * When calculating the price：
	 * 1、If the purchased quantity does not exceed the sold empty quantity, the commodity price is the activity price set by the merchant; if it exceeds the sold empty quantity, the commodity price is set as the original price.
	 */
	SECKILL("seckillPlugin","flash"),

	/**
	 * Group activity type
	 * Designated goods, preferential prices
	 */
	PINTUAN("pintuanPlugin","Spell group");

	private String pluginId;
	private String promotionName;

	/**
	 * The constructor
	 * @param pluginId
	 * @param promotionName
	 */
	PromotionTypeEnum(String pluginId, String promotionName) {
		this.pluginId = pluginId;
		this.promotionName = promotionName;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}


	//--------------------------------------------------
	//--------------------------------------------------


	/**
	 * Read the item activity collection
	 * @return
	 */
	public static List<String> getSingle(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.EXCHANGE.getPluginId());
		pluginId.add(PromotionTypeEnum.GROUPBUY.getPluginId());
		pluginId.add(PromotionTypeEnum.HALF_PRICE.getPluginId());
		pluginId.add(PromotionTypeEnum.MINUS.getPluginId());

		// Pay attention to the following activities and do not participate in any other activities
		pluginId.add(PromotionTypeEnum.SECKILL.getPluginId());
		return pluginId;
	}

	/**
	 * Read the composite activity collection
	 * @return
	 */
	public static List<String> getGroup(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.FULL_DISCOUNT.getPluginId());
		return pluginId;
	}

	/**
	 * An activity tool that reads some activities that have a separate inventory
	 * 1：Flash sales(When an order is paid, increase the quantity sold)
	 * 2：Group-buying activities(When an order is created, increase the quantity sold)
	 * @return
	 */
	public static List<String> getIndependent(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.SECKILL.getPluginId());
		pluginId.add(PromotionTypeEnum.GROUPBUY.getPluginId());
		return pluginId;
	}

	/**
	 * Determine if it is a single product activity
	 * @param type
	 * @return
	 */
	public static boolean isSingle(String type){
		if (PromotionTypeEnum.EXCHANGE.name().equals(type)) {
			return true;
		} else if (PromotionTypeEnum.GROUPBUY.name().equals(type)) {
			return true;
		} else if (PromotionTypeEnum.HALF_PRICE.name().equals(type)) {
			return true;
		} else if (PromotionTypeEnum.MINUS.name().equals(type)) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if it is a combined activity
	 * @param type
	 * @return
	 */
	public boolean isGroup(String type){
        return PromotionTypeEnum.FULL_DISCOUNT.name().equals(type);
    }

	/**
	 * Gets the plug-in name. According to thetypefield
	 * @param type
	 * @return
	 */
	public static String getPlugin(String type) {
		if (PromotionTypeEnum.EXCHANGE.name().equals(type)) {
			return PromotionTypeEnum.EXCHANGE.pluginId;
		} else if (PromotionTypeEnum.FULL_DISCOUNT.name().equals(type)) {
			return PromotionTypeEnum.FULL_DISCOUNT.pluginId;
		} else if (PromotionTypeEnum.GROUPBUY.name().equals(type)) {
			return PromotionTypeEnum.GROUPBUY.pluginId;
		} else if (PromotionTypeEnum.HALF_PRICE.name().equals(type)) {
			return PromotionTypeEnum.HALF_PRICE.pluginId;
		} else if (PromotionTypeEnum.MINUS.name().equals(type)) {
			return PromotionTypeEnum.MINUS.pluginId;
		} else {
			return "";
		}
	}


	/**
	 * Find enumeration values by activity type
	 * @param promotionName
	 * @return Returns null if there is no matching name
	 */
	public static PromotionTypeEnum myValueOf(String promotionName) {

		try {
			return PromotionTypeEnum.valueOf(promotionName);
		} catch (Exception e) {
			System.out.println("Discover unknown activity types："+ promotionName);
			return null;
		}

	}

}
