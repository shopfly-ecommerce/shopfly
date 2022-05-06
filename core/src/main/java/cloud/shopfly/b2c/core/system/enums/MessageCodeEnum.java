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
package cloud.shopfly.b2c.core.system.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 消息模板编号枚举类
 * @ClassName MessageCodeEnum
 * @since v7.0 下午5:03 2018/7/5
 */
public enum MessageCodeEnum {
    /**
     * 店铺新订单创建提醒
     */
    SHOPORDERSNEW("店铺新订单创建提醒"),
    /**
     * 店铺订单支付提醒
     */
    SHOPORDERSPAY("店铺订单支付提醒"),
    /**
     * 店铺订单收货提醒
     */
    SHOPORDERSRECEIVE("店铺订单收货提醒"),
    /**
     * 店铺订单评价提醒
     */
    SHOPORDERSEVALUATE("店铺订单评价提醒"),
    /**
     * 店铺订单取消提醒
     */
    SHOPORDERSCANCEL("店铺订单取消提醒"),
    /**
     * 店铺退款提醒
     */
    SHOPREFUND("店铺退款提醒"),
    /**
     * 店铺退货提醒
     */
    SHOPRETURN("店铺退货提醒"),
    /**
     * 商品违规被禁售提醒（商品下架）
     */
    SHOPGOODSVIOLATION("商品违规被禁售提醒（商品下架）"),
    /**
     * 商品审核失败提醒
     */
    SHOPGOODSVERIFY("商品审核失败提醒"),
    /**
     * 退货提醒
     */
    MEMBERRETURNUPDATE("退货提醒"),
    /**
     * 退款提醒
     */
    MEMBERREFUNDUPDATE("退款提醒"),
    /**
     * 订单发货提醒
     */
    MEMBERORDERSSEND("订单发货提醒"),
    /**
     * 订单收货提醒
     */
    MEMBERORDERSRECEIVE("订单收货提醒"),
    /**
     * 订单支付提醒
     */
    MEMBERORDERSPAY("订单支付提醒"),
    /**
     * 订单取消提醒
     */
    MEMBERORDERSCANCEL("订单取消提醒"),
    /**
     * 手机发送验证码
     */
    MOBILECODESEND("手机发送验证码"),
    /**
     * 商品下架消息提醒
     */
    SHOPGOODSMARKETENABLE("商品下架消息提醒"),
    /**
     * 会员登陆成功提醒
     */
    MEMBERLOGINSUCCESS("会员登陆成功提醒"),
    /**
     * 会员注册成功提醒
     */
    MEMBERREGISTESUCCESS("会员注册成功提醒");

    private String description;

    MessageCodeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
