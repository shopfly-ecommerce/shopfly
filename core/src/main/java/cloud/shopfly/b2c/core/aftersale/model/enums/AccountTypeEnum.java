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
package cloud.shopfly.b2c.core.aftersale.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 退款账户类型枚举类
 * @ClassName AccountTypeEnum
 * @since v7.0 下午3:11 2018/6/7
 */
public enum AccountTypeEnum {

    //支付宝
    ALIPAY("支付宝"),
    //微信
    WEIXINPAY("微信"),
    //银行转账
    BANKTRANSFER("银行转账");

    private String description;

    AccountTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
