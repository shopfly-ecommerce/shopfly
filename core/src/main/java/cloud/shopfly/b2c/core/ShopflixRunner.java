/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core;

import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexInitManager;
import cloud.shopfly.b2c.framework.ShopflixConfig;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.context.UserHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Shopflix 项目启动配置
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */

@Component
@Order(value = 1)
public class ShopflixRunner implements ApplicationRunner {


    /**
     * 用户信息holder，认证信息的获取者
     */
    @Autowired
    private UserHolder userHolder;


    @Autowired
    private ShopflixConfig shopflixConfig;

    @Autowired
    GoodsIndexInitManager goodsIndexInitManager;

   protected final Logger logger = LoggerFactory.getLogger(ShopflixRunner.class);


    /**
     * 在项目加载时指定认证信息获取者
     * 默认是由spring 安全上下文中获取
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (shopflixConfig.getTokenSecret() == null) {
            String errorMsg = "配置异常:未配置token秘钥，请到config配置中心检查如下：\n";
            errorMsg += "===========================\n";
            errorMsg += "   shopflix.token-secret\n";
            errorMsg += "===========================";

            throw new Exception(errorMsg);
        }

        UserContext.setHolder(userHolder);

        goodsIndexInitManager.initIndex();
        logger.debug("Shopflix started");

    }
}
