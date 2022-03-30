/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.pagecreate;

import dev.shopflix.consumer.core.event.GoodsChangeEvent;
import dev.shopflix.consumer.shop.pagecreate.service.PageCreator;
import com.enation.app.javashop.core.base.message.GoodsChangeMsg;
import com.enation.app.javashop.core.pagecreate.model.PageCreatePrefixEnum;
import com.enation.app.javashop.core.payment.model.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品页面生成
 *
 * @author zh
 * @version v1.0
 * @since v6.4.0 2017年8月29日 下午3:40:14
 */
@Component
public class GoodsChangeConsumer implements GoodsChangeEvent {

    @Autowired
    private PageCreator pageCreator;


    /**
     * 生成商品静态页
     *
     * @param goodsChangeMsg 商品变化对象
     */
    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {

        try {

            Integer[] goodsIds = goodsChangeMsg.getGoodsIds();
            //查看商品是否存在，不存在则应将静态页删除
            //删除商品先不删除静态页，防止影响促销相关逻辑  add by liuyulei 2019-06-03
//            if (GoodsChangeMsg.DEL_OPERATION == goodsChangeMsg.getOperationType()) {
//                for (int i = 0; i < goodsIds.length; i++) {
//                    String pageName = PageCreatePrefixEnum.GOODS.getHandlerGoods(goodsIds[i]);
//                    this.pageCreator.deleteGoods("/" + ClientType.PC.name() + pageName);
//                    this.pageCreator.deleteGoods("/" + ClientType.WAP.name() + pageName);
//                }
//                return;
//            }

            /** 为了防止生成的商品在首页存在 所有先生成首页一次 */
            pageCreator.createIndex();

            for (int i = 0; i < goodsIds.length; i++) {
                String pageName = PageCreatePrefixEnum.GOODS.getHandlerGoods(goodsIds[i]);
                /** 生成静态页面 */
                pageCreator.createOne(pageName, ClientType.PC.name(), "/" + ClientType.PC.name() + pageName);
                //pageCreator.createOne(pageName, ClientType.WAP.name(), "/" + ClientType.WAP.name() + pageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
