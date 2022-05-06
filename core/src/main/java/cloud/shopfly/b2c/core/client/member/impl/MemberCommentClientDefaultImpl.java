/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberCommentClient;
import cloud.shopfly.b2c.core.member.model.vo.GoodsGrade;
import cloud.shopfly.b2c.core.member.service.MemberCommentManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: 会员评论对外接口实现
 * @date 2018/7/26 11:35
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class MemberCommentClientDefaultImpl implements MemberCommentClient {

    @Autowired
    private MemberCommentManager memberCommentManager;

    @Override
    public List<GoodsGrade> queryGoodsGrade() {
        return memberCommentManager.queryGoodsGrade();
    }

    @Override
    public Integer getGoodsCommentCount(Integer goodsId) {
        return memberCommentManager.getGoodsCommentCount(goodsId);
    }

    @Override
    public void autoGoodComments(List<OrderDetailDTO> detailDTOList) {
        memberCommentManager.autoGoodComments(detailDTOList);
    }

    @Override
    public void editComment(Integer memberId, String face) {
        memberCommentManager.editComment(memberId, face);
    }
}
