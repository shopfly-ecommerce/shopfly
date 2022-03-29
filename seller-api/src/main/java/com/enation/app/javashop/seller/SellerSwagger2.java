/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package com.enation.app.javashop.seller;

import com.enation.app.javashop.core.base.context.Region;
import com.enation.app.javashop.framework.swagger.AbstractSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingapex on 2018/3/10.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/10
 */

@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class SellerSwagger2 extends AbstractSwagger2 {

    @Bean
    public Docket afterSaleApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("售后")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/after-sales/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket paymentApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("支付")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/payment/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket goodsApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("商品")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/goods/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket memberApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("会员")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.regex("(/seller/members.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket pageDataApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("楼层")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.regex("(/seller/focus-pictures.*)|(/seller/pages.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket pageCreateApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("静态页")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/page-create/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket promotionApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("促销")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/promotion/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket passportApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("商家认证中心")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.regex("(/seller/login.*)|(/seller/register.*)|(/seller/check.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket tradeApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("交易")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.regex("(/seller/trade.*)|(/seller/waybill.*)|(/seller/express.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket systemApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统设置")
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.regex("(/seller/task.*)|(/seller/systems.*)|(/seller/settings.*)|(/seller/sensitive-words.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket statisticsApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("统计")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/statistics/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket distributionApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("分销")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enation.app.javashop.seller.api"))
                .paths(PathSelectors.ant("/seller/distribution/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("卖家中心Api文档")
                .description("卖家中心API接口")
                .version("7.0")
                .contact(new Contact("Javashop", "http://www.javamall.com.cn", "service@javashop.cn"))
                .build();
    }
}
