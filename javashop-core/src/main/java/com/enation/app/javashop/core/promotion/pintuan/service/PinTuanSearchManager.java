/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.service;

import com.enation.app.javashop.core.promotion.pintuan.model.PinTuanGoodsVO;
import com.enation.app.javashop.core.promotion.pintuan.model.PtGoodsDoc;

import java.util.List;

/**
 * Created by kingapex on 2019-01-21.
 * 拼团搜索业务接口
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-21
 */
public interface PinTuanSearchManager {

    /**
     * 搜索拼团商品
     *
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<PtGoodsDoc> search(Integer categoryId, Integer pageNo, Integer pageSize);


    /**
     * 向es写入索引
     *
     * @param goodsDoc
     */
    void addIndex(PtGoodsDoc goodsDoc);

    /**
     * 向es写入索引
     *
     * @param pintuanGoods
     * @return 是否生成成功
     */
    boolean addIndex(PinTuanGoodsVO pintuanGoods);

    /**
     * 删除一个sku的索引
     *
     * @param skuId
     */
    void delIndex(Integer skuId);


    /**
     * 删除某个商品的所有的索引
     *
     * @param goodsId
     */
    void deleteByGoodsId(Integer goodsId);


    /**
     * 删除某个拼团的所有索引
     *
     * @param pinTuanId 拼团id
     */
    void deleteByPintuanId(Integer pinTuanId);

    /**
     * 根据拼团id同步es中的拼团商品<br/>
     * 当拼团活动商品发生变化时调用此方法
     *
     * @param pinTuanId
     */
    void syncIndexByPinTuanId(Integer pinTuanId);

    /**
     * 根据商品id同步es中的拼团商品<br>
     *
     * @param goodsId 商品id
     */
    void syncIndexByGoodsId(Integer goodsId);
}
