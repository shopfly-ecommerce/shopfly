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

import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.service.MemberPointHistoryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member points form business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
@Service
public class MemberPointHistoryManagerImpl implements MemberPointHistoryManager {

    @Autowired
    
    private DaoSupport memberDaoSupport;

    @Override
    public Page list(int page, int pageSize, Integer memberId) {
        String sql = "select * from es_member_point_history  where member_id = ? and (grade_point>0 || consum_point>0) order by time desc";
        Page webPage = this.memberDaoSupport.queryForPage(sql, page, pageSize, MemberPointHistory.class, memberId);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberPointHistory add(MemberPointHistory memberPointHistory) {
        this.memberDaoSupport.insert(memberPointHistory);
        return memberPointHistory;
    }


}
