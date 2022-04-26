/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.api.config;

import dev.shopflix.core.base.context.Region;
import dev.shopflix.framework.swagger.AbstractSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by kingapex on 2018/3/10.
 * Swagger2配置
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/10
 */

@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Swagger2Config extends AbstractSwagger2 {

    @Bean
    public Docket wechat() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/wechat/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket distributionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("分销")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/distribution/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket pintuanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("拼团")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/pintuan/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket afterSaleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("售后")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/after-sales/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }
    @Bean
    public Docket sssApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("流量")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/view"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket goodsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("商品")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/goods/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket passportApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("会员认证中心")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.regex("(/seller/passport/.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("会员")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.regex("(/seller/members.*)|(/seller/account-binder.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket pageDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("楼层")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.regex("(/seller/pages/.*)|(/seller/focus-pictures.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket paymentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("支付")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/order/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket shopApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("店铺")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/shops/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket tradeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("交易")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.regex("(/seller/trade.*)|(/seller/express.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket promotionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("促销")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.ant("/seller/promotions/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("所有")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("dev.shopflix.api"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("买家Api文档")
                .description("买家中心API接口")
                .version("7.0")
                .contact(new Contact("shopflix", "https://www.shopflix.dev", "service@shopflix.dev"))
                .build();
    }
}
