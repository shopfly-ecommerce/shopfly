/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.service;

import dev.shopflix.core.aftersale.model.dos.RefundDO;
import dev.shopflix.core.aftersale.model.dos.RefundGoodsDO;
import dev.shopflix.core.aftersale.model.dto.RefundDTO;
import dev.shopflix.core.aftersale.model.dto.RefundDetailDTO;
import dev.shopflix.core.goods.model.enums.Permission;
import dev.shopflix.core.aftersale.model.vo.*;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 售后管理接口
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 下午3:07 2018/5/2
 */
public interface AfterSaleManager {

    /**
     * 申请退款
     *
     * @param refundApply 退款申请
     */
    void applyRefund(BuyerRefundApplyVO refundApply);


    /**
     * 退货申请
     *
     * @param refundApply
     */
    void applyGoodsReturn(BuyerRefundApplyVO refundApply);

    /**
     * 买家对已付款的订单执行取消操作
     *
     * @param buyerCancelOrderVO
     */
    void cancelOrder(BuyerCancelOrderVO buyerCancelOrderVO);

    /**
     * 管理员审批一个退货（款）
     *
     * @param refundApproval 批准 vo
     * @param permission 权限
     * @return 批准 vo
     */
    AdminRefundApprovalVO approval(AdminRefundApprovalVO refundApproval, Permission permission);

    /**
     * 财务审核/执行一个退款
     * @param refundApproval
     * @return
     */
    FinanceRefundApprovalVO approval(FinanceRefundApprovalVO refundApproval);

    /**
     * 根据参数查询退款（货）单
     *
     * @param param 查询参数
     * @return
     */
    Page<RefundDTO> query(RefundQueryParamVO param);

    /**
     * 根据编号获取详细
     *
     * @param sn 单据编号
     * @return
     */
    RefundDetailDTO getDetail(String sn);

    /**
     * 查询退款方式为原路退回且状态为退款中的退款单
     *
     * @return
     */
    List<RefundDO> queryNoReturnOrder();

    /**
     * 更新退款单的状态
     *
     * @param list 退款单列表
     */
    void update(List<RefundDO> list);

    /**
     * 获取退款申请信息
     *
     * @param orderSn
     * @param skuId
     * @return
     */
    RefundApplyVO refundApply(String orderSn, Integer skuId);

    /**
     * 获取未完成售后订单数量
     *
     * @param memberId
     * @return
     */
    Integer getAfterSaleCount(Integer memberId);


    /**
     * 获取退货单的商品列表
     *
     * @param sn 退款单号
     * @return 退货商品列表
     */
    List<RefundGoodsDO> getRefundGoods(String sn);

    /**
     * 查询退款单状态
     */
    void queryRefundStatus();

    /**
     * 入库
     *
     * @param sn     退款单
     * @param remark 备注
     */
    void stockIn(String sn, String remark);

    /**
     * 退款
     *
     * @param sn     退款单号
     * @param remark 退款备注
     */
    void refund(String sn, String remark);

    /**
     * 系统对已付款的订单执行取消操作
     * @param buyerCancelOrderVO
     */
    void sysCancelOrder(BuyerCancelOrderVO buyerCancelOrderVO);

    /**
     * 退款单导出excel
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 字节流
     */
    List<ExportRefundExcelVO> exportExcel(long startTime, long endTime);

}
