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

import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.service.CommissionTplManager;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 模版管理实现类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午11:00
 */

@Service("commissionTplManager")
public class CommissionTplManagerImpl implements CommissionTplManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public CommissionTpl getModel(int tplId) {

        String sql = "SELECT * FROM es_commission_tpl WHERE id = ?";
        CommissionTpl commissionTpl = this.daoSupport.queryForObject(sql, CommissionTpl.class, tplId);
        return commissionTpl;

    }

    @Override
    public CommissionTpl getCommissionTplByMember(int memberid) {

        // 如果是个正确的模板id
        String sql = "SELECT * FROM es_commission_tpl where id in (select current_id from es_distribution WHERE member_id = ?)";
        CommissionTpl commissionTpl = this.daoSupport.queryForObject(sql,
                CommissionTpl.class, memberid);
        return commissionTpl;
    }

    @Override
    public Page page(int page, int pageSize) {
        return this.daoSupport.queryForPage("select * from es_commission_tpl", page, pageSize, CommissionTpl.class);
    }

    @Override
    public CommissionTpl add(CommissionTpl commissionTpl) {
        this.daoSupport.insert("es_commission_tpl", commissionTpl);
        Integer id = this.daoSupport.getLastId("es_commission_tpl");

        if (commissionTpl.getIsDefault() == 1) {
            this.daoSupport.execute("update es_commission_tpl set is_default = 0 where id != ?", id);
        }
        return commissionTpl;
    }


    @Override
    public CommissionTpl edit(CommissionTpl commissionTpl) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", commissionTpl.getId());
        this.daoSupport.update("es_commission_tpl", commissionTpl, map);
        if (commissionTpl.getIsDefault() == 1) {
            this.daoSupport.execute("update es_commission_tpl set is_default = 0 where id != ?", commissionTpl.getId());
        }
        return commissionTpl;

    }


    @Override
    public void delete(Integer tplId) {

        CommissionTpl commissionTpl = this.getModel(tplId);
        if (commissionTpl.getIsDefault() == 1) {
            throw new DistributionException(DistributionErrorCode.E1010.code(), DistributionErrorCode.E1010.des());
        }
        if (this.daoSupport.queryForInt("select count(0) from es_distribution where current_tpl_id = ?", tplId) > 0) {
            throw new DistributionException(DistributionErrorCode.E1012.code(), DistributionErrorCode.E1012.des());
        }

        this.daoSupport.execute("delete from es_commission_tpl where id = ?", tplId);

    }


    @Override
    public CommissionTpl getDefaultCommission() {
        String sql = "select * from es_commission_tpl where is_default=1";
        return this.daoSupport.queryForObject(sql, CommissionTpl.class);
    }


}
