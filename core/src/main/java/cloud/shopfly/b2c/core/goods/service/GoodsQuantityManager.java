/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;

import java.util.List;
import java.util.Map;


/**
 * 商品库存接口
 *
 * @author fk
 * @version 3.0
 * 统一为一个接口（更新接口）<br/>
 * 内部实现为redis +lua 保证原子性 -- by kingapex 2019-01-17
 * @since v7.0.0
 * 2018年3月23日 上午11:47:29
 */
public interface GoodsQuantityManager {


    /**
     * 为某个sku 填充库存cache<br/>
     * 库存数量由数据库中获取<br/>
     * 一般用于缓存被击穿的情况
     *
     * @param skuId
     * @return 可用库存和实际库存
     */
    Map<String, Integer> fillCacheFromDB(int skuId);

    /**
     * 库存更新接口
     * @param goodsQuantityList 要更新的库存vo List
     * @return 如果更新成功返回真，否则返回假
     */
    Boolean updateSkuQuantity(List<GoodsQuantityVO> goodsQuantityList );

    /**
     * 同步数据库数据
     */
    void syncDataBase();


}
