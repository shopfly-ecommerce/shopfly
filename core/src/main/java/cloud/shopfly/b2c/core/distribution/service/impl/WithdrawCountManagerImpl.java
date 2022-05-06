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
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.distribution.service.WithdrawCountManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WithdrawCountManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-15 上午8:43
 */
@Service
public class WithdrawCountManagerImpl implements WithdrawCountManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired

    private DaoSupport daoSupport;

    /**
     * 整理解冻金额
     */
    @Override
    public void withdrawCount() {
        try {
            Long currentData = DateUtil.getDateline();
            String sql = "UPDATE es_distribution distribution SET can_rebate = can_rebate + \n" +
                    "IFNULL(( SELECT ( IFNULL( ( SELECT sum(disorder.grade1_rebate) FROM es_distribution_order disorder WHERE member_id_lv1 = distribution.member_id AND disorder.is_withdraw = 0 and disorder.settle_cycle <? ), 0 )+ IFNULL( ( SELECT sum(disorder.grade2_rebate) FROM es_distribution_order disorder  WHERE member_id_lv2 = distribution.member_id AND disorder.is_withdraw = 0 and disorder.settle_cycle <? ), 0 ) ) ), 0 )\n" +
                    ",commission_frozen = commission_frozen-IFNULL(( SELECT ( IFNULL( ( SELECT sum(disorder.grade1_rebate) FROM es_distribution_order disorder WHERE member_id_lv1 = distribution.member_id AND disorder.is_withdraw = 0 and disorder.settle_cycle <? ), 0 )+ IFNULL( ( SELECT sum(disorder.grade2_rebate) FROM es_distribution_order disorder  WHERE member_id_lv2 = distribution.member_id AND disorder.is_withdraw = 0 and disorder.settle_cycle <? ), 0 ) ) ), 0 )\n";

            this.daoSupport.execute(sql, currentData, currentData, currentData, currentData);
            this.daoSupport.execute("update es_distribution_order set is_withdraw = 1 where settle_cycle <?", currentData);
        } catch (Exception e) {
            logger.error("每日将解锁金额自动添加到可提现金额异常：", e);
        }
    }
}
