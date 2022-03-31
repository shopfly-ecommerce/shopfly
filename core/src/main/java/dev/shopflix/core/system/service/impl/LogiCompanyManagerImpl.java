/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.dos.LogiCompanyDO;
import dev.shopflix.core.system.service.LogiCompanyManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流公司业务类
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
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LogiCompanyDO add(LogiCompanyDO logi) {
        LogiCompanyDO logicode = this.getLogiByCode(logi.getCode());
        LogiCompanyDO logikdcode = this.getLogiBykdCode(logi.getKdcode());
        LogiCompanyDO loginame = this.getLogiByName(logi.getName());
        if (loginame != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "物流公司名称重复");
        }
        if (logicode != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "物流公司代码重复");
        }
        if (logikdcode != null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "快递鸟公司代码重复");
        }
        //支持电子面单时，代码必填
        if (logi.getIsWaybill() != null && logi.getIsWaybill().equals(1)) {
            if (StringUtil.isEmpty(logi.getKdcode())) {
                throw new ServiceException(SystemErrorCode.E214.name(), "快递鸟公司代码必填");
            }
        }

        this.daoSupport.insert(logi);
        int lastId = this.daoSupport.getLastId("es_logi_company");
        logi.setId(lastId);

        return logi;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LogiCompanyDO edit(@Valid LogiCompanyDO logi, Integer id) {
        LogiCompanyDO model = this.getModel(id);
        if (model == null) {
            throw new ServiceException(SystemErrorCode.E214.name(), "物流公司不存在");
        }
        //当支持电子面单时，需要填写快递鸟物流公司code
        if (logi.getIsWaybill() == 1 && StringUtil.isEmpty(logi.getKdcode())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "快递鸟公司代码必填");
        }

        LogiCompanyDO logicode = this.getLogiByCode(logi.getCode());
        LogiCompanyDO logikdcode = this.getLogiBykdCode(logi.getKdcode());
        LogiCompanyDO loginame = this.getLogiByName(logi.getName());
        if (logikdcode != null && !logikdcode.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "快递鸟公司代码重复");
        }
        if (loginame != null && !loginame.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "物流公司名称重复");
        }
        if (logicode != null && !logicode.getId().equals(logi.getId())) {
            throw new ServiceException(SystemErrorCode.E214.name(), "物流公司代码重复");
        }
        this.daoSupport.update(logi, id);
        return logi;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer[] logiId) {
        for (Integer logi : logiId) {
            LogiCompanyDO model = this.getModel(logi);
            if (model == null) {
                throw new ServiceException(SystemErrorCode.E214.name(), "物流公司不存在");
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
