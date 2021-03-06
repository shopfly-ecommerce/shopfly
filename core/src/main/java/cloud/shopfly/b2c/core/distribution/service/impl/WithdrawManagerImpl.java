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
import cloud.shopfly.b2c.core.distribution.model.dos.WithdrawApplyDO;
import cloud.shopfly.b2c.core.distribution.model.dos.WithdrawSettingDO;
import cloud.shopfly.b2c.core.distribution.model.enums.WithdrawStatusEnum;
import cloud.shopfly.b2c.core.distribution.model.vo.BankParamsVO;
import cloud.shopfly.b2c.core.distribution.model.vo.WithdrawApplyVO;
import cloud.shopfly.b2c.core.distribution.service.WithdrawManager;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Withdrawal setup implementation
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon12:57
 */

@Service
public class WithdrawManagerImpl implements WithdrawManager {


    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    public WithdrawApplyDO getModel(Integer id) {
        String applysql = "select * from es_withdraw_apply where id=?";
        return this.daoSupport.queryForObject(applysql, WithdrawApplyDO.class, id);

    }


    @Override
    public Page<WithdrawApplyVO> pageWithdrawApply(Integer memberId, Integer pageNo, Integer pageSize) {
        String sql = "select * from es_withdraw_apply where member_id=? order by id desc";

        Page<WithdrawApplyDO> page = this.daoSupport.queryForPage(sql, pageNo, pageSize, WithdrawApplyDO.class, memberId);
        Page result = new Page();
        result.setPageNo(page.getPageNo());
        result.setPageSize(page.getPageSize());
        result.setDataTotal(page.getDataTotal());
        List<WithdrawApplyVO> vos = new ArrayList<>();
        for (WithdrawApplyDO withdrawApplyDO : page.getData()) {
            vos.add(new WithdrawApplyVO(withdrawApplyDO));
        }

        result.setData(vos);
        return result;

    }


