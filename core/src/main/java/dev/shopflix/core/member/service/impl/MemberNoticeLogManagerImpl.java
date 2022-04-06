/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.member.model.dos.MemberNoticeLog;
import dev.shopflix.core.member.service.MemberNoticeLogManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员站内消息历史业务类
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
        //校验是否已读参数是否为空，不为空则加入查询条件进行查询
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
        //是否删除默认正常状态
        memberNoticeLog.setIsDel(1);
        //是否已读默认未读
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
