/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.enums;

/**
 * Created by kingapex on 2019-01-17.
 * 库存数类型
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-17
 */
public enum QuantityType {

    /**
     * 实际的库存，包含了待发货的
     */
    actual,

    /**
     * 可以售的库存，不包含待发货的
     */
    enable

}
