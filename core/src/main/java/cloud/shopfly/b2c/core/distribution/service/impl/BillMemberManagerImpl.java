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

import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.distribution.model.dos.BillMemberDO;
import cloud.shopfly.b2c.core.distribution.model.dos.BillTotalDO;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import cloud.shopfly.b2c.core.distribution.model.dto.DistributionRefundDTO;
import cloud.shopfly.b2c.core.distribution.model.vo.BillMemberVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionVO;
import cloud.shopfly.b2c.core.distribution.service.BillMemberManager;
import cloud.shopfly.b2c.core.distribution.service.BillTotalManager;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用户结算单 实现
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午8:52
 */

@SuppressWarnings("ALL")
@Service("billMemberManager")
public class BillMemberManagerImpl implements BillMemberManager {

    @Autowired
    private DistributionManager distributionManager;

    @Autowired
    private BillTotalManager billTotalManager;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public BillMemberDO getBillMember(Integer billId) {
        return (BillMemberDO) this.daoSupport.queryForObject(
                "select * from es_bill_member where id = ?",
                BillMemberDO.class, billId);
    }

    @Override
    public BillMemberDO add(BillMemberDO billMember) {

        daoSupport.insert("es_bill_member", billMember);
        return billMember;
    }

    @Override
    public Page<DistributionOrderVO> listOrder(Integer page, Integer pageSize, Integer totalId, Integer memberId) {
        Page<DistributionOrderDO> dos = this.daoSupport.queryForPage(
                "select * from  es_distribution_order where bill_id = ? and member_id = ?",
                page, pageSize, DistributionOrderDO.class, totalId, memberId);

        List<DistributionOrderVO> data = new ArrayList<>();
        for (DistributionOrderDO fxdo : dos.getData()) {
            data.add(new DistributionOrderVO(fxdo, memberId));
        }
        Page<DistributionOrderVO> result = new Page<>();
        result.setData(data);
        result.setDataTotal(dos.getDataTotal());
        result.setPageNo(dos.getPageNo());
        result.setPageSize(dos.getPageSize());
        return result;
    }

    @Override
    public Page<DistributionSellbackOrderVO> listSellback(Integer page, Integer pagesize, Integer totalId, Integer memberId) {
        Page<DistributionOrderDO> dos = this.daoSupport
                .queryForPage(
                        "select * from es_distribution_order fo on "
                                + "fo.buy_member_id  where total_id = ? and member_id = ?",
                        page, pagesize, totalId, memberId);

        List<DistributionSellbackOrderVO> data = new ArrayList<>();
        for (DistributionOrderDO fxdo : dos.getData()) {
            data.add(new DistributionSellbackOrderVO(fxdo, memberId));
        }
        Page<DistributionSellbackOrderVO> result = new Page<>();
        result.setData(data);
        result.setDataTotal(dos.getDataTotal());
        result.setPageNo(dos.getPageNo());
        result.setPageSize(dos.getPageSize());
        return result;
    }

    @Override
    public Page<BillMemberVO> page(Integer page, Integer pageSize, Integer totalid, String uname) {


        Page<BillMemberDO> data = null;
        if (StringUtil.isEmpty(uname)) {
            data = this.daoSupport.queryForPage(
                    "select * from es_bill_member b where total_id = ?", page,
                    pageSize, BillMemberDO.class, totalid);
        } else {
            data = this.daoSupport
                    .queryForPage(
                            "select * from es_bill_member b where total_id = ? and b.member_name like(?) ",
                            page, pageSize, BillMemberDO.class, totalid, "%" + uname + "%");
        }

        List<BillMemberVO> vos = new ArrayList<>();
        for (BillMemberDO bdo : data.getData()) {
            vos.add(new BillMemberVO(bdo));
        }

        Page<BillMemberVO> result = new Page<>(data.getPageNo(), data.getDataTotal(), data.getPageSize(), vos);
        return result;
    }


    @Override
    public BillMemberVO getCurrentBillMember(Integer memberId, Integer billId) {
        Long[] time = DateUtil.getCurrentMonth();
        BillTotalDO billTotal = billTotalManager.getTotalByStart(time[0]);
        // 如果没有创建 总结算单

        if (null == billTotal) {
            billTotal = this.createTotal();
        }
        BillMemberDO bm = this.getBillByStart(time[0], memberId, billId);
        // 如果没有创建 总结算单
        if (null == bm) {
            bm = this.createBill(memberId,
                    billTotal.getId());
        }
        return new BillMemberVO(bm);
    }

