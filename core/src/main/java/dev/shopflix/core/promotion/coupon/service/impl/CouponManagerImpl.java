/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.coupon.service.impl;

import dev.shopflix.core.promotion.PromotionErrorCode;
import dev.shopflix.core.promotion.coupon.model.dos.CouponDO;
import dev.shopflix.core.promotion.coupon.service.CouponManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 优惠券业务类
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

            //endTime前端传过来的是开始时间2019-08-08 00:00:00  取日期拼接结束时间
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
            throw new NoPermissionException("无权操作或者数据不存在");
        }
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void addUsedNum(Integer couponId) {
        String sql = "update es_coupon set used_num = used_num+1 where coupon_id=?";
        this.daoSupport.execute(sql, couponId);
    }


    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
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

        //获取当前时间
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
     * 验证是否可修改和删除
     *
     * @param id
     */
    private void verifyStatus(Integer id) {
        CouponDO couponDO = this.getModel(id);
        long nowTime = DateUtil.getDateline();

        //如果当前时间大于起始时间，小于终止时间，标识活动已经开始了，不可修改和删除。
        if (couponDO.getStartTime().longValue() < nowTime && couponDO.getEndTime().longValue() > nowTime) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动已经开始，不能进行编辑删除操作");
        }


    }

}
