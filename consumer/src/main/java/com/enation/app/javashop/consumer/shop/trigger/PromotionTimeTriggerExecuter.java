/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.trigger;

import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.goods.GoodsIndexClient;
import com.enation.app.javashop.core.promotion.tool.model.dto.PromotionPriceDTO;
import com.enation.app.javashop.framework.trigger.Interface.TimeTriggerExecuter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 评团活动 延时执行器
 *
 * @author zh
 * @version v1.0
 * @since v7.0
 * 2019-02-13 下午5:34
 */
@Component("promotionTimeTriggerExecuter")
public class PromotionTimeTriggerExecuter implements TimeTriggerExecuter {

    @Autowired
    private GoodsIndexClient goodsIndexClient;

    @Autowired
    private GoodsClient goodsClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 执行促销活动商品添加后重新更改索引商品价格
     *
     * @param object 任务参数
     */
    @Override
    public void execute(Object object) {
        try {
            //获取商品id
            PromotionPriceDTO promotionPriceDTO = (PromotionPriceDTO) object;
            //获取商品信息
            Integer[] goodsIds = {promotionPriceDTO.getGoodsId()};
            List<Map<String, Object>> list = goodsClient.getGoodsAndParams(goodsIds);
            list.get(0).put("discount_price", promotionPriceDTO.getPrice());
            //重新生成索引
            goodsIndexClient.updateIndex(list.get(0));
        } catch (Exception e) {
            logger.error("促销活动新增商品生成索引失败");
        }
    }
}
