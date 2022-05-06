/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.model.enums;

/**
 * 团购商品审核状态
 *
 * @author Snow create in 2018/4/25
 * @version v2.0
 * @since v7.0.0
 */
public enum GroupBuyGoodsStatusEnum {

    /**
     * 待审核
     */
    PENDING(0),

    /**
     * 通过审核
     */
    APPROVED(1),

    /**
     * 未通过审核
     */
    NOT_APPROVED(2);

    private Integer status;


    GroupBuyGoodsStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer status() {
        return this.status;
    }

    public String value() {
        return this.name();
    }


}
