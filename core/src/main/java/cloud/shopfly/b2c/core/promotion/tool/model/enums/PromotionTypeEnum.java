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
 * 促销活动工具枚举
 * <p>类中注释的Cart 指com.enation.app.shop.trade.model.vo.CartDO
 * @author Snow
 * @since V6.4
 * @version v1.0
 * 2017年08月18日17:55:35
 */
public enum PromotionTypeEnum {

	/**
	 * 不参与活动（指不参与任何单品活动）
	 */
	NO("no","不参与活动"),

	/**
	 * 积分商品
	 */
	POINT("point","积分活动"),

	/**
	 * 单品立减活动
	 * 计算价格时:
	 * 1、CartDO.SkuList.Sku.purchase_price 需要修改。<br>
	 * 2、CartDO.price.discount_price 需要累加。<br>
	 * 3、CartDO.SkuList.Sku.subtotal 需要计算<br>
	 */
	MINUS("minusPlugin","单品立减"),

	/**
	 * 团购活动
	 * 计算价格时:
	 * 1、CartDO.SkuList.Sku.purchase_price 需要修改。<br>
	 * 2、CartDO.price.discount_price 需要累加。<br>
	 * 3、CartDO.SkuList.Sku.subtotal 需要计算<br>
	 */
	GROUPBUY("groupBuyGoodsPlugin","团购"),

	/**
	 *积 分换购活动
	 * 计算价格时:
	 * 1、CartDO.SkuList.Sku.point 需要修改。<br>
	 * 2、CartDO.price.exchange_point 需要累加。<br>
	 * 3、CartDO.price.discount_price 需要累加。<br>
	 * 4、CartDO.SkuList.Sku.subtotal 需要计算<br>
	 */
	EXCHANGE("exchangePlugin","积分换购"),

	/**
	 * 第二件半价活动
	 * 计算价格时:
	 * 1、CartDO.price.discount_price 需要累加。<br>
	 * 2、CartDO.SkuList.Sku.subtotal 需要计算<br>
	 */
	HALF_PRICE("halfPricePlugin","第二件半价"),

	/**
	 *满优惠活动
	 * 计算价格时:
	 * 1、CartDO.price.discount_price 需要累加。<br>
	 */
	FULL_DISCOUNT("fullDiscountPlugin","满优惠"),

	/**
	 * 限时抢购
	 * 如果商品参与的显示抢购活动，则不允许参与其他活动。
	 * 计算价格时：
	 * 1、如果购买的数量没有超过售空数量，则商品价格为商家设置的活动价，如果超过售空数量，则将商品的价格设置为原价。
	 */
	SECKILL("seckillPlugin","限时抢购"),

	/**
	 * 拼团活动类型
	 * 指定商品，优惠价格
	 */
	PINTUAN("pintuanPlugin","拼团");

	private String pluginId;
	private String promotionName;

	/**
	 * 构造器
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
	 * 读取单品活动集合
	 * @return
	 */
	public static List<String> getSingle(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.EXCHANGE.getPluginId());
		pluginId.add(PromotionTypeEnum.GROUPBUY.getPluginId());
		pluginId.add(PromotionTypeEnum.HALF_PRICE.getPluginId());
		pluginId.add(PromotionTypeEnum.MINUS.getPluginId());

		//注意以下活动，不参与其他任何活动
		pluginId.add(PromotionTypeEnum.SECKILL.getPluginId());
		return pluginId;
	}

	/**
	 * 读取组合活动集合
	 * @return
	 */
	public static List<String> getGroup(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.FULL_DISCOUNT.getPluginId());
		return pluginId;
	}

	/**
	 * 读取某些活动具有独立库存的活动工具
	 * 1：限时抢购活动 (订单付款时，增加已售数量)
	 * 2：团购活动 (订单创建时，增加已售数量)
	 * @return
	 */
	public static List<String> getIndependent(){
		List<String> pluginId = new ArrayList<>();
		pluginId.add(PromotionTypeEnum.SECKILL.getPluginId());
		pluginId.add(PromotionTypeEnum.GROUPBUY.getPluginId());
		return pluginId;
	}

	/**
	 * 判断是否是单品活动
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
	 * 判断是否是组合活动
	 * @param type
	 * @return
	 */
	public boolean isGroup(String type){
        return PromotionTypeEnum.FULL_DISCOUNT.name().equals(type);
    }

	/**
	 * 获取插件名称。根据type字段
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
	 * 根据活动类型找到枚举值
	 * @param promotionName
	 * @return 如果没有匹配的名字返回空
	 */
	public static PromotionTypeEnum myValueOf(String promotionName) {

		try {
			return PromotionTypeEnum.valueOf(promotionName);
		} catch (Exception e) {
			System.out.println("发现未知的活动类型："+ promotionName);
			return null;
		}

	}

}
