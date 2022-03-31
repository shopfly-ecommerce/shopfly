/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.trade.order.model.dos.PayLog;
import dev.shopflix.core.trade.order.model.dto.PayLogQueryParam;
import dev.shopflix.core.trade.order.service.PayLogManager;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 收款单业务类
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-18 10:39:51
 */
@Service
public class PayLogManagerImpl implements PayLogManager {

    @Autowired

    private DaoSupport daoSupport;

    /**
     * 日志记录
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page list(PayLogQueryParam queryParam) {

        StringBuffer sql = new StringBuffer("select * from es_pay_log ");
        List<String> sqlList = new ArrayList<>();
        List paramList = new ArrayList();

        //付款方式
        if (!StringUtil.isEmpty(queryParam.getPaymentType())) {
            sqlList.add(" pay_way = ? ");
            paramList.add(queryParam.getPaymentType());
        }

        //支付状态
        if (!StringUtil.isEmpty(queryParam.getPayStatus())) {
            sqlList.add(" pay_status = ? ");
            paramList.add(queryParam.getPayStatus());
        }

        if (queryParam.getStartTime() != null && queryParam.getEndTime() != null) {
            sqlList.add(" pay_time BETWEEN ? and ? ");
            paramList.add(queryParam.getStartTime());
            paramList.add(queryParam.getEndTime());
        }

        if (!StringUtil.isEmpty(queryParam.getMemberName())) {
            sqlList.add(" pay_member_name = ?");
            paramList.add(queryParam.getMemberName());
        }

        if (!StringUtil.isEmpty(queryParam.getOrderSn())) {
            sqlList.add(" order_sn like ?");
            paramList.add("%" + queryParam.getOrderSn() + "%");
        }

        if (!StringUtil.isEmpty(queryParam.getPayWay())) {
            sqlList.add(" pay_type like ?");
            String payWay = queryParam.getPayWay();
            String value = "";
            if ("alipay".equals(payWay)) {
                value = "%支付宝%";
            } else if ("wechat".equals(payWay)) {
                value = "%微信%";
            }
            paramList.add(value);
        }

        sql.append(SqlUtil.sqlSplicing(sqlList));
        sql.append(" order by pay_log_id desc");

        Page webPage = this.daoSupport.queryForPage(sql.toString(), queryParam.getPageNo(), queryParam.getPageSize(), PayLog.class, paramList.toArray());
        return webPage;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PayLog add(PayLog payLog) {
        this.daoSupport.insert(payLog);
        return payLog;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PayLog edit(PayLog payLog, Integer id) {
        this.daoSupport.update(payLog, id);
        return payLog;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(PayLog.class, id);
    }

    @Override
    public PayLog getModel(Integer id) {
        return this.daoSupport.queryForObject(PayLog.class, id);
    }


    @Override
    public PayLog getModel(String orderSn) {
        String sql = "select * from es_pay_log where order_sn=?";
        PayLog payLog = this.daoSupport.queryForObject(sql, PayLog.class, orderSn);
        return payLog;
    }


    @Override
    public List<PayLog> exportExcel(PayLogQueryParam queryParam) {

        StringBuffer sql = new StringBuffer("select * from es_pay_log ");
        List<String> sqlList = new ArrayList<>();
        List paramList = new ArrayList();

        if (queryParam.getStartTime() != null && queryParam.getEndTime() != null) {
            sqlList.add(" pay_time BETWEEN ? and ? ");
            paramList.add(queryParam.getStartTime());
            paramList.add(queryParam.getEndTime());
        }

        if (!StringUtil.isEmpty(queryParam.getMemberName())) {
            sqlList.add(" pay_member_name = ?");
            paramList.add(queryParam.getMemberName());
        }

        if (!StringUtil.isEmpty(queryParam.getOrderSn())) {
            sqlList.add(" order_sn like ?");
            paramList.add("%" + queryParam.getOrderSn() + "%");
        }

        if (!StringUtil.isEmpty(queryParam.getPayWay())) {
            sqlList.add(" pay_way = ?");
            paramList.add(queryParam.getPayWay());
        }

        sql.append(SqlUtil.sqlSplicing(sqlList));
        sql.append(" order by pay_log_id desc");

        List<PayLog> payLogList = this.daoSupport.queryForList(sql.toString(), PayLog.class, paramList.toArray());
        return payLogList;
    }

}
