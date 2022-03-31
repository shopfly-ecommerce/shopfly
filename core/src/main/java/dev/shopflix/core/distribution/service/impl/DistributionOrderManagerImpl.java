/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.core.distribution.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import dev.shopflix.core.aftersale.model.dos.RefundDO;
import dev.shopflix.core.aftersale.model.dos.RefundGoodsDO;
import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.client.system.SettingClient;
import dev.shopflix.core.client.trade.AfterSaleClient;
import dev.shopflix.core.distribution.exception.DistributionErrorCode;
import dev.shopflix.core.distribution.exception.DistributionException;
import dev.shopflix.core.distribution.model.dos.*;
import dev.shopflix.core.distribution.model.dto.DistributionRefundDTO;
import dev.shopflix.core.distribution.model.vo.DistributionOrderVO;
import dev.shopflix.core.distribution.model.vo.DistributionSellbackOrderVO;
import dev.shopflix.core.statistics.util.DateUtil;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.distribution.service.*;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.CurrencyUtil;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分销订单Manager 实现
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午12:05
 */

@Component
public class DistributionOrderManagerImpl implements DistributionOrderManager {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private CommissionTplManager commissionTplManager;
    @Autowired
    private DistributionManager distributionManager;

    @Autowired
    private DistributionGoodsManager distributionGoodsManager;
    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private AfterSaleClient afterSaleClient;

    @Autowired
    private SettingClient settingClient;
    @Autowired
    private DistributionOrderManager distributionOrderManager;

    @Autowired
    private BillMemberManager billMemberManager;

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public DistributionOrderDO getModelByOrderSn(String orderSn) {

        // 如果订单id有效
        if (!StringUtil.isEmpty(orderSn)) {
            String getFxOrderDOSql = "SELECT * FROM es_distribution_order WHERE order_sn = ?";
            DistributionOrderDO distributionOrderDO = this.daoSupport.queryForObject(getFxOrderDOSql, DistributionOrderDO.class, orderSn);
            return distributionOrderDO;
        }
        return null;
    }

    @Override
    public DistributionOrderDO getModel(Integer orderId) {

        // 如果订单id有效
        if (orderId != null) {
            String getFxOrderDOSql = "SELECT * FROM es_distribution_order WHERE order_id = ?";
            DistributionOrderDO distributionOrderDO = this.daoSupport.queryForObject(getFxOrderDOSql, DistributionOrderDO.class, orderId);
            return distributionOrderDO;
        }
        return null;
    }

    @Override
    public DistributionOrderDO add(DistributionOrderDO distributionOrderDO) {
        distributionOrderDO.setGrade1SellbackPrice(0D);
        distributionOrderDO.setGrade2SellbackPrice(0D);

        // 如果Order有效
        if (distributionOrderDO != null) {
            this.daoSupport.insert("es_distribution_order", distributionOrderDO);
        }
        return distributionOrderDO;

    }

    @Override
    public boolean calCommission(String orderSn) {

        // 如果是一个正确的编号
        if (orderSn != null) {

            DistributionOrderDO model = this.getModelByOrderSn(orderSn);
            double price = model.getOrderPrice();

            // 1.获取各个级别的memberid
            Integer lv1MemberId = model.getMemberIdLv1();
            Integer lv2MemberId = model.getMemberIdLv2();

            calRebate(model, lv1MemberId, lv2MemberId);

            // 2.保存到分销商冻结金额
            if (lv1MemberId != null && lv1MemberId != 0) {
                this.distributionManager.addFrozenCommission(model.getGrade1Rebate(), lv1MemberId);
                this.distributionManager.addTotalPrice(price, model.getGrade1Rebate(), lv1MemberId);
            }

            if (lv2MemberId != null && lv2MemberId != 0) {
                this.distributionManager.addFrozenCommission(model.getGrade2Rebate(), lv2MemberId);
                this.distributionManager.addTotalPrice(price, model.getGrade2Rebate(), lv2MemberId);
            }
            // 3.保存订单
            Map where = new HashMap(16);
            where.put("id", model.getId());
            this.daoSupport.update("es_distribution_order", model, where);
            return true;
        }
        return false;
    }

