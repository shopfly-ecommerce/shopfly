/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.event;

import cloud.shopfly.b2c.core.base.message.GoodsCommentMsg;

/**
 * 商品评论事件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:24:49
 */
public interface GoodsCommentEvent {

    /**
     * 商品评论后执行
     *
     * @param goodsCommentMsg
     */
    void goodsComment(GoodsCommentMsg goodsCommentMsg);
}
