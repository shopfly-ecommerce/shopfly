/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.exception;

/**
 * PromotionErrorCode
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-19 下午1:31
 */
public enum PintuanErrorCode {

    /**
     * PintuanErrorCode
     */
    E5011("活动时间内，无法编辑活动，请前往商家端进行停止"),
    E5012("非活动时间，无法开启/关闭活动"),
    E5013("非法操作"),

    E5014("积分商品无法参加"),
    E5015("活动商品无法参加"),
    E5016("拼团商品无法参与"),
    E5017("活动正在进行，无法操作，如要操作请联系管理员"),
    E5018("超出限购数量");

    private String describe;

    PintuanErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取商品的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    /**
     * 获取错误提示
     *
     * @return
     */
    public String describe() {
        return this.describe;
    }

}
