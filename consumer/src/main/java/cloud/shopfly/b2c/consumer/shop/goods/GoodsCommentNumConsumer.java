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
package cloud.shopfly.b2c.consumer.shop.goods;

import cloud.shopfly.b2c.consumer.core.event.GoodsCommentEvent;
import cloud.shopfly.b2c.core.base.message.GoodsCommentMsg;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v1.0
 * @Description: Change the number of reviews for an item
 * @date 2018/6/25 10:23
 * @since v7.0.0
 */
@Service
public class GoodsCommentNumConsumer implements GoodsCommentEvent {

    @Autowired
    private GoodsClient goodsClient;

    @Override
    public void goodsComment(GoodsCommentMsg goodsCommentMsg) {

        MemberComment comment = goodsCommentMsg.getComment();
        if (comment != null) {
            this.goodsClient.updateCommentCount(comment.getGoodsId());
        }
    }
}
