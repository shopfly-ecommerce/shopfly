/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.auth;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-24
 */

public class BeanUtil {

    public static <T> T mapToBean(Class<T> clz, Map map) {

        T t = BeanUtils.instantiateClass(clz);

        List<Field> fields = getFields(clz);
        fields.forEach(field -> {
            String fieldName = field.getName();
            Method setter = BeanUtils.getPropertyDescriptor(clz, fieldName).getWriteMethod();
            Object value = map.get(fieldName);
            try {
                if (setter != null) {
                    setter.invoke(t, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return t;
    }

    public static List<Field> getFields(Class clz) {
        List<Field> fields = new ArrayList<>();
        //获取所有属性(包含父类)
        fields = getParentField(clz, fields);
        return fields;
    }


    /**
     * 递归获取所有父类的属性
     *
     * @param calzz
     * @param list
     * @return add by liuyulei 2019-02-14
     */
    private static List<Field> getParentField(Class<?> calzz, List<Field> list) {

        if (calzz.getSuperclass() != Object.class) {
            getParentField(calzz.getSuperclass(), list);
        }

        Field[] fields = calzz.getDeclaredFields();
        list.addAll(arrayConvertList(fields));

        return list;
    }


    /**
     * 将数组转换成List
     *
     * @param fields
     * @return add by liuyulei 2019-02-14
     */
    private static List<Field> arrayConvertList(Field[] fields) {
        List<Field> resultList = new ArrayList<>(fields.length);
        Collections.addAll(resultList, fields);
        return resultList;

    }
}
