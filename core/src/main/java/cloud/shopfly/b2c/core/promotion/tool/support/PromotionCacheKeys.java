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
package cloud.shopfly.b2c.core.promotion.tool.support;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 促销活动缓存
 * @author Snow
 * @since v6.4
 * @version v1.0
 * 2017年08月18日17:58:12
 */
public class PromotionCacheKeys {

	/**
	 * 读取积分换购redis key
	 * @param activityId
	 * @return
	 */
	public static final String getExchangeKey(Integer activityId){
		String key = CachePrefix.STORE_ID_EXCHANGE_KEY.getPrefix()+ activityId;
		return key;
	}

	/**
	 * 读取满优惠redis key
	 * @param activityId
	 * @return
	 */
	public static final String getFullDiscountKey(Integer activityId){
		String key = CachePrefix.STORE_ID_FULL_DISCOUNT_KEY.getPrefix()+ activityId;
		return key;
	}

	/**
	 * 读取团购活动redis key
	 * @param activityId
	 * @return
	 */
	public static final String getGroupbuyKey(Integer activityId){
		String key = CachePrefix.STORE_ID_GROUP_BUY_KEY.getPrefix()+ activityId;
		return key;
	}

	/**
	 * 读取第二件半价活动redis key
	 * @param activityId
	 * @return
	 */
	public static final String getHalfPriceKey(Integer activityId){
		String key = CachePrefix.STORE_ID_HALF_PRICE_KEY.getPrefix()+ activityId;
		return key;
	}

	/**
	 *
	 * 读取单品立减活动 redis key
	 * @param activityId
	 * @return
	 */
	public static final String getMinusKey(Integer activityId){
		String key = CachePrefix.STORE_ID_MINUS_KEY.getPrefix()+ activityId;
		return key;
	}

	/**
	 * 读取限时抢购 redis key
	 * @param time 格式为(年月日):20171215   如果为空则默认查询当天
	 * @return
	 */
	public static final String getSeckillKey(String time){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		if(StringUtil.isEmpty(time)){
			time = dateFormat.format(new Date());
		}
		String key = CachePrefix.STORE_ID_SECKILL_KEY.getPrefix()+"_"+time;
		return key;
	}


}
