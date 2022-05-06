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
package cloud.shopfly.b2c.framework.auth;

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
