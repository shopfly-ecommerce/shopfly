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
package cloud.shopfly.b2c.core.member.service.impl;


import cloud.shopfly.b2c.core.client.trade.CouponClient;
import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dto.MemberCouponQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.MemberCouponNumVO;
import cloud.shopfly.b2c.core.member.service.MemberCouponManager;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Membership coupon
 *
 * @author Snow create in 2018/5/24
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class MemberCouponManagerImpl implements MemberCouponManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private CouponClient couponClient;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void receiveBonus(Integer memberId, Integer couponId) {
        CouponDO couponDO = this.couponClient.getModel(couponId);

        if (memberId != null) {

            // Add membership coupon table
            MemberCoupon memberCoupon = new MemberCoupon();
            memberCoupon.setCouponId(couponId);
            memberCoupon.setTitle(couponDO.getTitle());
            memberCoupon.setCreateTime(DateUtil.getDateline());
            memberCoupon.setMemberId(memberId);
            memberCoupon.setStartTime(couponDO.getStartTime());
            memberCoupon.setEndTime(couponDO.getEndTime());
            memberCoupon.setCouponPrice(couponDO.getCouponPrice());
            memberCoupon.setCouponThresholdPrice(couponDO.getCouponThresholdPrice());
            memberCoupon.setUsedStatus(0);
            this.daoSupport.insert(memberCoupon);

            // Modify the number of coupons that have been claimed
            this.couponClient.addReceivedNum(couponId);
        }
    }


    @Override
    public Page<MemberCoupon> list(MemberCouponQueryParam param) {

        // Current logged-in members
        Buyer buyer = UserContext.getBuyer();
        // Current server time
        long nowTime = DateUtil.getDateline();
        // SQL parameters
        List where = new ArrayList();
        //sql
        StringBuffer sql = new StringBuffer();
        sql.append("select * from es_member_coupon where member_id = ?");
        where.add(buyer.getUid());

        // 1: unused 2: Used, 3 expired,4 is not available coupon (used and expired)
        if (param.getStatus() != null && param.getStatus().intValue() == 1) {

            // Available coupon reading conditions The current time is greater than or equal to the validity time and the current time is less than or equal to the validity time and the use status is unused
            sql.append(" and start_time <= ? and end_time >= ? and used_status = 0 ");
            // And is greater than or equal to the coupon use amount conditions
            where.add(nowTime);
            where.add(nowTime);
            if (param.getOrderPrice() != null) {
                sql.append(" and coupon_threshold_price <= ?");
                where.add(param.getOrderPrice());
            }
        } else if (param.getStatus() != null && param.getStatus().intValue() == 2) {

            // Used coupon
            sql.append(" and used_status = 1");

        } else if (param.getStatus() != null && param.getStatus().intValue() == 3) {

            // The current time of reading conditions of expired coupons is less than the validity time or longer than the validity time
            sql.append(" and end_time <?  and used_status = 0  ");
            where.add(nowTime);
        } else if (param.getStatus() != null && param.getStatus().intValue() == 4) {

            // Query used and expired coupons
            sql.append(" and ((end_time <?  and used_status = 0 ) or used_status = 1)  ");
            where.add(nowTime);
        }
        sql.append(" order by coupon_price desc");
        Page<MemberCoupon> webPage = this.daoSupport.queryForPage(sql.toString(), param.getPageNo(), param.getPageSize(), MemberCoupon.class, where.toArray());

        List<MemberCoupon> list = webPage.getData();
        if (list != null) {
            for (MemberCoupon memberCoupon : list) {
                if (memberCoupon.getEndTime() < nowTime && memberCoupon.getUsedStatus().equals(0)) {
                    memberCoupon.setUsedStatus(2);
                }
            }
        }
        webPage.setData(list);

        return webPage;
    }


    @Override
    public MemberCoupon getModel(Integer memberId, Integer mcId) {

        StringBuffer sql = new StringBuffer();
        sql.append("select * from es_member_coupon where member_id=? and mc_id=? ");
        MemberCoupon memberCoupon = this.daoSupport.queryForObject(sql.toString(), MemberCoupon.class, memberId, mcId);
        return memberCoupon;
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void usedCoupon(Integer mcId) {
        String sql = "update es_member_coupon set used_status = 1 where mc_id = ?";
        this.daoSupport.execute(sql, mcId);
    }


    @Override
    public void checkLimitNum(Integer couponId) {
        CouponDO couponDO = this.couponClient.getModel(couponId);
        Buyer buyer = UserContext.getBuyer();

        int limitNum = couponDO.getLimitNum();

        String sql = "select count(0) from es_member_coupon where member_id=? and coupon_id=?";
        int num = this.daoSupport.queryForInt(sql, buyer.getUid(), couponId);

        if (couponDO.getReceivedNum() >= couponDO.getCreateNum()) {
            throw new ServiceException(MemberErrorCode.E203.code(), "Coupons have been collected");
        }
        if (limitNum != 0 && num >= limitNum) {
            throw new ServiceException(MemberErrorCode.E203.code(), "Coupon limit" + limitNum + "a");
        }

    }

    @Override
    public List<MemberCoupon> listByCheckout(Integer memberId) {
        // SQL parameters
        List where = new ArrayList();
        //sql
        StringBuffer sql = new StringBuffer("select DISTINCT coupon_id,coupon_threshold_price,coupon_price," +
                "title,start_time,end_time,used_status,mc_id" +
                " from es_member_coupon where member_id = ? and  used_status = 0 and start_time < ? and end_time > ? ");
        where.add(memberId);
        where.add(DateUtil.getDateline());
        where.add(DateUtil.getDateline());

        sql.append(" order by coupon_price desc");
        // Query all coupons
        List<MemberCoupon> couponList = this.daoSupport.queryForList(sql.toString(), MemberCoupon.class, where.toArray());

        return couponList;
    }


    @Override
    public MemberCouponNumVO statusNum() {

        // Current logged-in members
        Buyer buyer = UserContext.getBuyer();
        // Current server time
        long nowTime = DateUtil.getDateline();

        // The number of unused
        String unUsedSql = "select count(0) from es_member_coupon where member_id = ? " +
                "and start_time <= ? and end_time >= ? ";
        int unUsedNum = this.daoSupport.queryForInt(unUsedSql, buyer.getUid(), nowTime, nowTime);

        // Quantity used
        String usedSql = "select count(0) from es_member_coupon where member_id = ? " +
                " and used_status = 1";
        int usedNum = this.daoSupport.queryForInt(usedSql, buyer.getUid());

        // expired
        String expiredSql = "select count(0) from es_member_coupon where member_id = ? " +
                "  and end_time <?  ";
        int expiredNum = this.daoSupport.queryForInt(expiredSql, buyer.getUid(), nowTime);

        MemberCouponNumVO couponNumVO = new MemberCouponNumVO();
        couponNumVO.setExpiredNum(expiredNum);
        couponNumVO.setUseNum(usedNum);
        couponNumVO.setUnUseNum(unUsedNum);

        return couponNumVO;
    }


}
