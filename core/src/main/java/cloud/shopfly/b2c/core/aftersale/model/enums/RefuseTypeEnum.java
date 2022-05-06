/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.aftersale.model.enums;

/**
 * 退款(货)类型
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午9:41 2018/5/3
 */
public enum RefuseTypeEnum {

    //退款
    RETURN_MONEY("退款"),
    //退货
    RETURN_GOODS("退货");

    private String description;

    RefuseTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
