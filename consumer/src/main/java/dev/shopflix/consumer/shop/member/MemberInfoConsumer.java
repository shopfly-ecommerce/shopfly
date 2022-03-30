/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.member;

import dev.shopflix.consumer.core.event.MemberInfoChangeEvent;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.client.member.MemberCommentClient;
import com.enation.app.javashop.core.member.model.dos.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会员信息消费者
 *
 * @author zh
 * @version v7.0
 * @date 18/12/26 下午4:39
 * @since v7.0
 */
@Component
public class MemberInfoConsumer implements MemberInfoChangeEvent {

    @Autowired
    private MemberCommentClient memberCommentClient;
    @Autowired
    private MemberClient memberClient;

    @Override
    public void memberInfoChange(Integer memberId) {
        //获取用户信息
        Member member = memberClient.getModel(memberId);
        //修改用户评论信息
        memberCommentClient.editComment(member.getMemberId(), member.getFace());

    }
}
