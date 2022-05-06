/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单出库的类型
 *
 * @author Snow create in 2018/7/10
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderOutTypeEnum {

    /**
     * 商品（除以下两种之外的所有商品）
     */
    GOODS("商品"),

    /**
     * 团购商品
     */
    GROUPBUY_GOODS("团购商品"),

    /**
     * 限时抢购商品
     */
    SECKILL_GOODS("限时抢购商品");


    private String description;

    OrderOutTypeEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }


    public static List<String> getAll() {
        List<String> all = new ArrayList<>();
        all.add(OrderOutTypeEnum.GOODS.name());
        all.add(OrderOutTypeEnum.GROUPBUY_GOODS.name());
        all.add(OrderOutTypeEnum.SECKILL_GOODS.name());
        return all;
    }
}
