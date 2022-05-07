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

import cloud.shopfly.b2c.core.client.system.RegionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by kingapex on 2018/5/2.
 * Register a custom locale formatter
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RegionsClient regionsClient;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new RegionFormatAnnotationFormatterFactory());
        registry.addFormatter(new RegionFormatter(regionsClient));
    }


}

