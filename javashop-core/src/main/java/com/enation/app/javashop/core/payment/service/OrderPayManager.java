/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.service;

import com.enation.app.javashop.core.payment.model.dto.PayParam;
import com.enation.app.javashop.core.payment.model.vo.Form;

import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 订单支付
 * @date 2018/4/1617:09
 * @since v7.0.0
 */
public interface OrderPayManager {

    /**
     * 支付
     *
     * @param param
     * @return
     */
    Map pay(PayParam param);


}
