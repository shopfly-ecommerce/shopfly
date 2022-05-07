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
 * Regional format chemical plant
 * be@RegionFormatThe annotation property accepts a localeidConvert to a locale object
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
public class RegionFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<RegionFormat> {

    /**
     * Gets the type of the annotated object
     *
     * @return
     */
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(asList(Region.class));
    }

    /**
     * Get the output object
     *
     * @param annotation Annotation instance
     * @param fieldType  The type of the annotated field
     * @return The output object of the formatted locale
     */
    @Override
    public Printer<?> getPrinter(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }

    /**
     * Get the parser
     *
     * @param annotation Annotation instance
     * @param fieldType  The type of the annotated field
     * @return Area formatted object
     */
    @Override
    public Parser<?> getParser(RegionFormat annotation, Class<?> fieldType) {
        return new RegionFormatter();
    }
}
