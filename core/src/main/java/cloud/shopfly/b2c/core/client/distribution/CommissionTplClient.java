/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.distribution;

import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;

/**
 * 模版client
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/14 下午2:17
 */

public interface CommissionTplClient {

    /**
     * 获取默认模版
     *
     * @return
     */
    CommissionTpl getDefaultCommission();

}
