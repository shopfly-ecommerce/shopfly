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
package cloud.shopfly.b2c.core.aftersale;

/**
 * 售后异常码
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum AftersaleErrorCode {

    /**
     * 某个异常
     */
    E600("退款金额不能大于支付金额"),
    E601("操作不允许"),
    E602("商品不存在"),
    E603("退款单不存在"),
    E604("订单不存在"),
    E605("退款方式必填"),
    E606("入库失败"),
    E607("申请售后货品数量不能大于购买数量"),
    E608("导出数据失败"),
    E609("可退款金额为0，无需申请退款/退货，请与平台联系解决");

    private String describe;

    AftersaleErrorCode(String des) {
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
     * 获取异常描述
     *
     * @return
     */
    public String describe() {
        return this.describe;
    }


}
