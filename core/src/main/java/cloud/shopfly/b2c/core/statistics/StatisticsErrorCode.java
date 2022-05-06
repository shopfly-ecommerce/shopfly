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
package cloud.shopfly.b2c.core.statistics;

/**
 * 统计错误码
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-03-26 下午11:56
 */
public enum StatisticsErrorCode {

    // 统计相关错误码
    E801("错误的请求参数"),
    E810("业务处理异常");

    private String describe;

    StatisticsErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return 异常码
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
