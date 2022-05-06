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
 * 会员优惠券
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

            //添加会员优惠券表
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

            // 修改优惠券已被领取的数量
            this.couponClient.addReceivedNum(couponId);
        }
    }


    @Override
    public Page<MemberCoupon> list(MemberCouponQueryParam param) {

        //当前登录的会员
        Buyer buyer = UserContext.getBuyer();
        //当前服务器时间
        long nowTime = DateUtil.getDateline();
        //sql 参数
        List where = new ArrayList();
        //sql
        StringBuffer sql = new StringBuffer();
        sql.append("select * from es_member_coupon where member_id = ?");
        where.add(buyer.getUid());

        // 判断读取可用或者不可用优惠券 1:未使用 2：已使用，3已过期,4为不可用优惠券（已使用和已过期）
        if (param.getStatus() != null && param.getStatus().intValue() == 1) {

            // 可用优惠券读取条件 当前时间大于等于生效时间 并且 当前时间小于等于失效时间且使用状态是未使用
            sql.append(" and start_time <= ? and end_time >= ? and used_status = 0 ");
            // 并且 大于等于优惠券使用金额条件
            where.add(nowTime);
            where.add(nowTime);
            if (param.getOrderPrice() != null) {
                sql.append(" and coupon_threshold_price <= ?");
                where.add(param.getOrderPrice());
            }
        } else if (param.getStatus() != null && param.getStatus().intValue() == 2) {

            //已使用优惠券
            sql.append(" and used_status = 1");

        } else if (param.getStatus() != null && param.getStatus().intValue() == 3) {

            // 已过期优惠券读取条件 当前时间小于生效时间 或者 当前时间大于失效时间
            sql.append(" and end_time <?  and used_status = 0  ");
            where.add(nowTime);
        } else if (param.getStatus() != null && param.getStatus().intValue() == 4) {

            // 查询已使用和已过期的优惠券
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
            throw new ServiceException(MemberErrorCode.E203.code(), "优惠券已被领完");
        }
        if (limitNum != 0 && num >= limitNum) {
            throw new ServiceException(MemberErrorCode.E203.code(), "优惠券限领" + limitNum + "个");
        }

    }

    @Override
    public List<MemberCoupon> listByCheckout(Integer memberId) {
        //sql 参数
        List where = new ArrayList();
        //sql
        StringBuffer sql = new StringBuffer("select DISTINCT coupon_id,coupon_threshold_price,coupon_price," +
                "title,start_time,end_time,used_status,mc_id" +
                " from es_member_coupon where member_id = ? and  used_status = 0 and start_time < ? and end_time > ? ");
        where.add(memberId);
        where.add(DateUtil.getDateline());
        where.add(DateUtil.getDateline());

        sql.append(" order by coupon_price desc");
        //查询的所有优惠券
        List<MemberCoupon> couponList = this.daoSupport.queryForList(sql.toString(), MemberCoupon.class, where.toArray());

        return couponList;
    }


    @Override
    public MemberCouponNumVO statusNum() {

        //当前登录的会员
        Buyer buyer = UserContext.getBuyer();
        //当前服务器时间
        long nowTime = DateUtil.getDateline();

        //未使用的数量
        String unUsedSql = "select count(0) from es_member_coupon where member_id = ? " +
                "and start_time <= ? and end_time >= ? ";
        int unUsedNum = this.daoSupport.queryForInt(unUsedSql, buyer.getUid(), nowTime, nowTime);

        //已使用的数量
        String usedSql = "select count(0) from es_member_coupon where member_id = ? " +
                " and used_status = 1";
        int usedNum = this.daoSupport.queryForInt(usedSql, buyer.getUid());

        //已过期
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