    @Override
    public void buyShop(DistributionOrderDO order) {

        Long[] time = DateUtil.getCurrentMonth();
        BillTotalDO billTotal = billTotalManager.getTotalByStart(time[0]);
        // 如果没有创建 总结算单
        if (null == billTotal) {
            billTotal = this.createTotal();
        }
        // 修改总结算单
        this.daoSupport.execute("update es_bill_total set  order_count = order_count + 1 , order_money = order_money + ?  where id = ? ",
                order.getOrderPrice(), billTotal.getId());
        this.daoSupport.execute("update es_distribution_order set bill_id = ? where order_id = ?", billTotal.getId(), order.getOrderId());

        // 如果没有一级运营商
        if (null != order.getMemberIdLv1() && order.getMemberIdLv1() != 0) {
            BillMemberDO billMemberLv1 = this.getBillByStart(time[0],
                    order.getMemberIdLv1());
            //如果为空
            if (null == billMemberLv1) {
                billMemberLv1 = this.createBill(order.getMemberIdLv1(),
                        billTotal.getId());

            }
            // 修改分销商结算单
            this.daoSupport
                    .execute(
                            "update es_bill_member set push_money = push_money + ? , final_money = final_money + ? , order_count = order_count + 1 , order_money = order_money + ? where id = ?",
                            order.getGrade1Rebate(), order.getGrade1Rebate(),
                            order.getOrderPrice(), billMemberLv1.getId());
            // 修改总结算单
            this.daoSupport
                    .execute(
                            "update es_bill_total set push_money = push_money + ? ,final_money = final_money + ? where id = ? ",
                            order.getGrade1Rebate(), order.getGrade1Rebate(),
                            billTotal.getId());

        }
        // 如果没有二级运营商
        if (null != order.getMemberIdLv2() && order.getMemberIdLv2() != 0) {
            BillMemberDO billMemberLv2 = this.getBillByStart(time[0],
                    order.getMemberIdLv2());
            //如果为空
            if (null == billMemberLv2) {
                billMemberLv2 = this.createBill(order.getMemberIdLv2(),
                        billTotal.getId());
            }

            this.daoSupport
                    .execute(
                            "update es_bill_member set push_money = push_money + ? , final_money = final_money + ? , order_count = order_count + 1 , order_money = order_money + ?   where id = ?",
                            order.getGrade2Rebate(), order.getGrade2Rebate(),
                            order.getOrderPrice(), billMemberLv2.getId());
            // 修改总结算单
            this.daoSupport
                    .execute(
                            "update es_bill_total set push_money = push_money + ? , final_money = final_money + ? where id = ? ",
                            order.getGrade2Rebate(), order.getGrade2Rebate(),
                            billTotal.getId());
        }

    }

    @Override
    public void returnShop(DistributionOrderDO order, DistributionRefundDTO distributionRefundDTO) {
        //修改 is_return
        this.daoSupport.execute("update es_distribution_order set is_return = 1 where order_id = ?", order.getOrderId());
        Long[] time = DateUtil.getCurrentMonth();
        BillTotalDO billTotal = billTotalManager.getTotalByStart(time[0]);
        // 如果没有创建 总结算单
        if (null == billTotal) {
            billTotal = this.createTotal();
        }
        // 修改总结算单
        this.daoSupport
                .execute(
                        "update es_bill_total set return_order_count = return_order_count + 1 , return_order_money = return_order_money + ?  where id = ? ",
                        distributionRefundDTO.getRefundMoney(), billTotal.getId());


        // 如果没有一级运营商
        if (null != order.getMemberIdLv1() && 0 != order.getMemberIdLv1()) {
            BillMemberDO billMemberLv1 = this.getBillByStart(time[0],
                    order.getMemberIdLv1());
            //如果为空
            if (null == billMemberLv1) {
                billMemberLv1 = this.createBill(order.getMemberIdLv1(),
                        billTotal.getId());
            }
            // 修改分销商结算单
            this.daoSupport
                    .execute(
                            "update es_bill_member set return_push_money = return_push_money + ? , final_money = final_money - ? , return_order_count = return_order_count + 1 , return_order_money = return_order_money + ? where id = ?  ",
                            distributionRefundDTO.getRefundLv1() == null ? 0 : distributionRefundDTO.getRefundLv1(),
                            distributionRefundDTO.getRefundLv1() == null ? 0 : distributionRefundDTO.getRefundLv1(),
                            distributionRefundDTO.getRefundMoney(), billMemberLv1.getId());

            // 修改总结算单
            this.daoSupport
                    .execute(
                            "update es_bill_total set return_push_money = return_push_money + ? ,final_money = final_money - ?,return_order_count=return_order_count+1 where id = ? ",
                            distributionRefundDTO.getRefundLv1() == null ? 0 : distributionRefundDTO.getRefundLv1(),
                            distributionRefundDTO.getRefundLv1() == null ? 0 : distributionRefundDTO.getRefundLv1(),
                            billTotal.getId());

        }
        // 如果没有二级运营商
        if (null != order.getMemberIdLv2() && 0 != order.getMemberIdLv2()) {
            BillMemberDO billMemberLv2 = this.getBillByStart(time[0],
                    order.getMemberIdLv2());
            if (null == billMemberLv2) {
                billMemberLv2 = this.createBill(order.getMemberIdLv2(),
                        billTotal.getId());
            }
            // 修改分销商结算单
            this.daoSupport
                    .execute(
                            "update es_bill_member set return_push_money = return_push_money + ? , final_money = final_money - ? , return_order_count = return_order_count + 1 , return_order_money = return_order_money + ? where id = ? ",
                            distributionRefundDTO.getRefundLv2() == null ? 0 : distributionRefundDTO.getRefundLv2(),
                            distributionRefundDTO.getRefundLv2() == null ? 0 : distributionRefundDTO.getRefundLv2(),
                            distributionRefundDTO.getRefundMoney(), billMemberLv2.getId());
            // 修改总结算单
            this.daoSupport
                    .execute(
                            "update es_bill_total set return_push_money = return_push_money + ? ,final_money = final_money - ? where id = ? ",
                            distributionRefundDTO.getRefundLv2() == null ? 0 : distributionRefundDTO.getRefundLv2(),
                            distributionRefundDTO.getRefundLv2() == null ? 0 : distributionRefundDTO.getRefundLv2(),
                            billTotal.getId());

        }

    }

