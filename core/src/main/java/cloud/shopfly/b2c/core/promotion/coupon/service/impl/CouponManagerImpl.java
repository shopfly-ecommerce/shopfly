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
package cloud.shopfly.b2c.core.promotion.coupon.service.impl;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Coupon business
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
@Service
public class CouponManagerImpl implements CouponManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize, Long startTime, Long endTime, String keyword) {
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from es_coupon ");

        List<String> whereList = new ArrayList();

        if (startTime != null && endTime != null) {

            // EndTime indicates the start time and endTime
            String day = DateUtil.toString(endTime, "yyyy-MM-dd");
            day += " 23:59:59";
            endTime = DateUtil.getDateline(day,"yyyy-MM-dd hh:mm:ss");


            whereList.add(" start_time BETWEEN ? and ? and end_time BETWEEN ? and ?");
            params.add(startTime);
            params.add(endTime);
            params.add(startTime);
            params.add(endTime);
        }

        if (StringUtil.notEmpty(keyword)) {
            whereList.add(" title like ? ");
            params.add("%" + keyword + "%");
        }
        sql.append(SqlUtil.sqlSplicing(whereList));
        sql.append(" order by coupon_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, CouponDO.class, params.toArray());

        return webPage;
    }

    @Override
    public List<CouponDO> getList() {
        String sql = "select * from es_coupon where start_time < ? and end_time > ?";
        List<CouponDO> couponDOList = this.daoSupport.queryForList(sql, CouponDO.class, DateUtil.getDateline(), DateUtil.getDateline());
        return couponDOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class, RuntimeException.class, Exception.class})
    public CouponDO add(CouponDO coupon) {

        this.daoSupport.insert(coupon);
        int id = this.daoSupport.getLastId("es_coupon");
        coupon.setCouponId(id);

        return coupon;
    }

    @Override
    public CouponDO edit(CouponDO coupon, Integer id) {
        this.verifyStatus(id);
        this.daoSupport.update(coupon, id);
        return coupon;
    }

    @Override
    public void delete(Integer id) {

        this.verifyStatus(id);
        this.daoSupport.delete(CouponDO.class, id);
    }

    @Override
    public CouponDO getModel(Integer id) {

        CouponDO couponDO = this.daoSupport.queryForObject(CouponDO.class, id);

        return couponDO;
    }

    @Override
    public void verifyAuth(Integer id) {
        CouponDO couponDO = this.getModel(id);
        if (couponDO == null) {
            throw new NoPermissionException("No operation or data does not exist");
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void addUsedNum(Integer couponId) {
        String sql = "update es_coupon set used_num = used_num+1 where coupon_id=?";
        this.daoSupport.execute(sql, couponId);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void addReceivedNum(Integer couponId) {
        String sql = "update es_coupon set received_num = received_num+1 where coupon_id=?";
        this.daoSupport.execute(sql, couponId);
    }

    @Override
    public Page all(int page, int pageSize) {
        Long nowTime = DateUtil.getDateline();
        List params = new ArrayList();

        StringBuffer sql = new StringBuffer("select * from es_coupon where ? >= start_time and ? < end_time");
        params.add(nowTime);
        params.add(nowTime);
        sql.append(" order by coupon_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, CouponDO.class, params.toArray());

        return webPage;
    }

    @Override
    public List<CouponDO> getByStatus(Integer status) {
        List paramList = new ArrayList();
        List<String> sqlList = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from es_coupon");

        // Get the current time
        Long currentTime = DateUtil.getDateline();

        if (status == 1) {
            sqlList.add(" end_time >= ?");
            paramList.add(currentTime);
        } else if (status == 2) {
            sqlList.add(" end_time < ?");
            paramList.add(currentTime);
        }
        sql.append(SqlUtil.sqlSplicing(sqlList));
        return this.daoSupport.queryForList(sql.toString(), CouponDO.class, paramList.toArray());
    }

    /**
     * Verify that it can be modified and deleted
     *
     * @param id
     */
    private void verifyStatus(Integer id) {
        CouponDO couponDO = this.getModel(id);
        long nowTime = DateUtil.getDateline();

        // If the current time is greater than the start time and smaller than the end time, the activity has started and cannot be modified or deleted.
        if (couponDO.getStartTime().longValue() < nowTime && couponDO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The activity has started. You cannot edit or delete the activity");
        }


    }

}
