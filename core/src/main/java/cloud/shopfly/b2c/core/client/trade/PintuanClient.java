/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade;


import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;

import java.util.List;

/**
 * 拼团client
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/18 上午11:39
 */

public interface PintuanClient {


    /**
     * 获取拼团
     *
     * @param id 拼团主键
     * @return Pintuan  拼团
     */
    Pintuan getModel(Integer id);


    /**
     * 停止一个活动
     *
     * @param promotionId
     */
    void closePromotion(Integer promotionId);

    /**
     * 开始一个活动
     *
     * @param promotionId
     */
    void openPromotion(Integer promotionId);

    /**
     * 根据状态查询拼团活动
     *
     * @param status 状态
     * @return 拼团活动集合
     */
    List<Pintuan> get(String status);
}
