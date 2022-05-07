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

import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;
import cloud.shopfly.b2c.core.system.service.SmtpManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.model.vo.EmailVO;
import cloud.shopfly.b2c.core.base.service.EmailManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Mail business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
@Service
public class SmtpManagerImpl implements SmtpManager {


    @Autowired
    
    private DaoSupport systemDaoSupport;
    @Autowired
    private EmailManager emailManager;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select * from es_smtp";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, SmtpDO.class);
        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SmtpDO edit(SmtpDO smtp, Integer id) {
        SmtpDO smtpDO = this.getModel(id);
        if (smtpDO != null) {
            this.systemDaoSupport.update(smtp, id);
            cache.remove(CachePrefix.SMTP.getPrefix());
            return smtp;
        }
        throw new ResourceNotFoundException("thesmtpNot found！");
    }

    @Override
    public SmtpDO getModel(Integer id) {
        SmtpDO smtp = this.systemDaoSupport.queryForObject(SmtpDO.class, id);
        if (smtp == null) {
            throw new ResourceNotFoundException("thesmtpNot found！");
        }
        return smtp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SmtpDO add(SmtpDO smtp) {
        if (smtp != null && smtp.getOpenSsl() > 1) {
            smtp.setOpenSsl(1);
        }
        this.systemDaoSupport.insert(smtp);
        Integer id = systemDaoSupport.getLastId("es_smtp");
        smtp.setId(id);
        return smtp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        SmtpDO smtp = this.getModel(id);
        if (smtp == null) {
            throw new ResourceNotFoundException("thesmtpNot found！");

        }
        this.systemDaoSupport.delete(SmtpDO.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void send(String send, SmtpDO smtp) {
        EmailVO emailVO = new EmailVO();
        emailVO.setTitle("Test email");
        emailVO.setEmail(send);
        emailVO.setType("Test email");
        emailVO.setContent("Test Email sending");

        if (smtp.getOpenSsl() == 1 || "smtp.qq.com".equals(smtp.getHost())) {
            emailManager.sendMailByTransport(smtp, emailVO);
        } else {
            emailManager.sendMailByMailSender(smtp, emailVO);
        }
    }

    @Override
    public SmtpDO getCurrentSmtp() {
        List<SmtpDO> smtpList = (List<SmtpDO>) cache.get(CachePrefix.SMTP.getPrefix());
        if (smtpList == null || smtpList.size() < 0) {
            String sql = "select * from es_smtp";
            smtpList = this.systemDaoSupport.queryForList(sql, SmtpDO.class);
            cache.put(CachePrefix.SMTP.getPrefix(), smtpList);
        }
        SmtpDO currentSmtp = null;
        for (SmtpDO smtp : smtpList) {
            if (checkCount(smtp)) {
                currentSmtp = smtp;
                break;
            }
        }
        if (currentSmtp == null) {
            throw new ResourceNotFoundException("Not found availablesmtp, have reached the maximum number of messages sent");
        }
        return currentSmtp;

    }

    /**
     * detectionsmtpWhether the server is available
     *
     * @param smtp
     * @return Check whether it passes
     */
    private boolean checkCount(SmtpDO smtp) {
        // Last sending time
        long lastSendTime = smtp.getLastSendTime();
        // Its not today
        if (!DateUtil.toString(new Date(lastSendTime * 1000), "yyyy-MM-dd").equals(DateUtil.toString(new Date(), "yyyy-MM-dd"))) {
            smtp.setSendCount(0);
        }
        return smtp.getSendCount() < smtp.getMaxCount();
    }
}
