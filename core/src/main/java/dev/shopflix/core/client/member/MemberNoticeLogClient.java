/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member;

import dev.shopflix.core.member.model.dos.MemberNoticeLog;

/**
 * @author fk
 * @version v2.0
 * @Description: 会员短消息
 * @date 2018/8/14 10:17
 * @since v7.0.0
 */
public interface MemberNoticeLogClient {

    /**
     * 添加会员站内消息历史
     *
     * @param content  消息内容
     * @param sendTime 发送时间
     * @param memberId 会员id
     * @param title    标题
     * @return 历史消息
     */
    MemberNoticeLog add(String content, long sendTime, Integer memberId, String title);
}
