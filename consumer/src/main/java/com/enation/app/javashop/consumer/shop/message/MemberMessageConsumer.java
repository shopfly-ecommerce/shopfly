/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.message;

import com.enation.app.javashop.consumer.core.event.MemberMessageEvent;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.client.member.MemberNoticeLogClient;
import com.enation.app.javashop.core.client.system.MessageClient;
import com.enation.app.javashop.core.system.model.dos.Message;
import com.enation.app.javashop.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 会员站内消息consumer
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年10月14日 上午11:27:15
 */
@Component
public class MemberMessageConsumer implements MemberMessageEvent {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private MemberNoticeLogClient memberNoticeLogClient;

    @Autowired
    private MemberClient memberClient;


    @Override
    public void memberMessage(int messageId) {
        Message message = messageClient.get(messageId);
        if (message != null) {
            //发送类型    0  全站   1   部分
            Integer sendType = message.getSendType();
            List<String> memberIdsRes;
            String memberIds;
            if (sendType != null && sendType.equals(0)) {
                memberIds =  memberClient.queryAllMemberIds();
            } else {
                memberIds = message.getMemberIds();
            }
            String[] memberIdsArray = memberIds.split(",");
            memberIdsRes = Arrays.asList(memberIdsArray);
            if (memberIdsRes.size() > 0) {
                String msgContent = message.getContent();
                for (String id : memberIdsRes) {
                    memberNoticeLogClient.add(msgContent, message.getSendTime(), StringUtil.toInt(id, false), message.getTitle());
                }
            }
        }
    }
}
