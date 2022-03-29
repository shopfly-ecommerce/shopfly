/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * javashop 安装器工程
 *
 * @author kingapex
 * @version v1.0.0
 * @since v7.0.0
 * 2018年1月22日 上午10:01:32
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(value = "com.enation.app.javashop.framework.database," +
        "com.enation.app.javashop.framework.context," +
        "com.enation.app.javashop.framework.exception," +
        "com.enation.app.javashop.framework.redis.configure.builders,"+
        "com.enation.app.javashop.deploy")
public class JavashopDeployApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JavashopDeployApplication.class, args);

    }
}
