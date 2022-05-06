/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade;

import cloud.shopfly.b2c.core.goods.model.dto.ExchangeClientDTO;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;

/**
 * @author fk
 * @version v2.0
 * @Description: 积分兑换
 * @date 2018/8/21 15:13
 * @since v7.0.0
 */
public interface ExchangeGoodsClient {

    /**
     * 添加积分兑换
     * @param dto 积分兑换
     * @return ExchangeSetting 积分兑换 */
    ExchangeDO add(ExchangeClientDTO dto);

    /**
     * 修改积分兑换
     * @param dto 商品DTO
     * @return ExchangeSetting 积分兑换
     */
    ExchangeDO edit(ExchangeClientDTO dto);

    /**
     * 查询某个商品的积分兑换信息
     * @param goodsId
     * @return
     */
    ExchangeDO getModelByGoods(Integer goodsId);

    /**
     * 删除某个商品的积分兑换信息
     * @param goodsId
     * @return
     */
    void del(Integer goodsId);

}
