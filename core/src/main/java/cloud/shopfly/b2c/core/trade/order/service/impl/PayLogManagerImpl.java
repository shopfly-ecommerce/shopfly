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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.dto.PayLogQueryParam;
import cloud.shopfly.b2c.core.trade.order.service.PayLogManager;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Receipt business class
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
     * logging
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page list(PayLogQueryParam queryParam) {

        StringBuffer sql = new StringBuffer("select * from es_pay_log ");
        List<String> sqlList = new ArrayList<>();
        List paramList = new ArrayList();

        // Payment method
        if (!StringUtil.isEmpty(queryParam.getPaymentType())) {
            sqlList.add(" pay_way = ? ");
            paramList.add(queryParam.getPaymentType());
        }

        // Payment status
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
                value = "%Alipay%";
            } else if ("wechat".equals(payWay)) {
                value = "%WeChat%";
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
