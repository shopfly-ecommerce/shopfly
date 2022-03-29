/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system.impl;

import com.enation.app.javashop.core.client.system.HotkeywordClient;
import com.enation.app.javashop.core.pagedata.model.HotKeyword;
import com.enation.app.javashop.core.pagedata.service.HotKeywordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @version v1.0
 * @Description: 热点关键字
 * @date 2021-01-19
 * @since v7.1.0
 */
@Service
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class HotKeywordClientDefaultImpl implements HotkeywordClient {

    @Autowired
    private HotKeywordManager hotKeywordManager;

    @Override
    public List<HotKeyword> listByNum(Integer num) {
        return hotKeywordManager.listByNum(num);
    }
}
