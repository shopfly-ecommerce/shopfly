/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.goods.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsIndexClient;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品索引
 * @date 2018/8/14 14:13
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class GoodsIndexClientDefaultImpl implements GoodsIndexClient {

    @Autowired
    private GoodsIndexManager goodsIndexManager;

    @Override
    public void addIndex(Map goods) {
        goodsIndexManager.addIndex(goods);
    }

    @Override
    public void updateIndex(Map goods) {
        goodsIndexManager.updateIndex(goods);

    }

    @Override
    public void deleteIndex(Map goods) {
        goodsIndexManager.deleteIndex(goods);
    }

    @Override
    public boolean addAll(List<Map<String, Object>> list, int index) {
        return  goodsIndexManager.addAll(list,index);
    }
}
