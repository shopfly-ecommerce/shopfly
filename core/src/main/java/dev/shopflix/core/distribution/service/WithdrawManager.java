/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service;

import dev.shopflix.core.distribution.model.dos.WithdrawApplyDO;
import dev.shopflix.core.distribution.model.vo.BankParamsVO;
import dev.shopflix.core.distribution.model.vo.WithdrawApplyVO;
import dev.shopflix.framework.database.Page;

import java.util.Map;


/**
 * 提现接口
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午1:18
 */
public interface WithdrawManager {

    /**
     * 根据ID提现申请详细记录
     *
     * @param id
     * @return
     */
    WithdrawApplyDO getModel(Integer id);

    /**
     * 申请提现
     *
     * @param memberId    会员id
     * @param applyMoney  申请金额
     * @param applyRemark 备注
     */
    void applyWithdraw(Integer memberId, Double applyMoney, String applyRemark);


    /**
     * 审核提现申请
     *
     * @param applyId     提现申请id
     * @param remark      备注
     * @param auditResult 审核结果
     */
    void auditing(Integer applyId, String remark, String auditResult);


    /**
     * 财务打款
     *
     * @param applyId 提现申请id
     * @param remark  备注
     */
    void transfer(Integer applyId, String remark);


    /**
     * 根据member_id查询提现记录
     *
     * @param memeberId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<WithdrawApplyVO> pageWithdrawApply(Integer memeberId, Integer pageNo, Integer pageSize);

    /**
     * 保存提现设置
     *
     * @param bankParams
     */
    void saveWithdrawWay(BankParamsVO bankParams);

    /**
     * 获取提现设置
     *
     * @param memberId
     * @return
     */
    BankParamsVO getWithdrawSetting(int memberId);

    /**
     * 分页会员提现查询
     *
     * @param pageNo
     * @param pageSize
     * @param map
     * @return
     */
    Page<WithdrawApplyVO> pageApply(Integer pageNo, Integer pageSize, Map<String, String> map);


    /**
     * 分页会员提现查询
     *
     * @param memberId
     * @return
     */
    Double getRebate(Integer memberId);
}
