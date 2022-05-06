/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.model.enums;

/**
 * 地区数据类型
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/9 11:59
 */
public enum RegionsDataType {

    // 下单会员数
    ORDER_MEMBER_NUM("下单会员数"),
    // 下单金额
    ORDER_PRICE("下单金额"),
    // 下单量
    ORDER_NUM("下单量");

    private String description;

    RegionsDataType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public String value() {
        return this.name();
    }

}
