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
package cloud.shopfly.b2c.framework.database;

import java.util.Arrays;

/**
 * metadata
 * @author Snow create in 2018/3/28
 * @version v2.0
 * @since v7.0.0
 */
public class DataMeta {

    /**
     * sql statements
     */
    private String sql;

    /**
     * sql The parameters needed in the statement
     */
    private Object[] paramter;


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParamter() {
        return paramter;
    }

    public void setParamter(Object[] paramter) {
        this.paramter = paramter;
    }

    @Override
    public String toString() {
        return "DataMeta{" +
                "sql='" + sql + '\'' +
                ", paramter=" + Arrays.toString(paramter) +
                '}';
    }
}
