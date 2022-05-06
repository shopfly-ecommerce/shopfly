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
package cloud.shopfly.b2c.framework.context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 蛇形转驼峰数据绑定器
 * Created by kingapex on 2018/3/20.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/20
 */
public class SnakeToCamelRequestDataBinder extends ExtendedServletRequestDataBinder {


    /**
     * 构造器，根据spring机制机要求存在
     *
     * @param target     spring 机制传递 target
     * @param objectName spring 机制传递 objectName
     */
    public SnakeToCamelRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    /**
     * 对蛇形参数绑定值
     *
     * @param mpvs    spring 机制传递   mpvs
     * @param request spring 机制传递  request
     */
    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);

        //处理JsonProperty注释的对象
        Class<?> targetClass = getTarget().getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
            if (jsonPropertyAnnotation != null && mpvs.contains(jsonPropertyAnnotation.value())) {
                if (!mpvs.contains(field.getName())) {
                    mpvs.add(field.getName(), mpvs.getPropertyValue(jsonPropertyAnnotation.value()).getValue());
                }
            }
        }

        List<PropertyValue> covertValues = new ArrayList<PropertyValue>();
        for (PropertyValue propertyValue : mpvs.getPropertyValueList()) {
            if (propertyValue.getName().contains("_")) {
                String camelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, propertyValue.getName());
                if (!mpvs.contains(camelName)) {
                    covertValues.add(new PropertyValue(camelName, propertyValue.getValue()));
                }
            }
        }


        mpvs.getPropertyValueList().addAll(covertValues);
    }


}
