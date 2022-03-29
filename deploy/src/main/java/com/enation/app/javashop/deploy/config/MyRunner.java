/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.ws.soap.Addressing;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2021/4/16
 */
@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    MysqlConfig mysqlConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(mysqlConfig);
    }
}
