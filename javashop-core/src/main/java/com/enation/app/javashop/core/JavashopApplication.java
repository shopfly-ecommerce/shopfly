/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by kingapex on 2018/3/21.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/21
 */
@SpringBootApplication
@ComponentScan("com.enation.app.javashop")
public class JavashopApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavashopApplication.class, args);
    }

}
