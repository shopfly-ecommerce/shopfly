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

import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author fk
 * @version v2.0
 * @Description: 对象之间转换操作
 * @date 2018/4/1616:40
 * @since v7.0.0
 */
public class BeanUtil {

    /**
     * 根据Map中的key对应对象的属性来更新对象的值
     * <li>
     * updateProperties中的key必须跟bean中的字段名保持一致才能更新
     * </li>
     *
     * @param updateProperties 要更新的字段以及值
     * @param bean             要更新的对象
     * @author wangyijie
     */
    public static <T> void copyPropertiesInclude(Map<String, Object> updateProperties, T bean) {

        Set<Map.Entry<String, Object>> revisabilityFiledSet = updateProperties.entrySet();
        for (Map.Entry<String, Object> entry : revisabilityFiledSet) {
            Object value = entry.getValue();
            if (value != null) {
                try {
                    org.apache.commons.beanutils.BeanUtils.setProperty(bean, entry.getKey(), value);
                } catch (Exception e) {

                }
            }
        }

    }

    /**
     * 复制属性
     * @param objectFrom
     * @param objectTo
     */
    public static void copyProperties(Object objectFrom,Object objectTo){

        BeanUtils.copyProperties(objectFrom, objectTo);

    }


}
