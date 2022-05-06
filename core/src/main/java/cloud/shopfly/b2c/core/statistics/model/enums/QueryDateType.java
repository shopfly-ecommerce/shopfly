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
package cloud.shopfly.b2c.core.statistics.model.enums;

/**
 * 枚举：搜索日期类型
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/28 下午1:40
 */
public enum QueryDateType {

    // 月份
    MONTH("月份"),
    // 年份
    YEAR("年份");

    private String query;

    QueryDateType(String query){
        this.query=query;
    }

    public String description(){
        return this.query;
    }

    public String value(){
        return this.name();
    }

}