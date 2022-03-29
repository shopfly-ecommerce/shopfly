/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics;

/**
 * 统计错误码
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-03-26 下午11:56
 */
public enum StatisticsErrorCode {

    // 统计相关错误码
    E801("错误的请求参数"),
    E810("业务处理异常");

    private String describe;

    StatisticsErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return 异常码
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
