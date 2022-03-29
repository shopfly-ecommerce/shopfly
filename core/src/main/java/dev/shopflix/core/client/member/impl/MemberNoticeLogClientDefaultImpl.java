/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.MemberNoticeLogClient;
import dev.shopflix.core.member.model.dos.MemberNoticeLog;
import dev.shopflix.core.member.service.MemberNoticeLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v2.0
 * @Description: 会员站内短消息
 * @date 2018/8/14 10:18
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class MemberNoticeLogClientDefaultImpl implements MemberNoticeLogClient {

    @Autowired
    private MemberNoticeLogManager memberNociceLogManager;

    @Override
    public MemberNoticeLog add(String content, long sendTime, Integer memberId, String title) {

        return memberNociceLogManager.add(content,sendTime,memberId,title);
    }
}
