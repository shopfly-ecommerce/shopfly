/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.api.config;

import cloud.shopfly.b2c.core.base.context.Region;
import cloud.shopfly.b2c.framework.swagger.AbstractSwagger2;
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
 * Swagger2configuration
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
                .groupName("Buyer side-WeChat")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/wechat/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket distributionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-distribution")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/distribution/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket pintuanApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-Spell group")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/pintuan/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket afterSaleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-after-sales")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/after-sales/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }
    @Bean
    public Docket sssApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-traffic")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/view"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket goodsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-product")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/goods/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket passportApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-Member Certification Center")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.regex("(/passport/.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-members")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.regex("(/members.*)|(/account-binder.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket pageDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-floor")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.regex("(/pages/.*)|(/focus-pictures.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket paymentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-pay")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/order/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket shopApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-The store")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/shops/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket tradeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-trading")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.regex("(/trade.*)|(/express.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket promotionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Buyer side-Sales promotion")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.buyer"))
                .paths(PathSelectors.ant("/promotions/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("buyersApiThe document")
                .description("The buyer centerAPIinterface")
                .version("7.0")
                .contact(new Contact("shopfly", "https://www.shopfly.dev", "service@shopfly.dev"))
                .build();
    }

    private ApiInfo sellerApiInfo() {
        return new ApiInfoBuilder()
                .title("merchantsApiThe document")
                .description("Business centerAPIinterface")
                .version("7.0")
                .contact(new Contact("shopfly", "https://www.shopfly.dev", "service@shopfly.dev"))
                .build();
    }

    @Bean
    public Docket sellerAfterSaleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-after-sales")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/after-sales/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerPaymentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-pay")
                .apiInfo(sellerApiInfo())
                .select()
                // Change to your own package path
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/payment/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerGoodsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-product")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/goods/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerMemberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-members")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.regex("(/seller/members.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerPageDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-floor")
                .apiInfo(sellerApiInfo())
                .select()
                // Change to your own package path
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.regex("(/seller/focus-pictures.*)|(/seller/pages.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerPageCreateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-A static page")
                .apiInfo(sellerApiInfo())
                .select()
                // Change to your own package path
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/page-create/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerPromotionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-Sales promotion")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/promotion/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }


    @Bean
    public Docket sellerPassportApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-Merchant Certification Center")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.regex("(/seller/login.*)|(/seller/register.*)|(/seller/check.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerTradeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-trading")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.regex("(/seller/trade.*)|(/seller/waybill.*)|(/seller/express.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerSystemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-System Settings")
                .apiInfo(sellerApiInfo())
                .select()
                // Change to your own package path
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.regex("(/seller/task.*)|(/seller/systems.*)|(/seller/settings.*)|(/seller/sensitive-words.*)"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerStatisticsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-statistical")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/statistics/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }

    @Bean
    public Docket sellerDistributionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("The management end-distribution")
                .apiInfo(sellerApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cloud.shopfly.b2c.api.seller"))
                .paths(PathSelectors.ant("/seller/distribution/**"))
                .build().globalOperationParameters(this.buildParameter()).directModelSubstitute(Region.class, Integer.class);
    }
}
