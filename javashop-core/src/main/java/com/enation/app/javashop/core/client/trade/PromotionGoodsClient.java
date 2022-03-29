/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.trade;

/**
 * 促销活动客户端
 *
 * @author zh
 * @version v7.0
 * @date 19/3/28 上午11:10
 * @since v7.0
 */
public interface PromotionGoodsClient {

    /**
     * 删除促销活动商品
     * @param goodsId
     */
    void delPromotionGoods(Integer goodsId);
}
