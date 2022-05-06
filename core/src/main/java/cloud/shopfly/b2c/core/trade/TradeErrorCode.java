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
package cloud.shopfly.b2c.core.trade;

/**
 * 交易异常码 范围：451~499
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum TradeErrorCode {

    /**
     * 加入购物车商品异常
     */
    E451("商品异常"),

    /**
     * 下单前检验异常
     */
    E452("下单前检验异常"),

    /**
     * 读取异常：如订单不存在
     */
    E453("读取异常"),

    /**
     * 付款异常：如付款金额与订单实付金额不一致
     */
    E454("付款异常"),

    /**
     * 参数异常
     */
    E455("参数异常"),

    /**
     * 订单创建异常
     */
    E456("订单创建异常"),

    /**
     * 导出Excel异常
     */
    E457("导出Excel异常"),

    /**
     * 交易不存在
     */
    E458("交易不存在"),

    /**
     * 订单不存在
     */
    E459("订单不存在"),

    /**
     * 操作订单无权限
     */
    E460("操作订单无权限"),

    /**
     * 商品不在配送区域
     */
    E461("商品不在配送区域"),

    /**
     * 使用促销活动出现错误
     */
    E462("使用促销活动出现错误"),

    /*
     * 订单金额不正确
     */
    E471("订单金额不正确");


    private String describe;

    TradeErrorCode(String des) {
        this.describe = des;
    }

    public String code() {
        return this.name().replaceAll("E", "");
    }


}
