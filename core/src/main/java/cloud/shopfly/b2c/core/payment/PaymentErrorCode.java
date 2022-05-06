/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.payment;

/**
 * 支付异常码
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum PaymentErrorCode {

    /**
     * 不存在的交易
     */
    E500("不存在的交易"),
    /**
     * 不存在的支付方式
     */
    E501("不存在的支付方式"),
    /**
     * 未开启的支付方式
     */
    E502("未开启的支付方式"),
    /**
     * 支付回调验证失败
     */
    E503("支付回调验证失败"),
    /**
     * 支付账单不存在
     */
    E504("支付账单不存在"),
    /**
     * 支付方式参数不正确
     */
    E505("支付方式参数不正确"),
    /**
     * 订单状态不正确，无法支付
     */
    E506("订单状态不正确无法支付"),

    /**
     * 没有找到适合的回调器
     */
    E507("没有找到适合的回调器"),

    E509("openid不能为空");

    private String describe;

    PaymentErrorCode(String des) {
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

    public String getDescribe() {
        return this.describe;
    }
}
