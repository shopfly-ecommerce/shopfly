/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion;

/**
 * 交易异常码
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum PromotionErrorCode {

    /**
     * 参数错误
     */
    E400("参数错误"),

    /**
     * 活动商品发生冲突
     */
    E401("活动商品发生冲突"),

    /**
     * 活动发生冲突
     */
    E402("活动发生冲突"),

    /**
     * 活动商品库存不足
     */
    E403("活动商品库存不足"),

    /**
     * 超出团购商品限购
     */
    E404("超出团购商品限购"),

    /**
     * 限领数大于发行量
     */
    E405("限领数超出发行量"),

    /**
     * 限领数量不合规
     */
    E406("限领数量不合规"),

    /**
     * 积分分类重复
     */
    E407("积分分类重复"),
    /**
     * 团购活动相关错误
     */
    E408("团购活动相关错误"),

    /**
     * 促销金额错误
     */
    E409("促销金额错误")
    ;

    private String describe;

    PromotionErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    /**
     * 异常消息
     *
     * @return
     */
    public String des() {
        return this.describe;
    }
}
