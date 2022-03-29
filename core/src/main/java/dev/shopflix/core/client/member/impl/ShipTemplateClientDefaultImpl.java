/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.ShipTemplateClient;
import dev.shopflix.core.system.model.vo.ShipTemplateVO;
import dev.shopflix.core.system.service.ShipTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/25 16:24
 */
@Service
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class ShipTemplateClientDefaultImpl implements ShipTemplateClient {

    @Autowired
    private ShipTemplateManager shipTemplateManager;

    @Override
    public ShipTemplateVO get(Integer id) {
        return shipTemplateManager.getFromCache(id);
    }
}
