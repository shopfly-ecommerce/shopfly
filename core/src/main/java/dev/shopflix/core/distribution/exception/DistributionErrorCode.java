/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.exception;

/**
 * 分销错误码
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:53
 */
public enum DistributionErrorCode {

    //STATISTICS ERROR CODE
    E1000("分销业务异常，请稍后重试。"),
    E1001("用户未登录，请登录后重试"),
    E1002("提现申请不可以重复操作。"),
    E1003("申请金额超出可提现金额。"),
    E1004("错误的提现申请。"),
    E1005("错误的审核状态。"),
    E1006("错误的申请金额。"),
    E1010("默认模版不允许删除!"),
    E1012("模版不允许删除，有分销商使用!"),
    E1013("默认模版不允许修改!"),
    E1011("参数不足!");

    private String describe;

    DistributionErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取统计的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * 获取统计的错误消息
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
