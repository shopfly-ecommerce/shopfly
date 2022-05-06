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
package cloud.shopfly.b2c.core.base.context;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * kingapex on 2018/5/2.
 * 地区格式化工厂
 * 被@RegionFormat注解的属性，会接受一个地区id转换为地区对象
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
public class RegionFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<RegionFormat> {

    /**
     * 获取被注解对象的类型
     *
     * @return
     */
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(asList(Region.class));
    }

    /**
     * 获取输出对象
     *
     * @param annotation 注解实例
     * @param fieldType  被注解字段的类型
     * @return 地区格式化后输出的对象
     */
    @Override
    public Printer<?> getPrinter(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }

    /**
     * 获取解析器
     *
     * @param annotation 注解实例
     * @param fieldType  被注解字段的类型
     * @return 地区格式化后对象
     */
    @Override
    public Parser<?> getParser(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }
}
