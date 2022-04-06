/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.system.model.dos.NoticeLogDO;
import dev.shopflix.core.system.service.NoticeLogManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
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
 * 店铺站内消息业务类
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