    /**
     * 创建分销商结算单
     *
     * @return
     */
    private BillMemberDO createBill(Integer memberId, Integer totalId) {
        Long[] time = DateUtil.getCurrentMonth();
        BillMemberDO billMember = new BillMemberDO();
        billMember.setMemberId(memberId);
        billMember.setStartTime(time[0]);
        billMember.setEndTime(time[1]);
        billMember.setOrderCount(0);
        billMember.setFinalMoney(0D);
        billMember.setOrderMoney(0d);
        billMember.setPushMoney(0d);
        billMember.setReturnPushMoney(0d);
        billMember.setReturnOrderMoney(0d);
        billMember.setReturnOrderCount(0);
        billMember.setSn(this.createSn());
        billMember.setTotalId(totalId);
        billMember.setMemberName(memberClient.getModel(memberId).getUname());
        this.add(billMember);

        return (BillMemberDO) this.daoSupport.queryForObject("select * from es_bill_member where total_id = ? and member_id = ?", BillMemberDO.class, totalId, memberId);

    }

    /**
     * 获取 分销商结算单
     *
     * @param startTime
     * @param memberId
     * @param billId
     * @return
     */
    public BillMemberDO getBillByStart(Long startTime, Integer memberId) {
        String sql = "select * from es_bill_member where start_time = ? and member_id = ?";
        return (BillMemberDO) this.daoSupport.queryForObject(sql,
                BillMemberDO.class, startTime, memberId);
    }

    /**
     * 获取 分销商结算单
     *
     * @param startTime
     * @param memberId
     * @param billId
     * @return
     */
    public BillMemberDO getBillByStart(Long startTime, Integer memberId, Integer billId) {
        String sql = "select * from es_bill_member where start_time = ? and member_id = ?";
        if (billId != null && billId != 0) {
            sql = "select * from es_bill_member where  id = ?";
            return (BillMemberDO) this.daoSupport.queryForObject(sql,
                    BillMemberDO.class, billId);

        }
        return (BillMemberDO) this.daoSupport.queryForObject(sql,
                BillMemberDO.class, startTime, memberId);
    }

    /**
     * 创建总结算单
     */
    private BillTotalDO createTotal() {
        Long[] time = DateUtil.getCurrentMonth();
        BillTotalDO billTotal = new BillTotalDO();
        billTotal.setSn(this.createSn());
        billTotal.setFinalMoney(0D);
        billTotal.setEndTime(time[1]);
        billTotal.setStartTime(time[0]);
        billTotal.setOrderCount(0);
        billTotal.setOrderMoney(0D);
        billTotal.setPushMoney(0D);
        billTotal.setReturnOrderCount(0);
        billTotal.setReturnOrderMoney(0D);
        billTotal.setReturnPushMoney(0D);
        billTotal.setFinalMoney(0D);
        billTotalManager.add(billTotal);
        return (BillTotalDO) this.daoSupport.queryForObject("select * from es_bill_total where start_time = ? ", BillTotalDO.class, time[0]);
    }

