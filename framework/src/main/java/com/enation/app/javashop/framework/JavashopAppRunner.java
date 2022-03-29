/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework;

import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.context.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Javashop项目启动配置
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */

@Component
@Order(value = 1)
public class JavashopAppRunner implements ApplicationRunner {


    /**
     * 用户信息holder，认证信息的获取者
     */
    @Autowired
    private UserHolder userHolder;


    @Autowired
    private JavashopConfig javashopConfig;

    /**
     * 在项目加载时指定认证信息获取者
     * 默认是由spring 安全上下文中获取
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (javashopConfig.getTokenSecret() == null) {
            String errorMsg = "配置异常:未配置token秘钥，请到config配置中心检查如下：\n";
            errorMsg += "===========================\n";
            errorMsg += "   javashop.token-secret\n";
            errorMsg += "===========================";

            throw new Exception(errorMsg);
        }

        UserContext.setHolder(userHolder);

    }
}
