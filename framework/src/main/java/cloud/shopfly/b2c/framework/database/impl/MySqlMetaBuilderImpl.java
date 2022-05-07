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
package cloud.shopfly.b2c.framework.database.impl;

import cloud.shopfly.b2c.framework.database.ColumnMeta;
import cloud.shopfly.b2c.framework.database.DataMeta;
import cloud.shopfly.b2c.framework.database.SqlMetaBuilder;
import cloud.shopfly.b2c.framework.util.ReflectionUtil;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Based on theMysqlBasic add, delete, change and check operation implementation class
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class MySqlMetaBuilderImpl implements SqlMetaBuilder {

    @Override
    public <T> DataMeta insert(T model) {
        ColumnMeta columnMeta = ReflectionUtil.getColumnMeta(model);
        Object[] columnName = columnMeta.getNames();
        Object[] columnValue = columnMeta.getValues();

        StringBuffer  questionMarkStr = new StringBuffer();
        for (int i =0;i < columnValue.length; i++ ){
            questionMarkStr.append("?");
            if ( (i+1) != columnValue.length) {
                questionMarkStr.append(",");
            }
        }

        Table table = model.getClass().getAnnotation(Table.class);
        String columnNameStr = StringUtils.join(columnName,",");

        String addSql = "INSERT INTO "+table.name()+" ("+columnNameStr+") VALUES ("+questionMarkStr.toString()+")";

        DataMeta dataMeta = new DataMeta();
        dataMeta.setSql(addSql);
        dataMeta.setParamter(columnValue);
        return dataMeta;
    }

    @Override
    public <T> DataMeta update(T model, Integer id) {
        ColumnMeta columnMeta = ReflectionUtil.getColumnMeta(model);
        Object[] columnName = columnMeta.getNames();
        Object[] columnValue = columnMeta.getValues();

        String columnId = ReflectionUtil.getPrimaryKey(model.getClass());
        Table table = model.getClass().getAnnotation(Table.class);

        List valueList = new ArrayList();
        StringBuffer  setStr = new StringBuffer();
        for ( int i =0; i < columnName.length; i++ ){
            setStr.append(columnName[i]+"=?");
            valueList.add(columnValue[i]);
            if ( (i+1) != columnName.length) {
                setStr.append(",");
            }
        }
        String editSql = "UPDATE "+table.name()+" SET "+setStr.toString()+" WHERE "+columnId+"=?";
        valueList.add(id);

        DataMeta dataMeta = new DataMeta();
        dataMeta.setSql(editSql);
        dataMeta.setParamter(valueList.toArray());
        return dataMeta;
    }


    @Override
    public String queryForModel(Class clazz) {
        Table table =  (Table) clazz.getAnnotation(Table.class);
        String columnId = ReflectionUtil.getPrimaryKey(clazz);
        String queryOneSql = "SELECT * FROM "+table.name()+" WHERE "+columnId+"=?";
        return queryOneSql;
    }

    @Override
    public String delete(Class clazz) {
        Table table =  (Table) clazz.getAnnotation(Table.class);
        String columnId = ReflectionUtil.getPrimaryKey(clazz);
        String deleteSql = "DELETE FROM "+table.name()+" WHERE "+columnId+"=?";
        return deleteSql;
    }

}
