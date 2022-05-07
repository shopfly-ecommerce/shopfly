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

import cloud.shopfly.b2c.core.member.model.dos.MemberNoticeLog;
import cloud.shopfly.b2c.core.member.service.MemberNoticeLogManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Member station message history business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */
@Service
public class MemberNoticeLogManagerImpl implements MemberNoticeLogManager {

    @Autowired

    private DaoSupport memberDaoSupport;

    @Override
    public Page list(int page, int pageSize, Integer read) {
        StringBuffer sqlBuffer = new StringBuffer("select * from es_member_notice_log where member_id = ? and is_del = 1");
        List<Object> term = new ArrayList<>();
        term.add(UserContext.getBuyer().getUid());
        // Check whether the read parameter is empty. If the read parameter is not empty, add the read parameter to the query condition
        if (read != null) {
            sqlBuffer.append(" and is_read = ?");
            term.add(read);
        }
        sqlBuffer.append(" order by send_time desc");
        Page webPage = this.memberDaoSupport.queryForPage(sqlBuffer.toString(), page, pageSize, MemberNoticeLog.class, term.toArray());
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberNoticeLog add(String content, long sendTime, Integer memberId, String title) {
        MemberNoticeLog memberNoticeLog = new MemberNoticeLog();
        memberNoticeLog.setContent(content);
        memberNoticeLog.setMemberId(memberId);
        memberNoticeLog.setTitle(title);
        // Whether to delete the default normal state
        memberNoticeLog.setIsDel(1);
        // Read Or not The default value is not read
        memberNoticeLog.setIsRead(0);
        memberNoticeLog.setSendTime(sendTime);
        memberNoticeLog.setReceiveTime(DateUtil.getDateline());
        this.memberDaoSupport.insert(memberNoticeLog);
        return memberNoticeLog;
    }


    @Override
    public void read(Integer[] ids) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(ids, term);
        term.add(UserContext.getBuyer().getUid());
        String sql = "update es_member_notice_log set is_read = 1 where id IN (" + str + ") and member_id = ?";
        memberDaoSupport.execute(sql, term.toArray());

    }

    @Override
    public void delete(Integer[] ids) {
        List<Object> term = new ArrayList<>();
        String str = SqlUtil.getInSql(ids, term);
        term.add(UserContext.getBuyer().getUid());
        String sql = "update es_member_notice_log set is_del = 0 where id IN (" + str + ") and member_id = ?";
        memberDaoSupport.execute(sql, term.toArray());
    }
}
