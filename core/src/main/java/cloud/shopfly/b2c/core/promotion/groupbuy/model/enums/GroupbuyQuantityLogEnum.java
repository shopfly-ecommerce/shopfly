/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.model.enums;

/**
 * GroupbuyQuantityLogEnum
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-09-21 上午9:01
 */
public enum GroupbuyQuantityLogEnum {

    /** 已结束*/
    BUY("售出"),

    /** 进行中*/
    CANCEL("取消");

    private String status;


    GroupbuyQuantityLogEnum(String status) {
        this.status=status;
    }

    public String getStatus() {
        return this.status;
    }
}
