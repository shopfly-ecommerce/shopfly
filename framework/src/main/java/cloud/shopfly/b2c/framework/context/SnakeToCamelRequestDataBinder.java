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
 * Serpentine hump data binder
 * Created by kingapex on 2018/3/20.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/20
 */
public class SnakeToCamelRequestDataBinder extends ExtendedServletRequestDataBinder {


    /**
     * Constructor, according tospringThe machine needs to exist
     *
     * @param target     spring Mechanism fortarget
     * @param objectName spring Mechanism forobjectName
     */
    public SnakeToCamelRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    /**
     * Bind values to snake parameters
     *
     * @param mpvs    spring Mechanism formpvs
     * @param request spring Mechanism forrequest
     */
    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);

        // Objects that handle JsonProperty annotations
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
