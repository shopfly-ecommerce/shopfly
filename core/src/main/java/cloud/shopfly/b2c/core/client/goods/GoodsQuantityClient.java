/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.goods;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;

import java.util.List;

/**
 * 商品库存操作客户端
 * @author zh
 * @version v1.0
 * @date 18/9/20 下午7:31
 * @since v7.0
 *
 * @version 2.0
 * 统一为一个接口（更新接口）<br/>
 * 内部实现为redis +lua 保证原子性 -- by kingapex 2019-01-17
 */
public interface GoodsQuantityClient {


    /**
     * 库存更新接口
     * @param goodsQuantityList 要扣减的库存vo List
     * @return 如果扣减成功返回真，否则返回假
     */
    boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList);
}


