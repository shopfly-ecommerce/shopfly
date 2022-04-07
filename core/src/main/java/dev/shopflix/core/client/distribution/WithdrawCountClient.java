/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.distribution;

/**
 * 分销结算
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/14 下午1:02
 */
public interface WithdrawCountClient {


    /**
     * 每日进行结算
     */
    void everyDay();
}