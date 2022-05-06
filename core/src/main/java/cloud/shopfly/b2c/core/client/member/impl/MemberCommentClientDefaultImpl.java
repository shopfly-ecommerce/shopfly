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
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
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
