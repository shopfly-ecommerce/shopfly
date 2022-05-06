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
 * 退款(货)流程枚举类
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午9:25 2018/5/3
 */
public enum RefundStatusEnum {

    //申请中
    APPLY("申请中"),
    //申请通过
    PASS("申请通过"),
    //审核拒绝
    REFUSE("审核拒绝"),
    //退货入库
    STOCK_IN("退货入库"),
    //待人工处理
    WAIT_FOR_MANUAL("待人工处理"),
    //申请取消
    CANCEL("申请取消"),
    //退款中
    REFUNDING("退款中"),
    //退款失败
    REFUNDFAIL("退款失败"),
    //完成
    COMPLETED("完成");

    private String description;

    RefundStatusEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