    /**
     * 计算模板退款时需要退的返利金额
     *
     * @param refundDO 退款信息
     */
    private void calReturnGoodsCommission(RefundDO refundDO, DistributionOrderDO distributionOrder) {

        List<RefundGoodsDO> refundGoodsList = afterSaleClient.getRefundGoods(refundDO.getSn());

        if (CollUtil.isEmpty(refundGoodsList)) {
            logger.info("退款商品信息不存在");
            return;
        }

        // 1.获取各个级别的memberid
        Integer lv1MemberId = distributionOrder.getMemberIdLv1();
        Integer lv2MemberId = distributionOrder.getMemberIdLv2();

        DistributionRefundDTO distributionRefundDTO = new DistributionRefundDTO();
        distributionRefundDTO.setMemberIdLv1(lv1MemberId);
        distributionRefundDTO.setMemberIdLv2(lv2MemberId);

        String goodsRebate = distributionOrder.getGoodsRebate();


        List<DistributionGoods> goodsList = JSONUtil.parseArray(goodsRebate).toList(DistributionGoods.class);


        //获取带有返佣记录的商品集合
        List<DistributionGoods> rebateGoodsList = goodsList.stream()
                .filter(goods -> refundGoodsList.stream().anyMatch(refundGoods -> refundGoods.getGoodsId().equals(goods.getGoodsId())))
                .filter(goods -> Objects.nonNull(goods.getGrade1Rebate()))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(rebateGoodsList)) {
            logger.info("返佣商品不存在");
            return;
        }

        //设置退款佣金
        if (lv1MemberId != null && lv1MemberId != 0) {
            //获取退款商品对应的一级佣金加和
            double grade1Rebate = rebateGoodsList.stream()
                    .mapToDouble(goods -> goods.getGrade1Rebate())
                    .sum();
            distributionOrder.setGrade1SellbackPrice(CurrencyUtil.add(distributionOrder.getGrade1SellbackPrice(), grade1Rebate));
            distributionRefundDTO.setRefundLv1(grade1Rebate);
        }
        if (lv2MemberId != null && lv2MemberId != 0) {
            //获取退款商品对应的二级佣金加和
            double grade2Rebate = rebateGoodsList.stream()
                    .mapToDouble(goods -> goods.getGrade2Rebate())
                    .sum();
            distributionOrder.setGrade2SellbackPrice(CurrencyUtil.add(distributionOrder.getGrade2SellbackPrice(), grade2Rebate));
            distributionRefundDTO.setRefundLv2(grade2Rebate);

        }


