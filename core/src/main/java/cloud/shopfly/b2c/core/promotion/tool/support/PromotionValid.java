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

import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;

import java.util.List;

/**
 * Parameter validation
 * @author Snow create in 2018/3/30
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionValid {


    /**
     * Parameter validation
     * 1、The start time of the activity must be greater than the current time
     * 2、Verify whether the activity start time is longer than the activity end time
     *
     * No return value. If there is an error, throw an exception directly
     * @param startTime Activity start time
     * @param endTime   End time
     * @param rangeType Whether all goods participate
     * @param goodsList Selection of goods
     *
     */
    public static void paramValid(Long startTime, Long endTime, int rangeType, List<PromotionGoodsDTO> goodsList){

        long nowTime  = DateUtil.getDateline();

        // If the activity start time is less than the present time
        if(startTime.longValue() < nowTime){
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"The start time of the activity must be greater than the current time");
        }

        // The start time cannot be later than the end time
        if (startTime.longValue() > endTime.longValue() ) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"The start time cannot be later than the end time");
        }

        // Part of the goods
        int part = 2;

        // If the promotional activities choose to participate in part of the commodity activities
        if (rangeType == part) {
            // The commodity ID group cannot be empty
            if (goodsList == null) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"Please select the products to participate in the campaign");
            }
        }
    }


}
