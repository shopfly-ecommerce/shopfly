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
package cloud.shopfly.b2c.core.promotion.tool.service.impl;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity rule detection
 *
 * @author Snow create in 2018/4/25
 * @version v2.0
 * @since v7.0.0
 */
@Service
public abstract class AbstractPromotionRuleManagerImpl {

    @Autowired
    
    private DaoSupport daoSupport;





    /**
     * Detect rule conflicts between activities
     *
     * @param goodsDTOList Activities of goods
     */
    protected void verifyRule(List<PromotionGoodsDTO> goodsDTOList) {

        if (goodsDTOList == null || goodsDTOList.isEmpty()) {
            throw new ServiceException(PromotionErrorCode.E401.code(), "No goods available");
        }
    }

    /**
     * The validation activity has the same name
     * @param name  The name of the
     * @param isUpdate  Whether to modify
     * @param activeId  You need to fill in the activity when you modifyid
     */
    protected void verifyName(String name,boolean isUpdate,Integer activeId) {

        if(isUpdate){
            // Identify activities with the same name
            if (this.daoSupport.queryForInt("select count(0) from es_groupbuy_active where act_name=? and act_id != ?", name,activeId) > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "Current activity has same name, please correct");
            }
        }else {
            // Identify activities with the same name
            if (this.daoSupport.queryForInt("select count(0) from es_groupbuy_active where act_name=?", name) > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "Current activity has same name, please correct");
            }
        }
    }

    /**
     * Validation activity time
     * Only one activity can be active at a time
     *
     * @param startTime
     * @param endTime
     */
    protected void verifyTime(long startTime, long endTime, PromotionTypeEnum typeEnum, Integer activityId) {

        // (The start time of the new activity is greater than the start time of the previous activity and less than the end time of the previous activity) or (The end time of the new activity is greater than the start time of the previous activity and less than the end time of the previous activity)
        String sql = "";
        List params = new ArrayList();

        switch (typeEnum) {
            case HALF_PRICE:
                sql = "select count(0) from es_half_price where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and hp_id != ?";
                    params.add(activityId);
                }
                break;

            case MINUS:
                sql = "select count(0) from es_minus where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and minus_id != ?";
                    params.add(activityId);
                }
                break;

            case FULL_DISCOUNT:
                sql = "select count(0) from es_full_discount where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and fd_id != ?";
                    params.add(activityId);
                }
                break;

            case GROUPBUY:
                sql = "select count(0) from es_groupbuy_active where ((start_time <= ? and ? <= end_time ) or (start_time <= ? and ? <= end_time ))";
                params.add(startTime);
                params.add(startTime);
                params.add(endTime);
                params.add(endTime);
                if (activityId != null) {
                    sql += " and act_id != ?";
                    params.add(activityId);
                }
                break;

            case SECKILL:
                sql = "select count(0) from es_seckill  where ((start_day <= ? and ? <= start_day ))";
                params.add(startTime);
                params.add(startTime);
                if (activityId != null) {
                    sql += " and seckill_id != ?";
                    params.add(activityId);
                }
                break;

            case NO:
                break;

            default:
                break;
        }

        if (!StringUtil.isEmpty(sql)) {
            int num = this.daoSupport.queryForInt(sql, params.toArray());
            if (num > 0) {
                throw new ServiceException(PromotionErrorCode.E402.code(), "This activity already exists in the current time");
            }
        }

    }

}