        if (distributionOrder.getReturnMoney() == null) {

            distributionOrder.setReturnMoney(0D);
        }
        distributionRefundDTO.setRefundMoney(refundDO.getRefundPrice());
        distributionOrder.setReturnMoney(CurrencyUtil.add(distributionOrder.getReturnMoney(), refundDO.getRefundPrice()));
        //更新退款佣金等信息
        this.updateReturnCommission(distributionOrder, distributionRefundDTO);

    }

    /**
     * 更新退款佣金等信息
     *
     * @param distributionOrder     分销订单
     * @param distributionRefundDTO 佣金退款单
     */
    private void updateReturnCommission(DistributionOrderDO distributionOrder, DistributionRefundDTO distributionRefundDTO) {
        Map where = new HashMap(16);
        where.put("id", distributionOrder.getId());
        // 4.保存订单
        this.daoSupport.update("es_distribution_order", distributionOrder, where);

        // 如果id不为0（有效id）
        if (distributionRefundDTO.getMemberIdLv1() != null) {
            this.distributionManager.subTotalPrice(distributionRefundDTO.getRefundMoney(), distributionRefundDTO.getRefundLv1(), distributionRefundDTO.getMemberIdLv1());
            this.daoSupport.execute("update es_distribution set can_rebate = can_rebate - ? where member_id = ?",
                    distributionRefundDTO.getRefundLv1(), distributionRefundDTO.getMemberIdLv1());
        }

        // 有效id 则2级有效
        if (distributionRefundDTO.getMemberIdLv2() != null) {
            this.distributionManager.subTotalPrice(distributionRefundDTO.getRefundMoney(), distributionRefundDTO.getRefundLv2(), distributionRefundDTO.getMemberIdLv2());
            this.daoSupport.execute("update es_distribution set can_rebate = can_rebate - ? where member_id = ?",
                    distributionRefundDTO.getRefundLv2(), distributionRefundDTO.getMemberIdLv2());
        }

        //结算单相关处理
        billMemberManager.returnShop(distributionOrderManager.getModelByOrderSn(distributionOrder.getOrderSn()), distributionRefundDTO);
    }

    /**
     * 计算商品模板退款时需要退的返利金额
     *
     * @param refundDO          退款信息
     * @param distributionOrder
     */
    private void calReturnTemplateCommission(RefundDO refundDO, DistributionOrderDO distributionOrder) {
        Double price = refundDO.getRefundPrice();
        String orderSn = refundDO.getOrderSn();
        // 退款可为0 如为0 则需要退的返利金额也为0
        if (price == 0) {
            return;
        }
        // 如果订单id有效
        if (orderSn == null) {
            return;
        }
        OrderDetailVO orderDetailVO = orderQueryManager.getModel(orderSn, null);
        //订单金额为0，则不计算
        if (orderDetailVO.getGoodsPrice() == 0) {
            return;
        }
        //可以返还的比例 = 订单申请退款金额/订单金额
        Double calReturnPercentage = CurrencyUtil.div(CurrencyUtil.mul(price, 100), orderDetailVO.getNeedPayMoney());


        Double lv1ReturnPrice = 0D;
        Double lv2ReturnPrice = 0D;

        // 1.获取各个级别的memberid
        Integer lv1MemberId = distributionOrder.getMemberIdLv1();
        Integer lv2MemberId = distributionOrder.getMemberIdLv2();

        DistributionRefundDTO distributionRefundDTO = new DistributionRefundDTO();
        distributionRefundDTO.setMemberIdLv1(lv1MemberId);
        distributionRefundDTO.setMemberIdLv2(lv2MemberId);

        if (lv1MemberId != null && lv1MemberId != 0) {
            //付值，最初的返现金额
            lv1ReturnPrice += distributionOrder.getGrade1SellbackPrice() == null ? 0D : distributionOrder.getGrade1SellbackPrice();
            //最初的返现金额+这回返现金额=总返现金额
            lv1ReturnPrice +=
                    CurrencyUtil.div(
                            CurrencyUtil.mul(
                                    distributionOrder.getGrade1Rebate()
                                    , calReturnPercentage),
                            100);
            distributionOrder.setGrade1SellbackPrice(lv1ReturnPrice);


            distributionRefundDTO.setRefundLv1(distributionOrder.getGrade1Rebate() * calReturnPercentage / 100);
        }
        if (lv2MemberId != null && lv2MemberId != 0) {
            lv2ReturnPrice += distributionOrder.getGrade2SellbackPrice() == null ? 0D : distributionOrder.getGrade2SellbackPrice();
            lv2ReturnPrice +=
                    CurrencyUtil.div(
                            CurrencyUtil.mul(
                                    distributionOrder.getGrade2Rebate()
                                    , calReturnPercentage),
                            100);

            distributionOrder.setGrade2SellbackPrice(lv2ReturnPrice);
            distributionRefundDTO.setRefundLv2(distributionOrder.getGrade2Rebate() * calReturnPercentage / 100);

        }

        if (distributionOrder.getReturnMoney() == null) {

            distributionOrder.setReturnMoney(0D);
        }
        distributionRefundDTO.setRefundMoney(price);
        distributionOrder.setReturnMoney(CurrencyUtil.add(distributionOrder.getReturnMoney(), price));
        //更新退款佣金等信息
        this.updateReturnCommission(distributionOrder, distributionRefundDTO);
    }


    @Override
    public boolean addDistributorFreeze(String orderSn) {
        // 如果是一个正确的id
        if (orderSn != null) {
            DistributionOrderDO distributionOrder = this.getModelByOrderSn(orderSn);

            // 1.获取各个级别的memberid
            Integer lv1MemberId = distributionOrder.getMemberIdLv1();
            Integer lv2MemberId = distributionOrder.getMemberIdLv2();

            // 2.获取各个级别的返利金额
            Double lv1Commission = distributionOrder.getGrade1Rebate();
            Double lv2Commission = distributionOrder.getGrade2Rebate();

            // 3.增加到冻结金额中
            String sql = "UPDATE es_distribution SET frozen_price = frozen_price+? WHERE member_id=?";
            if (lv1MemberId != null && lv1MemberId != 0) {
                this.daoSupport.execute(sql, lv1Commission, lv1MemberId);
            }
            if (lv2MemberId != null && lv2MemberId != 0) {
                this.daoSupport.execute(sql, lv2Commission, lv2MemberId);
            }
            return true;
        }
        return false;
    }

    @Override
    public double getTurnover(int memberId) {

        double turnover = 0;

        String sql = "SELECT sum(order_price) actual_price FROM es_distribution_order "
                + "WHERE (member_id_lv1 = ? OR member_id_lv2 = ? OR member_id_lv3 = ?)";
        Map map = this.daoSupport.queryForMap(sql, memberId, memberId, memberId);
        turnover = Double.parseDouble(map.get("actual_price").toString());

        return turnover;
    }

    @Override
    public void addOrderNum(int buyMemberId) {
        DistributionDO buyDistributor = this.distributionManager.getDistributorByMemberId(buyMemberId);

        // 上级订单数量
        Integer lv1MemberId = buyDistributor.getMemberIdLv1();
        Integer lv2MemberId = buyDistributor.getMemberIdLv2();

        // 如果存在上级
        if (null != lv1MemberId) {
            this.daoSupport.execute("update es_distribution set order_num = order_num+1 where member_id = ?", lv1MemberId);
        }
        // 如果存在2级
        if (lv2MemberId != null) {
            this.daoSupport.execute("update es_distribution set order_num = order_num+1 where member_id = ?", lv2MemberId);
        }
    }

    /**
     * 根据价格 算出lv1 lv2的返利金额
     *
     * @param distributionOrderDO do
     * @param lv1MemberId         lv1会员id
     * @param lv2MemberId         lv2会员id
     *                            Map集合 key： lv1_rebate=lv1返利金额 lv2_rebate=lv2返利金额
     */
    private void calRebate(DistributionOrderDO distributionOrderDO, Integer lv1MemberId, Integer lv2MemberId) {

        //查询系统设置
        String json = settingClient.get(SettingGroup.DISTRIBUTION);
        DistributionSetting distributionSetting = JsonUtil.jsonToObject(json, DistributionSetting.class);

        // 1.获取各个级别的分销商
        DistributionDO lv1Distributor = this.distributionManager.getDistributorByMemberId(lv1MemberId);
        DistributionDO lv2Distributor = this.distributionManager.getDistributorByMemberId(lv2MemberId);

        //如果商品模式开启，则优先按照商品进行计算
        if (distributionSetting.getGoodsModel() == 1) {
            List<OrderItemsDO> orderItemsDOS = orderQueryManager.orderItems(distributionOrderDO.getOrderSn());
            this.goodsRebate(distributionOrderDO, orderItemsDOS, lv1Distributor, lv2Distributor);
        } else {
            this.tplRebate(distributionOrderDO, lv1Distributor, lv2Distributor);
        }

    }

    /**
     * 模版返现
     *
     * @param lv1Distributor
     * @param lv2Distributor
     */
    private void tplRebate(DistributionOrderDO distributionOrderDO, DistributionDO lv1Distributor, DistributionDO lv2Distributor) {

        Map<String, Double> map = new HashMap<String, Double>(16);

        double lv1Commission = 0;
        double lv2Commission = 0;

        // 2.获取各个级别分销商的模板对象
        // 如果有这个级别的分销商才计算
        if (lv1Distributor != null) {
            CommissionTpl lv1CommissionTpl = this.commissionTplManager
                    .getModel(lv1Distributor.getCurrentTplId());
            double lv1CommissionRatio = lv1CommissionTpl.getGrade1();

            lv1Commission = CurrencyUtil.div(CurrencyUtil.mul(lv1CommissionRatio, distributionOrderDO.getOrderPrice()), 100);
            distributionOrderDO.setLv1Point(lv1CommissionRatio);
        }

        // 如果有这个级别的分销商才计算
        if (lv2Distributor != null) {

            CommissionTpl lv2CommissionTpl = this.commissionTplManager
                    .getModel(lv2Distributor.getCurrentTplId());
            double lv2CommissionRatio = lv2CommissionTpl.getGrade2();
            lv2Commission = CurrencyUtil.div(CurrencyUtil.mul(lv2CommissionRatio, distributionOrderDO.getOrderPrice()), 100);
            distributionOrderDO.setLv2Point(lv2CommissionRatio);
        }
        distributionOrderDO.setGrade1Rebate(lv1Commission);
        distributionOrderDO.setGrade2Rebate(lv2Commission);
    }


    /**
     * 商品返现
     *
     * @param orderItemsDOS
     * @param lv1Distributor
     * @param lv2Distributor
     * @return
     */
    private void goodsRebate(DistributionOrderDO distributionOrderDO, List<OrderItemsDO> orderItemsDOS, DistributionDO lv1Distributor, DistributionDO lv2Distributor) {
        //计算出商品返现的金额，并且记录商品返现单件返现金额
        Map<Integer, Double> grade1 = new HashMap<>(16);
        Map<Integer, Double> grade2 = new HashMap<>(16);
        Map<Integer, Integer> num = new HashMap<>(16);
        List<DistributionGoods> dgs = new ArrayList<>();
        for (OrderItemsDO orderItemsDO : orderItemsDOS) {
            DistributionGoods distributionGoods = distributionGoodsManager.getModel(orderItemsDO.getGoodsId());
            //如果没有设置商品返现，则设置一个默认0返现的商品
            if (distributionGoods == null) {
                distributionGoods = new DistributionGoods();
                distributionGoods.setGoodsId(orderItemsDO.getGoodsId());
                distributionGoods.setGrade1Rebate(0);
                distributionGoods.setGrade2Rebate(0);
                distributionGoods.setId(0);
            }
            dgs.add(distributionGoods);
            if (distributionGoods != null) {
                grade1.put(orderItemsDO.getProductId(), distributionGoods.getGrade1Rebate());
                grade2.put(orderItemsDO.getProductId(), distributionGoods.getGrade2Rebate());
                num.put(orderItemsDO.getProductId(), orderItemsDO.getNum());
            }
        }
        //根据商品返现
        double lv1Commission = 0;
        double lv2Commission = 0;
        for (Integer productId : grade1.keySet()) {
            if (lv1Distributor != null) {
                lv1Commission = CurrencyUtil.add(lv1Commission, CurrencyUtil.mul(grade1.get(productId), num.get(productId)));
            }
            if (lv2Distributor != null) {
                lv2Commission = CurrencyUtil.add(lv2Commission, CurrencyUtil.mul(grade2.get(productId), num.get(productId)));
            }
        }
        Map<String, Double> result = new HashMap<>(16);
        result.put("lv1_rebate", lv1Commission);
        result.put("lv2_rebate", lv2Commission);
        distributionOrderDO.setGrade1Rebate(lv1Commission);
        distributionOrderDO.setGrade2Rebate(lv2Commission);
        Gson gson = new Gson();
        distributionOrderDO.setGoodsRebate(gson.toJson(dgs));
    }


    @Override
    public Page<DistributionOrderVO> pageDistributionOrder(Integer pageSize, Integer page, Integer memberId, Integer billId) {

        if ((memberId == null) || (billId == null)) {
            throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
        }

        Page<DistributionOrderDO> data = this.daoSupport.queryForPage(
                "select * from es_distribution_order o "
                        + " where member_id_lv1 = ? or member_id_lv2 = ? and bill_id = (select total_id from es_bill_member where id = ? )",
                page, pageSize, DistributionOrderDO.class, memberId, memberId, billId);

        List<DistributionOrderVO> list = new ArrayList<>();
        for (DistributionOrderDO ddo : data.getData()) {
            list.add(new DistributionOrderVO(ddo, memberId));
        }

        Page<DistributionOrderVO> result = new Page<>(data.getPageNo(), data.getDataTotal(), data.getPageSize(), list);
        return result;
    }

    @Override
    public Page<DistributionOrderVO> pageDistributionTotalBillOrder(Integer pageSize, Integer page, Integer memberId, Integer billId) {

        if ((memberId == null) || (billId == null)) {
            throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
        }

        Page<DistributionOrderDO> data = this.daoSupport.queryForPage(
                "select * from es_distribution_order o "
                        + " where member_id_lv1 = ? or member_id_lv2 = ? and bill_id = (select total_id from es_bill_member where id = ? ) ",
                page, pageSize, DistributionOrderDO.class, memberId, memberId, billId);

        List<DistributionOrderVO> list = new ArrayList<>();
        for (DistributionOrderDO ddo : data.getData()) {
            list.add(new DistributionOrderVO(ddo, memberId));
        }

        Page<DistributionOrderVO> result = new Page<>(data.getPageNo(), data.getDataTotal(), data.getPageSize(), list);
        return result;
    }


    @Override
    public Page<DistributionSellbackOrderVO> pageSellBackOrder(Integer pageSize, Integer page, Integer memberId, Integer billId) {

        if ((memberId == null) || (billId == null)) {
            throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
        }
        Page<DistributionOrderDO> data = this.daoSupport.queryForPage(
                "select * from es_distribution_order o "
                        + " where (member_id_lv1 = ? or member_id_lv2 = ? )and bill_id = (select total_id from es_bill_member where id = ? ) and is_return = 1 ",
                page, pageSize, DistributionOrderDO.class, memberId, memberId, billId);

        List<DistributionSellbackOrderVO> list = new ArrayList<>();
        for (DistributionOrderDO ddo : data.getData()) {
            list.add(new DistributionSellbackOrderVO(ddo, memberId));
        }

        Page<DistributionSellbackOrderVO> result = new Page<>(data.getPageNo(), data.getDataTotal(), data.getPageSize(), list);
        return result;

    }


    /**
     * 确认收款事件
     *
     * @param order
     */
    @Transactional(value = "distributionTransactionManager", rollbackFor = Exception.class)
    @Override
    public void confirm(OrderDO order) {

        //计算返利金额
        try {
            int buyMemberId = order.getMemberId();
            DistributionDO distributor = this.distributionManager.getDistributorByMemberId(buyMemberId);

            // 新增分销关联订单
            DistributionOrderDO distributionOrderDO = new DistributionOrderDO();
            distributionOrderDO.setMemberIdLv1(distributor.getMemberIdLv1());
            distributionOrderDO.setMemberIdLv2(distributor.getMemberIdLv2());
            distributionOrderDO.setOrderId(order.getOrderId());
            distributionOrderDO.setBuyerMemberId(buyMemberId);
            distributionOrderDO.setBuyerMemberName(distributor.getMemberName());
            distributionOrderDO.setOrderSn(order.getSn());
            // 解锁周期
            String setting = settingClient.get(SettingGroup.DISTRIBUTION);

            DistributionSetting ds = JsonUtil.jsonToObject(setting, DistributionSetting.class);

            distributionOrderDO.setSettleCycle((ds.getCycle() * 3600 * 24) + new Long(DateUtil.getDateline()).intValue());
            distributionOrderDO.setOrderPrice(order.getNeedPayMoney());
            distributionOrderDO.setCreateTime(order.getCreateTime());
            this.distributionOrderManager.add(distributionOrderDO);
            this.logger.info("订单【" + order.getSn() + "】支付金额【" + order.getNeedPayMoney() + "】");
        } catch (RuntimeException e) {
            this.logger.error("分销提现计算异常", e);
        }
        // 调用增加订单数量
        this.distributionOrderManager.addOrderNum(order.getMemberId());

        // 调用计算返利金额方法
        this.distributionOrderManager.calCommission(order.getSn());
        //计算业绩
        billMemberManager.buyShop(this.distributionOrderManager.getModelByOrderSn(order.getSn()));
    }

    @Override
    public void calReturnCommission(RefundDO refundDO) {

        DistributionOrderDO distributionOrder = this.getModelByOrderSn(refundDO.getOrderSn());
        if (Objects.isNull(distributionOrder)) {
            logger.info("分销订单信息不存在");
            return;
        }

        //判断历史分销订单类型  分销订单包含商品信息则该订单应该按照商品退款方式进行退佣金
        if (StringUtil.notEmpty(distributionOrder.getGoodsRebate())) {
            this.calReturnGoodsCommission(refundDO, distributionOrder);
        } else {
            this.calReturnTemplateCommission(refundDO, distributionOrder);
        }

    }

}