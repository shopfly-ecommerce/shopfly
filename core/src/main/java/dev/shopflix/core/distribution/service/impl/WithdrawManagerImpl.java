/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service.impl;

import dev.shopflix.core.distribution.exception.DistributionErrorCode;
import dev.shopflix.core.distribution.exception.DistributionException;
import dev.shopflix.core.distribution.model.dos.WithdrawApplyDO;
import dev.shopflix.core.distribution.model.dos.WithdrawSettingDO;
import dev.shopflix.core.distribution.model.enums.WithdrawStatusEnum;
import dev.shopflix.core.distribution.model.vo.BankParamsVO;
import dev.shopflix.core.distribution.model.vo.WithdrawApplyVO;
import dev.shopflix.core.distribution.service.WithdrawManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 提现设置实现
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午12:57
 */

@Service
public class WithdrawManagerImpl implements WithdrawManager {


    @Autowired
    @Qualifier("distributionDaoSupport")
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
        // 修改可提现金额
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
        //如果审核过
        if (wdo.getInspectTime() != null) {
            if (wdo.getInspectTime() != 0) {
                throw new DistributionException(DistributionErrorCode.E1002.code(), DistributionErrorCode.E1002.des());
            }
        }
        if (StringUtil.isEmpty(auditResult)) {
            throw new DistributionException(DistributionErrorCode.E1005.code(), DistributionErrorCode.E1005.des());
        }
        // 审核时间
        Long auditingTime = DateUtil.getDateline();
        //审核通过
        if (auditResult.equals(WithdrawStatusEnum.FAIL_AUDITING.name())) {
            String applySql = "update es_withdraw_apply set status=?,inspect_time=?,inspect_remark=? where id=?";
            this.daoSupport.execute(applySql, auditResult, auditingTime, remark, applyId);

            // 将要提现的金额返还
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
        //如果审核过
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
     * 转换page
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
