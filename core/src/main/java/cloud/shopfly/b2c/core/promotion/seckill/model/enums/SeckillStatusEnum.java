/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.seckill.model.enums;

/**
 * 限时活动状态
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年12月15日 下午3:32:43
 */
public enum SeckillStatusEnum {

    /**
     * 编辑中
     */
    EDITING("编辑中"),

    /**
     * 已发布
     */
    RELEASE("已发布"),

    /**
     * 已关闭
     */
    CLOSED("已关闭"),
    /**
     * 已结束
     */
    OVER("已结束");

    private String description;

    SeckillStatusEnum(String description) {
        this.description = description;

    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
