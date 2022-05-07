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

/**
 * Metadata builder
 * Build model build executionsqlThe builder
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public interface SqlMetaBuilder {

    /**
     * Obtain metadata information of new data through a model
     * @param <T> modelThe type of
     * @param model model
     * @return insertThe metadata
     */
    <T> DataMeta insert(T model);

    /**
     * Retrieve metadata information about modified data through a model
     * @param <T>  modelThe type of
     * @param model model
     * @param id The primary key
     * @return updateThe metadata
     */
    <T> DataMeta update(T model, Integer id);

    /**
     * throughClassTo query a row of datasql
     * @param clazz Type of model
     * @return Query for a modelsql, which has been concatenated according to the primary key querywhereconditions
     */
    String queryForModel(Class clazz);

    /**
     * throughClassTo delete a row of datasql
     * @param clazz Type of model
     * @return Delete thesql语句，其中已经拼接好根据主键Delete thewhereconditions
     */
    String delete(Class clazz);
}
