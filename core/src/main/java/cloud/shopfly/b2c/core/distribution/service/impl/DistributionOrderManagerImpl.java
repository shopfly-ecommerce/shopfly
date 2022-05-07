/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.client.trade.AfterSaleClient;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.dos.*;
import cloud.shopfly.b2c.core.distribution.model.dto.DistributionRefundDTO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.distribution.service.*;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.model.dos.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.core.distribution.service.*;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Distribution of ordersManager implementation
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 In the afternoon12:05
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

        // If the order ID is valid
        if (!StringUtil.isEmpty(orderSn)) {
            String getFxOrderDOSql = "SELECT * FROM es_distribution_order WHERE order_sn = ?";
            DistributionOrderDO distributionOrderDO = this.daoSupport.queryForObject(getFxOrderDOSql, DistributionOrderDO.class, orderSn);
            return distributionOrderDO;
        }
        return null;
    }

    @Override
    public DistributionOrderDO getModel(Integer orderId) {

        // If the order ID is valid
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

        // If Order is valid
        if (distributionOrderDO != null) {
            this.daoSupport.insert("es_distribution_order", distributionOrderDO);
        }
        return distributionOrderDO;

    }

    @Override
    public boolean calCommission(String orderSn) {

        // If its a correct number
        if (orderSn != null) {

            DistributionOrderDO model = this.getModelByOrderSn(orderSn);
            double price = model.getOrderPrice();

            // 1. Obtain the memberiD of each level
            Integer lv1MemberId = model.getMemberIdLv1();
            Integer lv2MemberId = model.getMemberIdLv2();

            calRebate(model, lv1MemberId, lv2MemberId);

            // 2. Save the frozen amount to the distributor
            if (lv1MemberId != null && lv1MemberId != 0) {
                this.distributionManager.addFrozenCommission(model.getGrade1Rebate(), lv1MemberId);
                this.distributionManager.addTotalPrice(price, model.getGrade1Rebate(), lv1MemberId);
            }

            if (lv2MemberId != null && lv2MemberId != 0) {
                this.distributionManager.addFrozenCommission(model.getGrade2Rebate(), lv2MemberId);
                this.distributionManager.addTotalPrice(price, model.getGrade2Rebate(), lv2MemberId);
            }
            // 3. Save the order
            Map where = new HashMap(16);
            where.put("id", model.getId());
            this.daoSupport.update("es_distribution_order", model, where);
            return true;
        }
        return false;
    }

    /**
     * Calculate the amount of rebate needed for template refund
     *
     * @param refundDO The refund information
     */
    private void calReturnGoodsCommission(RefundDO refundDO, DistributionOrderDO distributionOrder) {

        List<RefundGoodsDO> refundGoodsList = afterSaleClient.getRefundGoods(refundDO.getSn());

        if (CollUtil.isEmpty(refundGoodsList)) {
            logger.info("Refundable item information does not exist");
            return;
        }

        // 1. Obtain the memberiD of each level
        Integer lv1MemberId = distributionOrder.getMemberIdLv1();
        Integer lv2MemberId = distributionOrder.getMemberIdLv2();

        DistributionRefundDTO distributionRefundDTO = new DistributionRefundDTO();
        distributionRefundDTO.setMemberIdLv1(lv1MemberId);
        distributionRefundDTO.setMemberIdLv2(lv2MemberId);

        String goodsRebate = distributionOrder.getGoodsRebate();


        List<DistributionGoods> goodsList = JSONUtil.parseArray(goodsRebate).toList(DistributionGoods.class);


        // Gets a collection of goods with a rebate record
        List<DistributionGoods> rebateGoodsList = goodsList.stream()
                .filter(goods -> refundGoodsList.stream().anyMatch(refundGoods -> refundGoods.getGoodsId().equals(goods.getGoodsId())))
                .filter(goods -> Objects.nonNull(goods.getGrade1Rebate()))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(rebateGoodsList)) {
            logger.info("Rebate goods do not exist");
            return;
        }

        // Set up refund commission
        if (lv1MemberId != null && lv1MemberId != 0) {
            // Get the first level commission plus for the refund item
            double grade1Rebate = rebateGoodsList.stream()
                    .mapToDouble(goods -> goods.getGrade1Rebate())
                    .sum();
            distributionOrder.setGrade1SellbackPrice(CurrencyUtil.add(distributionOrder.getGrade1SellbackPrice(), grade1Rebate));
            distributionRefundDTO.setRefundLv1(grade1Rebate);
        }
        if (lv2MemberId != null && lv2MemberId != 0) {
            // Obtain a secondary commission plus for refunded items
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
        // Update refund commission and other information
        this.updateReturnCommission(distributionOrder, distributionRefundDTO);

    }

    /**
     * Update refund commission and other information
     *
     * @param distributionOrder     Distribution of orders
     * @param distributionRefundDTO Commission refund order
     */
    private void updateReturnCommission(DistributionOrderDO distributionOrder, DistributionRefundDTO distributionRefundDTO) {
        Map where = new HashMap(16);
        where.put("id", distributionOrder.getId());
        // 4. Save the order
        this.daoSupport.update("es_distribution_order", distributionOrder, where);

        // If id is not 0 (valid ID)
        if (distributionRefundDTO.getMemberIdLv1() != null) {
            this.distributionManager.subTotalPrice(distributionRefundDTO.getRefundMoney(), distributionRefundDTO.getRefundLv1(), distributionRefundDTO.getMemberIdLv1());
            this.daoSupport.execute("update es_distribution set can_rebate = can_rebate - ? where member_id = ?",
                    distributionRefundDTO.getRefundLv1(), distributionRefundDTO.getMemberIdLv1());
        }

        // The valid ID is valid at level 2
        if (distributionRefundDTO.getMemberIdLv2() != null) {
            this.distributionManager.subTotalPrice(distributionRefundDTO.getRefundMoney(), distributionRefundDTO.getRefundLv2(), distributionRefundDTO.getMemberIdLv2());
            this.daoSupport.execute("update es_distribution set can_rebate = can_rebate - ? where member_id = ?",
                    distributionRefundDTO.getRefundLv2(), distributionRefundDTO.getMemberIdLv2());
        }

        // Related processing of statement
        billMemberManager.returnShop(distributionOrderManager.getModelByOrderSn(distributionOrder.getOrderSn()), distributionRefundDTO);
    }

    /**
     * Calculate the rebate amount to be returned when the commodity template is refunded
     *
     * @param refundDO          The refund information
     * @param distributionOrder
     */
    private void calReturnTemplateCommission(RefundDO refundDO, DistributionOrderDO distributionOrder) {
        Double price = refundDO.getRefundPrice();
        String orderSn = refundDO.getOrderSn();
        // The refund can be 0. If 0, the amount of rebate to be returned is also 0
        if (price == 0) {
            return;
        }
        // If the order ID is valid
        if (orderSn == null) {
            return;
        }
        OrderDetailVO orderDetailVO = orderQueryManager.getModel(orderSn, null);
        // If the order amount is 0, it will not be calculated
        if (orderDetailVO.getGoodsPrice() == 0) {
            return;
        }
        // Refundable ratio = order refund amount/order amount
        Double calReturnPercentage = CurrencyUtil.div(CurrencyUtil.mul(price, 100), orderDetailVO.getNeedPayMoney());


        Double lv1ReturnPrice = 0D;
        Double lv2ReturnPrice = 0D;

        // 1. Obtain the memberiD of each level
        Integer lv1MemberId = distributionOrder.getMemberIdLv1();
        Integer lv2MemberId = distributionOrder.getMemberIdLv2();

        DistributionRefundDTO distributionRefundDTO = new DistributionRefundDTO();
        distributionRefundDTO.setMemberIdLv1(lv1MemberId);
        distributionRefundDTO.setMemberIdLv2(lv2MemberId);

        if (lv1MemberId != null && lv1MemberId != 0) {
            // Payment value, the initial amount of cash back
            lv1ReturnPrice += distributionOrder.getGrade1SellbackPrice() == null ? 0D : distributionOrder.getGrade1SellbackPrice();
            // Initial cash rebate + this cash rebate = total cash rebate
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
        // Update refund commission and other information
        this.updateReturnCommission(distributionOrder, distributionRefundDTO);
    }


    @Override
    public boolean addDistributorFreeze(String orderSn) {
        // If its a correct ID
        if (orderSn != null) {
            DistributionOrderDO distributionOrder = this.getModelByOrderSn(orderSn);

            // 1. Obtain the memberiD of each level
            Integer lv1MemberId = distributionOrder.getMemberIdLv1();
            Integer lv2MemberId = distributionOrder.getMemberIdLv2();

            // 2. Obtain the rebate amount of each level
            Double lv1Commission = distributionOrder.getGrade1Rebate();
            Double lv2Commission = distributionOrder.getGrade2Rebate();

            // 3. Add it to the frozen amount
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

        // Superior order quantity
        Integer lv1MemberId = buyDistributor.getMemberIdLv1();
        Integer lv2MemberId = buyDistributor.getMemberIdLv2();

        // If there is a superior
        if (null != lv1MemberId) {
            this.daoSupport.execute("update es_distribution set order_num = order_num+1 where member_id = ?", lv1MemberId);
        }
        // If there is a level 2
        if (lv2MemberId != null) {
            this.daoSupport.execute("update es_distribution set order_num = order_num+1 where member_id = ?", lv2MemberId);
        }
    }

    /**
     * Lets figure it out based on the pricelv1 lv2The amount of rebate
     *
     * @param distributionOrderDO do
     * @param lv1MemberId         lv1membersid
     * @param lv2MemberId         lv2membersid
     *                            MapA collection ofkey： lv1_rebate=lv1The rebate amountlv2_rebate=lv2The rebate amount
     */
    private void calRebate(DistributionOrderDO distributionOrderDO, Integer lv1MemberId, Integer lv2MemberId) {

        // Querying System Settings
        String json = settingClient.get(SettingGroup.DISTRIBUTION);
        DistributionSetting distributionSetting = JsonUtil.jsonToObject(json, DistributionSetting.class);

        // 1. Acquire distributors at all levels
        DistributionDO lv1Distributor = this.distributionManager.getDistributorByMemberId(lv1MemberId);
        DistributionDO lv2Distributor = this.distributionManager.getDistributorByMemberId(lv2MemberId);

        // If commodity mode is enabled, the calculation is based on commodities
        if (distributionSetting.getGoodsModel() == 1) {
            List<OrderItemsDO> orderItemsDOS = orderQueryManager.orderItems(distributionOrderDO.getOrderSn());
            this.goodsRebate(distributionOrderDO, orderItemsDOS, lv1Distributor, lv2Distributor);
        } else {
            this.tplRebate(distributionOrderDO, lv1Distributor, lv2Distributor);
        }

    }

    /**
     * Template cashback
     *
     * @param lv1Distributor
     * @param lv2Distributor
     */
    private void tplRebate(DistributionOrderDO distributionOrderDO, DistributionDO lv1Distributor, DistributionDO lv2Distributor) {

        Map<String, Double> map = new HashMap<String, Double>(16);

        double lv1Commission = 0;
        double lv2Commission = 0;

        // 2. Obtain template objects of distributors at all levels
        // Only if there are distributors of this level
        if (lv1Distributor != null) {
            CommissionTpl lv1CommissionTpl = this.commissionTplManager
                    .getModel(lv1Distributor.getCurrentTplId());
            double lv1CommissionRatio = lv1CommissionTpl.getGrade1();

            lv1Commission = CurrencyUtil.div(CurrencyUtil.mul(lv1CommissionRatio, distributionOrderDO.getOrderPrice()), 100);
            distributionOrderDO.setLv1Point(lv1CommissionRatio);
        }

        // Only if there are distributors of this level
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
     * Commodity cashback
     *
     * @param orderItemsDOS
     * @param lv1Distributor
     * @param lv2Distributor
     * @return
     */
    private void goodsRebate(DistributionOrderDO distributionOrderDO, List<OrderItemsDO> orderItemsDOS, DistributionDO lv1Distributor, DistributionDO lv2Distributor) {
        // Calculate the amount of cash back and record the amount of cash back for each item
        Map<Integer, Double> grade1 = new HashMap<>(16);
        Map<Integer, Double> grade2 = new HashMap<>(16);
        Map<Integer, Integer> num = new HashMap<>(16);
        List<DistributionGoods> dgs = new ArrayList<>();
        for (OrderItemsDO orderItemsDO : orderItemsDOS) {
            DistributionGoods distributionGoods = distributionGoodsManager.getModel(orderItemsDO.getGoodsId());
            // If no item cashback is set, set a default of 0 cashback for the item
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
        // Cash back on goods
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
     * Confirmation of receipt event
     *
     * @param order
     */
    @Transactional( rollbackFor = Exception.class)
    @Override
    public void confirm(OrderDO order) {

        // Calculate the rebate amount
        try {
            int buyMemberId = order.getMemberId();
            DistributionDO distributor = this.distributionManager.getDistributorByMemberId(buyMemberId);

            // Added distribution associated order
            DistributionOrderDO distributionOrderDO = new DistributionOrderDO();
            distributionOrderDO.setMemberIdLv1(distributor.getMemberIdLv1());
            distributionOrderDO.setMemberIdLv2(distributor.getMemberIdLv2());
            distributionOrderDO.setOrderId(order.getOrderId());
            distributionOrderDO.setBuyerMemberId(buyMemberId);
            distributionOrderDO.setBuyerMemberName(distributor.getMemberName());
            distributionOrderDO.setOrderSn(order.getSn());
            // Unlock the cycle
            String setting = settingClient.get(SettingGroup.DISTRIBUTION);

            DistributionSetting ds = JsonUtil.jsonToObject(setting, DistributionSetting.class);

            distributionOrderDO.setSettleCycle((ds.getCycle() * 3600 * 24) + new Long(DateUtil.getDateline()).intValue());
            distributionOrderDO.setOrderPrice(order.getNeedPayMoney());
            distributionOrderDO.setCreateTime(order.getCreateTime());
            this.distributionOrderManager.add(distributionOrderDO);
            this.logger.info("The order【" + order.getSn() + "】Pay the amount【" + order.getNeedPayMoney() + "】");
        } catch (RuntimeException e) {
            this.logger.error("Distribution withdrawal calculation is abnormal", e);
        }
        // Call to increase order quantity
        this.distributionOrderManager.addOrderNum(order.getMemberId());

        // Call the method to calculate the rebate amount
        this.distributionOrderManager.calCommission(order.getSn());
        // Calculation results
        billMemberManager.buyShop(this.distributionOrderManager.getModelByOrderSn(order.getSn()));
    }

    @Override
    public void calReturnCommission(RefundDO refundDO) {

        DistributionOrderDO distributionOrder = this.getModelByOrderSn(refundDO.getOrderSn());
        if (Objects.isNull(distributionOrder)) {
            logger.info("Distribution order information does not exist");
            return;
        }

        // Determine the type of historical distribution order. If the distribution order contains product information, the refund commission shall be based on the product refund method
        if (StringUtil.notEmpty(distributionOrder.getGoodsRebate())) {
            this.calReturnGoodsCommission(refundDO, distributionOrder);
        } else {
            this.calReturnTemplateCommission(refundDO, distributionOrder);
        }

    }

}
