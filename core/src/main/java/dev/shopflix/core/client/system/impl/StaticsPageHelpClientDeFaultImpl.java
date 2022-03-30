/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.client.system.StaticsPageHelpClient;
import dev.shopflix.core.pagedata.service.StaticsPageHelpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 静态页帮助中心
 * @date 2018/8/14 10:40
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class StaticsPageHelpClientDeFaultImpl implements StaticsPageHelpClient {

    @Autowired
    private StaticsPageHelpManager staticsPageHelpManager;

    @Override
    public Integer count() {
        return staticsPageHelpManager.count();
    }

    @Override
    public List helpList(Integer page, Integer pageSize) {
        return staticsPageHelpManager.helpList(page,pageSize);
    }
}
