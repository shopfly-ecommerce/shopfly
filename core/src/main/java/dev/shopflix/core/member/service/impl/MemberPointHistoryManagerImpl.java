/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.member.model.dos.MemberPointHistory;
import dev.shopflix.core.member.service.MemberPointHistoryManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员积分表业务类
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