    @Override
    public void saveWithdrawWay(BankParamsVO bankParams) {
        String sql = "select * from es_withdraw_setting where member_id=?";
        Integer userId = UserContext.getBuyer().getUid();
        WithdrawSettingDO withdrawSetting = this.daoSupport.queryForObject(sql, WithdrawSettingDO.class,
                userId);
        if (withdrawSetting != null) {
            withdrawSetting.setMemberId(userId);
            withdrawSetting.setParam(JsonUtil.objectToJson(bankParams));
            Map where = new HashMap(16);
            where.put("id", withdrawSetting.getId());
            this.daoSupport.update("es_withdraw_setting", withdrawSetting, where);
        } else {
            withdrawSetting = new WithdrawSettingDO();
            withdrawSetting.setMemberId(userId);
            withdrawSetting.setParam(JsonUtil.objectToJson(bankParams));
            this.daoSupport.insert("es_withdraw_setting", withdrawSetting);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyWithdraw(Integer memberId, Double applyMoney, String applyRemark) {
        WithdrawApplyDO apply = new WithdrawApplyDO();
        apply.setApplyTime(DateUtil.getDateline());
        apply.setApplyMoney(applyMoney);
        apply.setApplyRemark(applyRemark);
        apply.setStatus(WithdrawStatusEnum.APPLY.name());
        apply.setMemberId(memberId);
        apply.setMemberName(UserContext.getBuyer().getUsername());
        this.daoSupport.insert("es_withdraw_apply", apply);
        // Modify withdrawal amount
        String sql = "update es_distribution set can_rebate=can_rebate-?,withdraw_frozen_price=withdraw_frozen_price+? where member_id =?";
        this.daoSupport.execute(sql, applyMoney, applyMoney, memberId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void auditing(Integer applyId, String remark, String auditResult) throws DistributionException {
        WithdrawApplyDO wdo = this.getModel(applyId);
        if (wdo == null) {
            throw new DistributionException(DistributionErrorCode.E1004.code(), DistributionErrorCode.E1004.des());
        }
        // If it has been reviewed
        if (wdo.getInspectTime() != null) {
            if (wdo.getInspectTime() != 0) {
                throw new DistributionException(DistributionErrorCode.E1002.code(), DistributionErrorCode.E1002.des());
            }
        }
        if (StringUtil.isEmpty(auditResult)) {
            throw new DistributionException(DistributionErrorCode.E1005.code(), DistributionErrorCode.E1005.des());
        }
        // Audit time
        Long auditingTime = DateUtil.getDateline();
        // approved
        if (auditResult.equals(WithdrawStatusEnum.FAIL_AUDITING.name())) {
            String applySql = "update es_withdraw_apply set status=?,inspect_time=?,inspect_remark=? where id=?";
            this.daoSupport.execute(applySql, auditResult, auditingTime, remark, applyId);

            // Return the amount to be withdrawn
            String applysql = "select * from es_withdraw_apply where id=?";
            WithdrawApplyDO apply = this.daoSupport.queryForObject(applysql, WithdrawApplyDO.class, applyId);

            String sql = "update es_distribution set can_rebate=can_rebate+?,withdraw_frozen_price=withdraw_frozen_price-? where member_id =?";
            this.daoSupport.execute(sql, apply.getApplyMoney(), apply.getApplyMoney(),
                    apply.getMemberId());
        } else if (auditResult.equals(WithdrawStatusEnum.VIA_AUDITING.name())) {
            String applySql = "update es_withdraw_apply set status=?,inspect_time=?,inspect_remark=? where id=?";
            this.daoSupport.execute(applySql, auditResult, auditingTime, remark, applyId);
        } else {
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @Override
    public void transfer(Integer applyId, String remark) {
        // If it has been reviewed
        WithdrawApplyDO wdo = this.getModel(applyId);
        if (wdo == null) {
            throw new DistributionException(DistributionErrorCode.E1004.code(), DistributionErrorCode.E1004.des());
        }
        if (wdo.getTransferTime() != null) {
            if (wdo.getTransferTime() != 0) {
                throw new DistributionException(DistributionErrorCode.E1002.code(), DistributionErrorCode.E1002.des());
            }
        }
        String applySql = "update es_withdraw_apply set status=?,transfer_time=?,transfer_remark=? where id=?";
        this.daoSupport.execute(applySql, WithdrawStatusEnum.TRANSFER_ACCOUNTS.name(), DateUtil.getDateline(), remark, applyId);
    }

    @Override
    public BankParamsVO getWithdrawSetting(int memberId) {
        String sql = "select * from es_withdraw_setting where member_id=?";
        WithdrawSettingDO withdrawSetting = this.daoSupport.queryForObject(sql, WithdrawSettingDO.class,
                memberId);
        if (withdrawSetting == null) {
            return new BankParamsVO();
        }
        return JsonUtil.jsonToObject(withdrawSetting.getParam(), BankParamsVO.class);
    }

    @Override
    public Page<WithdrawApplyVO> pageApply(Integer pageNo, Integer pageSize, Map<String, String> map) {
        Page<WithdrawApplyDO> page;
        List paramList = new ArrayList();
        List<String> sqlList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from es_withdraw_apply ");

        if (!StringUtil.isEmpty(map.get("uname"))) {
            sqlList.add(" member_name like ? ");
            paramList.add("%" + map.get("uname") + "%");
        }
        if (!StringUtil.isEmpty(map.get("start_time"))) {
            sqlList.add(" apply_time > ? ");
            paramList.add(map.get("start_time"));
        }
        if (!StringUtil.isEmpty(map.get("end_time"))) {
            sqlList.add(" apply_time < ? ");
            paramList.add(map.get("end_time"));
        }
        if (!StringUtil.isEmpty(map.get("status"))) {
            sqlList.add(" status = ? ");
            paramList.add(map.get("status"));

        }

        sql.append(SqlUtil.sqlSplicing(sqlList));
        sql.append(" order by id desc");

        page = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, WithdrawApplyDO.class, paramList.toArray());
        Page<WithdrawApplyVO> result = convertPage(page);
        return result;
    }

    @Override
    public Double getRebate(Integer memberId) {
        Double rebate = this.daoSupport.queryForDouble("select can_rebate from es_distribution where member_id = ? ", memberId);
        return rebate <= 0 ? 0 : rebate;
    }

    /**
     * conversionpage
     * @param page
     * @return
     */
    private Page convertPage(Page<WithdrawApplyDO> page) {

        List<WithdrawApplyVO> vos = new ArrayList<>();
        for (WithdrawApplyDO withdrawApplyDO : page.getData()) {

            WithdrawApplyVO applyVO = new WithdrawApplyVO(withdrawApplyDO);
            BankParamsVO paramsVO = this.getWithdrawSetting(withdrawApplyDO.getMemberId());
            applyVO.setBankParamsVO(paramsVO);
            vos.add(applyVO);
        }
        Page result = new Page(page.getPageNo(),page.getDataTotal(),page.getPageSize(),vos);
        return result;
    }
}
