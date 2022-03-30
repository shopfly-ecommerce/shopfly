/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.goods;

import dev.shopflix.consumer.core.event.GoodsCommentEvent;
import dev.shopflix.core.base.message.GoodsCommentMsg;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.member.model.dos.MemberComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v1.0
 * @Description: 更改商品的评论数量
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
