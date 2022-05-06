/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 反射机制工具类
 * @author Snow create in 2018/3/21
 * @version v2.0
 * @since v7.0.0
 */
public class ReflectionUtil {


	/**
	 * 将po对象中有属性和值转换成map
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
	 * 遍历实体类的属性和数据类型以及属性值
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
			//获取所有属性(包含父类)
			fields = getParentField(model.getClass(),fields);

			/*** 修改获取不到父类属性的问题 **********end*/


			//属性名
			List columnName = new ArrayList();
			//属性值
			List columnValue = new ArrayList();

			/**
			 * 遍历所有的属性，过滤掉满足以下条件的属性。
			 * 1、属性值为null的数据
			 * 2、没有添加自定义标签 @id 和 @Column 的属性
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
	 * 根据Class读取主键的字段名
	 * @param clazz
	 * @return
	 */
	public static String  getPrimaryKey(Class clazz){
		String  columnId = "";
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//主键字段名
			if(field.isAnnotationPresent(Id.class)){
				Id id =  field.getAnnotation(Id.class);
				String columnIdName = id.name();

				//是否自定义的属性名
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
	 * 递归获取所有父类的属性
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
	 * 将数组转换成List
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
