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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.core.system.service.LogiCompanyManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Logistics company business
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
@Service
public class LogiCompanyManagerImpl implements LogiCompanyManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_logi_company  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, LogiCompanyDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LogiCompanyDO add(LogiCompanyDO logi) {
        LogiCompanyDO logicode = this.getLogiByCode(logi.getCode());
        LogiCompanyDO logikdcode = this.getLogiBykdCode(logi.getKdcode());
        LogiCompanyDO loginame = this.getLogiByName(logi.getName());
        if (loginame != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "The name of logistics company is the same");
        }
        if (logicode != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Logistics company code duplication");
        }
        if (logikdcode != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Express bird company code duplication");
        }
        // The code is mandatory if the electronic sheet is supported
        if (logi.getIsWaybill() != null && logi.getIsWaybill().equals(1)) {
            if (StringUtil.isEmpty(logi.getKdcode())) {
                throw new ServiceException(SystemErrorCode.E214.name(), "Express bird Company code mandatory");
            }
        }

        this.daoSupport.insert(logi);
        int lastId = this.daoSupport.getLastId("es_logi_company");
        logi.setId(lastId);

        return logi;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LogiCompanyDO edit(@Valid LogiCompanyDO logi, Integer id) {
        LogiCompanyDO model = this.getModel(id);
        if (model == null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Logistics companies do not exist");
        }
        // The code of express Bird Logistics company needs to be filled in when supporting electronic sheet
        if (logi.getIsWaybill() == 1 && StringUtil.isEmpty(logi.getKdcode())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Express bird Company code mandatory");
        }

        LogiCompanyDO logicode = this.getLogiByCode(logi.getCode());
        LogiCompanyDO logikdcode = this.getLogiBykdCode(logi.getKdcode());
        LogiCompanyDO loginame = this.getLogiByName(logi.getName());
        if (logikdcode != null && !logikdcode.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Express bird company code duplication");
        }
        if (loginame != null && !loginame.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "The name of logistics company is the same");
        }
        if (logicode != null && !logicode.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "Logistics company code duplication");
        }
        this.daoSupport.update(logi, id);
        return logi;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer[] logiId) {
        for (Integer logi : logiId) {
            LogiCompanyDO model = this.getModel(logi);
            if (model == null) {
                throw new ServiceException(SystemErrorCode.E214.name(), "Logistics companies do not exist");
            }
        }
        String id = StringUtil.implode(",", logiId);
        if (id == null || "".equals(id)) {
            return;
        }
        List term = new ArrayList<>();
        String idsStr = SqlUtil.getInSql(logiId, term);
        String sql = "delete from es_logi_company where id in (" + idsStr + ")";
        this.daoSupport.execute(sql, term.toArray());
    }

    @Override
    public LogiCompanyDO getModel(Integer id) {
        return this.daoSupport.queryForObject(LogiCompanyDO.class, id);
    }

    @Override
    public LogiCompanyDO getLogiByCode(String code) {
        String sql = "select * from es_logi_company where code=?";
        LogiCompanyDO logiCompany = this.daoSupport.queryForObject(sql, LogiCompanyDO.class, code);
        return logiCompany;
    }

    @Override
    public LogiCompanyDO getLogiBykdCode(String kdcode) {
        String sql = "select * from es_logi_company where kdcode=?";
        LogiCompanyDO logiCompany = this.daoSupport.queryForObject(sql, LogiCompanyDO.class, kdcode);
        return logiCompany;
    }

    @Override
    public LogiCompanyDO getLogiByName(String name) {
        String sql = "select * from es_logi_company where name=?";
        LogiCompanyDO logiCompany = this.daoSupport.queryForObject(sql, LogiCompanyDO.class, name);
        return logiCompany;
    }

    @Override
    public List<LogiCompanyDO> list() {
        String sql = "select * from es_logi_company  ";
        return this.daoSupport.queryForList(sql, LogiCompanyDO.class);
    }
}
