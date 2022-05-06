/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.framework.database.Page;


/**
 * 分销Order Manager接口
 *
 * @author Chopper
 * @version v1.0
 * @since v6.1
 * 2016年10月2日 下午5:24:14
 */
public interface DistributionOrderManager {

    /**
     * 根据sn获得分销商订单详情
     *
     * @param orderSn 订单编号
     * @return FxOrderDO
     */
    DistributionOrderDO getModelByOrderSn(String orderSn);

    /**
     * 根据id获得分销商订单详情
     *
     * @param orderId 订单id
     * @return FxOrderDO
     */
    DistributionOrderDO getModel(Integer orderId);

    /**
     * 保存一条数据
     *
     * @param distributionOrderDO
     * @return
     */
    DistributionOrderDO add(DistributionOrderDO distributionOrderDO);

    /**
     * 通过订单id，计算出各个级别的返利金额并保存到数据库
     *
     * @param orderSn 订单编号
     * @return 计算结果 true 成功， false 失败
     */
    boolean calCommission(String orderSn);


    /**
     * 通过订单id，把各个级别的返利金额增加到分销商冻结金额中
     *
     * @param orderSn 订单sn
     * @return 操作结果 true 成功， false 失败
     */
    boolean addDistributorFreeze(String orderSn);

    /**
     * 分销商退货订单分页
     *
     * @param pagesize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionSellbackOrderVO> pageSellBackOrder(Integer pagesize, Integer page, Integer memberId, Integer billId);


    /**
     * 结算单订单查询
     *
     * @param pageSize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionOrderVO> pageDistributionOrder(Integer pageSize, Integer page, Integer memberId, Integer billId);


    /**
     * 结算单订单查询
     *
     * @param pageSize
     * @param page
     * @param memberId
     * @param billId
     * @return
     */
    Page<DistributionOrderVO> pageDistributionTotalBillOrder(Integer pageSize, Integer page, Integer memberId, Integer billId);

    /**
     * 根据会员id获取营业额
     *
     * @param memberId 会员id
     * @return 营业额
     */
    double getTurnover(int memberId);

    /**
     * 根据购买人增加上级人员订单数量
     *
     * @param buyMemberId 购买人会员id
     */
    void addOrderNum(int buyMemberId);

    /**
     * 结算订单
     *
     * @param order
     */
    void confirm(OrderDO order);
    /**
     * 计算商品模板退款时需要退的返利金额
     *
     * @param refundDO   退款信息
     */
    void calReturnCommission(RefundDO refundDO);
}
