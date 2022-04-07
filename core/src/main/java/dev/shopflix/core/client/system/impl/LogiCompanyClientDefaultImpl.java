/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.client.system.LogiCompanyClient;
import dev.shopflix.core.system.model.dos.LogiCompanyDO;
import dev.shopflix.core.system.service.LogiCompanyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/26 14:18
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class LogiCompanyClientDefaultImpl implements LogiCompanyClient {

    @Autowired
    private LogiCompanyManager logiCompanyManager;

    @Override
    public LogiCompanyDO getLogiByCode(String code) {
        return logiCompanyManager.getLogiByCode(code);
    }

    @Override
    public LogiCompanyDO getModel(Integer id) {
        return logiCompanyManager.getModel(id);
    }

    @Override
    public List<LogiCompanyDO> list() {
        return logiCompanyManager.list();
    }
}