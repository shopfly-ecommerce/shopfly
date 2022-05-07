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
package cloud.shopfly.b2c.framework.util;

import cloud.shopfly.b2c.framework.database.ColumnMeta;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Reflection mechanism utility class
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public class ReflectionUtil {


	/**
	 * willpoObjects have properties and values converted tomap
	 * @param po
	 * @return
	 */
	@SuppressWarnings("AlibabaCollectionInitShouldAssignCapacity")
	public static Map po2Map(Object po) {
		Map poMap = new HashedMap();
		ColumnMeta columnMeta = getColumnMeta(po);

		Object[] columnName = columnMeta.getNames();
		Object[] columnValue = columnMeta.getValues();

		for (int i = 0; i < columnValue.length; i++) {
			poMap.put(columnName[i],columnValue[i]);
		}

		return poMap;
	}


	/**
	 * Iterates through the attributes and data types of the entity class as well as the attribute values
	 * @param model
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static ColumnMeta getColumnMeta(Object model)  {

		ColumnMeta columnMeta = new ColumnMeta();

		try {
			List<Field> fields = new ArrayList<>();
			// Get all attributes (including parent classes)
			fields = getParentField(model.getClass(),fields);

			/*** Fix the problem of not getting a parent class attribute**********end*/


			// The property name
			List columnName = new ArrayList();
			// Attribute values
			List columnValue = new ArrayList();

			/**
			 * Iterate through all attributes and filter out attributes that meet the following criteria.
			 * 1、Attribute values fornullThe data of
			 * 2、No custom label is added@id and@Column The properties of the
			 */
			for (Field field:fields ) {

				if(field.isAnnotationPresent(Column.class) ){

					String dbName = field.getName();
					Column column = field.getAnnotation(Column.class);
					if(!StringUtil.isEmpty(column.name())){
						dbName = column.name();
					}
					ReflectionUtils.makeAccessible(field);
					Object value = ReflectionUtils.getField(field,model);
					if ( value == null && !column.allowNullUpdate()){
						continue;
					}

					columnName.add(dbName);
					columnValue.add(value);
				}
			}

			columnMeta.setNames(columnName.toArray());
			columnMeta.setValues(columnValue.toArray());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return columnMeta;
	}

	/**
	 * According to theClassRead the field name of the primary key
	 * @param clazz
	 * @return
	 */
	public static String  getPrimaryKey(Class clazz){
		String  columnId = "";
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// Primary key field name
			if(field.isAnnotationPresent(Id.class)){
				Id id =  field.getAnnotation(Id.class);
				String columnIdName = id.name();

				// Whether the attribute name is custom
				if(StringUtil.isEmpty(columnIdName)){
					columnId = field.getName();
				}else{
					columnId = columnIdName;
				}
				break;
			}
		}
		return columnId;
	}



	/**
	 * Recursively gets the attributes of all the parent classes
	 * @param calzz
	 * @param list
	 * @return
	 *
	 * add by liuyulei 2019-02-14
	 */
	public static List<Field> getParentField(Class<?> calzz,List<Field> list){

		if(calzz.getSuperclass() !=  Object.class){
			getParentField(calzz.getSuperclass(),list);
		}

		Field[] fields = calzz.getDeclaredFields();
		list.addAll(arrayConvertList(fields));

		return list ;
	}

	/**
	 * Convert the array toList
	 * @param fields
	 * @return
	 * add by liuyulei 2019-02-14
	 *
	 */
	public static List<Field> arrayConvertList(Field[] fields){
		List<Field> resultList = new ArrayList<>(fields.length);
		Collections.addAll(resultList,fields);
		return  resultList;

	}
}
