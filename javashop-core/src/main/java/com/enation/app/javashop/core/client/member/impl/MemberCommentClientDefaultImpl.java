/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.member.impl;

import com.enation.app.javashop.core.client.member.MemberCommentClient;
import com.enation.app.javashop.core.member.model.vo.GoodsGrade;
import com.enation.app.javashop.core.member.service.MemberCommentManager;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;
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
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
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
