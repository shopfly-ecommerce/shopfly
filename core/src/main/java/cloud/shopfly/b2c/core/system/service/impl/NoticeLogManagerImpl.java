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

import cloud.shopfly.b2c.core.system.model.dos.NoticeLogDO;
import cloud.shopfly.b2c.core.system.service.NoticeLogManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * In-store message business class
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-10 10:21:45
 */
@Service
public class NoticeLogManagerImpl implements NoticeLogManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize, String type, Integer isRead) {
        List<Object> term = new ArrayList<>();
        term.add(isRead);
        StringBuffer stringBuffer = new StringBuffer("select * from es_notice_log where is_delete = 0 and is_read =? ");
        if (!StringUtil.isEmpty(type)) {
            stringBuffer.append(" and type = ?");
            term.add(type);
        }
        stringBuffer.append(" ORDER BY send_time DESC ");
        Page webPage = this.daoSupport.queryForPage(stringBuffer.toString(), page, pageSize, NoticeLogDO.class, term.toArray());

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public NoticeLogDO add(NoticeLogDO shopNoticeLog) {
        this.daoSupport.insert(shopNoticeLog);

        return shopNoticeLog;
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer[] ids) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(ids, term);
        String sql = "update es_notice_log set is_delete = 1 where id IN (" + str + ")";
        daoSupport.execute(sql, term.toArray());
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void read(Integer[] ids) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(ids, term);
        String sql = "update es_notice_log set is_read = 1 where id IN (" + str + ")";
        daoSupport.execute(sql, term.toArray());
    }
}
