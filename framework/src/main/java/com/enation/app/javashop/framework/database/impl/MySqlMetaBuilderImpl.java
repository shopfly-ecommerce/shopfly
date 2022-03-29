/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.database.impl;

import com.enation.app.javashop.framework.database.ColumnMeta;
import com.enation.app.javashop.framework.database.DataMeta;
import com.enation.app.javashop.framework.database.SqlMetaBuilder;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.enation.app.javashop.framework.util.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于Mysql的基本增删改查操作实现类
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
