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
package cloud.shopfly.b2c.framework.swagger;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置基类
 * Created by kingapex on 2018/3/23.
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/23
 */
public abstract class AbstractSwagger2 {


    /**
     * 构建认证token参数
     * @return token参数
     */
   protected  List<Parameter>  buildParameter( ){

       ParameterBuilder tokenPar = new ParameterBuilder();
       List<Parameter> pars = new ArrayList<Parameter>();
       tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
       pars.add(tokenPar.build());
        return  pars;
   }

}
