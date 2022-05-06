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
package cloud.shopfly.b2c.core.distribution.exception;

/**
 * 分销错误码
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:53
 */
public enum DistributionErrorCode {

    //STATISTICS ERROR CODE
    E1000("分销业务异常，请稍后重试。"),
    E1001("用户未登录，请登录后重试"),
    E1002("提现申请不可以重复操作。"),
    E1003("申请金额超出可提现金额。"),
    E1004("错误的提现申请。"),
    E1005("错误的审核状态。"),
    E1006("错误的申请金额。"),
    E1010("默认模版不允许删除!"),
    E1012("模版不允许删除，有分销商使用!"),
    E1013("默认模版不允许修改!"),
    E1011("参数不足!");

    private String describe;

    DistributionErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取统计的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


    /**
     * 获取统计的错误消息
     *
     * @return
     */
    public String des() {
        return this.describe;
    }


}