    /**
     * 创建结算单号（日期+两位随机数）
     */
    public String createSn() {
        Random random = new Random();

        int result = random.nextInt(10);

        String sn = DateUtil.getDateline() + "" + result;
        return sn;
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public int getRandom() {
        Random random = new Random();
        int num = Math.abs(random.nextInt()) % 100;
        //如果num小于10
        if (num < 10) {
            num = getRandom();
        }
        return num;
    }


    /**
     * 获取某个会员的下线业绩
     *
     * @param memberId
     * @return
     */
    @Override
    public List<BillMemberVO> allDown(Integer memberId, Integer billId) {
        //获取所有下线的分销业绩
        String sql = "select * from es_bill_member where member_id in " +
                "(select member_id from es_distribution where member_id_lv1 =? or member_id_lv2 = ?)" +
                "and total_id = (select total_id from es_bill_member where id = ?)";
        List<BillMemberDO> billMemberDOS = this.daoSupport.queryForList(sql, BillMemberDO.class, memberId, memberId, billId);

        //获取下级 分销商集合
        List<DistributionVO> distributionVOS = this.distributionManager.getLowerDistributorTree(memberId);

        /*
         * 返回所有分销商集合  及分销商订单
         * 下级分销商没有下单,则返回分销商信息   分销业绩默认为0
         * add by liuyulei 2019-08-01
         */
        distributionVOS = this.convertDistribution(distributionVOS,billMemberDOS);

        List<BillMemberVO> result = new ArrayList<>();

        result = convertBillMember(distributionVOS,result);

        return result;
    }

    /**
     * 写入第二层数据
     *
     * @param dvo
     * @param billMemberDOS
     * @param bvo
     */
    private void setChilden(DistributionVO dvo, List<BillMemberDO> billMemberDOS, BillMemberVO bvo) {
        if (dvo.getItem() == null) {
            return;
        }
        for (DistributionVO ditem : dvo.getItem()) {
            for (BillMemberDO bdo : billMemberDOS) {
                if (ditem.getId().equals(bdo.getMemberId())) {
                    List<BillMemberVO> bitems = bvo.getItem();
                    if (bitems == null) {
                        bitems = new ArrayList<>();
                    }
                    bitems.add(new BillMemberVO(bdo));
                    bvo.setItem(bitems);
                }
            }
        }
    }

    @Override
    public BillMemberDO getBillByTotalSn(String totalSn, Integer memberId) {
        BillMemberDO billmember = (BillMemberDO) this.daoSupport.queryForObject("select * from es_bill_member where total_id = (select total_id from es_bill_total where sn = ?) and member_id = ?", BillMemberDO.class, totalSn, memberId);
        return billmember;
    }

    @Override
    public BillMemberDO getHistoryBillMember(Integer memberId, String sn) {
        return (BillMemberDO) this.daoSupport.queryForObject("select * from es_bill_member where member_id = ? and sn = ?", BillMemberDO.class, memberId, sn);
    }

    @Override
    public Page<BillMemberVO> getAllByMemberId(Integer memberId, Integer pageNo, Integer pageSize) {
        return this.daoSupport.queryForPage("select * from es_bill_member where member_id = ?", pageNo, pageSize, BillMemberVO.class, memberId);
    }

    @Override
    public Page<BillMemberVO> billMemberPage(Integer pageNo, Integer pageSize, Integer memberId) {

        return this.daoSupport.queryForPage("select * from es_bill_member where member_id = ?", pageNo, pageSize, BillMemberVO.class, memberId);

    }

    /**
     * 整合分销商集合与分销业绩集合
     * @param distributionVOS
     * @param billMemberDOS
     * @return
     */
    private List<DistributionVO> convertDistribution(List<DistributionVO> distributionVOS,List<BillMemberDO> billMemberDOS){

        for (DistributionVO distributionVO:distributionVOS) {
            if(distributionVO.getItem() != null && !distributionVO.getItem().isEmpty()){
                convertDistribution(distributionVO.getItem(),billMemberDOS);
            }

            for (BillMemberDO billMemberDO:billMemberDOS) {
                if (distributionVO.getId().equals(billMemberDO.getMemberId())) {
                    distributionVO.setBillMemberVO(new BillMemberVO(billMemberDO));
                }else{
                    distributionVO.setBillMemberVO(new BillMemberVO());
                }
            }
        }

        return distributionVOS;
    }

    /**
     * 递归遍历所有分销商的业绩列表
     * @param distributionVOS
     * @param result
     * @return
     */
    private List<BillMemberVO> convertBillMember(List<DistributionVO> distributionVOS,List<BillMemberVO> result) {

        for (DistributionVO dos: distributionVOS) {

            BillMemberVO billMemberVO = dos.getBillMemberVO();
            if(billMemberVO == null ){
                billMemberVO = new BillMemberVO();

            }
            billMemberVO.setMemberName(dos.getName());
            billMemberVO.setItem(new ArrayList<>());
            if(dos.getItem() != null && !dos.getItem().isEmpty()){
                billMemberVO.setItem(convertBillMember(dos.getItem(),billMemberVO.getItem()));
            }
            result.add(billMemberVO);
        }

        return result;
    }


}
