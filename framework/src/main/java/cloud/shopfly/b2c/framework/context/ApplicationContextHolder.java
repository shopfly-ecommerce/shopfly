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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * spring Get the class from the file up and down
 * Created by kingapex on 2018/3/23.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/23
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    /**
     * Context object instance
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }

    /**
     * To obtainapplicationContext
     * @return  spring applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * throughnameTo obtainBean.
     * @param name beanThe name of the
     * @return beanThe instance
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * throughclassTo obtainBean.
     * @param clazz beanThe type of
     * @param <T> beanThe type of
     * @return beanThe instance
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * throughname,As well asClazzReturns the specifiedBean
     * @param name beanThe name of the
     * @param clazz beanThe type of
     * @param <T>  beanThe type of
     * @return beanThe instance
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
